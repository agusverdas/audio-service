package edu.epam.audio.model.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.dao.SongDao;
import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.dao.impl.SongDaoImpl;
import edu.epam.audio.model.dao.impl.UserDaoImpl;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.model.util.RequestAttributes.*;
import static edu.epam.audio.model.util.RequestParams.*;

public class UserService {
    private static final String INCORRECT_BONUS = "Bonus should be decimal";
    private static final String INCORRECT_LOGIN = "Wrong email or password";
    private static final String INCORRECT_EMAIL_REG = "There is user with such email";
    private static final String NO_SUCH_USER = "There is no such user";
    private static final String INCORRECT_BONUS_PAYMENT = "You don't have enough bonus to pay for this audio";
    private static final String INCORRECT_POCKET_PAYMENT = "You don't have enough money to pay for this audio";

    private static final String INCORRECT_NAME_REG = "There is user with this name";

    private static final String INCORRECT_PASSWORD_REGEX_MES = "Incorrect password, password should be 6-10 characters " +
            "length at least one letter and one number";
    private static final String INCORRECT_NAME_MES = "Incorrect name, name should be at least 6 characters length, " +
            "all the characters should be letters. Name is unique.";
    private static final String INCORRECT_MONEY = "Money should be positive decimal";

    public void loginUser(RequestContent content) throws LogicLayerException {
        UserDao userDao = UserDaoImpl.getInstance();
        String email = content.getRequestParam(PARAM_NAME_EMAIL);
        String password = content.getRequestParam(PARAM_NAME_PASSWORD);

        String encryptedPassword = DigestUtils.md5Hex(password);

        User potentialUser = new UserBuilder()
                .addEmail(email)
                .build();

        try {
            Optional<User> userFromDb = userDao.findUserByEmail(potentialUser);
            if (userFromDb.isPresent() && userFromDb.get().getPassword().equals(encryptedPassword)) {
                content.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER, userFromDb.get());
                content.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_LOGIN);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while logging into app.", e);
        }
    }

    public void registerUser(RequestContent content) throws LogicLayerException {
        String email = content.getRequestParam(PARAM_NAME_EMAIL);
        String password = content.getRequestParam(PARAM_NAME_PASSWORD);
        String name = content.getRequestParam(PARAM_NAME_NICK);

        User user = new UserBuilder()
                .addEmail(email)
                .addPassword(password)
                .addName(name)
                .build();

        UserDao userDao = UserDaoImpl.getInstance();

        try {
            Optional<User> userFormDb = userDao.findUserByEmail(user);
            if (!userFormDb.isPresent()) {
                if (ParamsValidator.validatePassword(password)) {
                    if (ParamsValidator.validateName(name)) {
                        userFormDb = userDao.findUserByName(user);
                        if (!userFormDb.isPresent()) {
                            String encryptedPassword = DigestUtils.md5Hex(password);
                            user.setPassword(encryptedPassword);
                            userDao.create(user);
                            content.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER, user);
                            content.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
                        } else {
                            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                        }
                    } else {
                        content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_MES);
                    }
                } else {
                    content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_PASSWORD_REGEX_MES);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while register into the app.", e);
        }
    }

    public void updateProfile(RequestContent content) throws LogicLayerException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        UserDao userDao = UserDaoImpl.getInstance();

        String email = content.getRequestParam(PARAM_NAME_EMAIL);
        String name = content.getRequestParam(PARAM_NAME_NICK);
        String path = (String) content.getRequestAttribute(PARAM_NAME_PATH);

        try {
            User updatedUser = user.clone();
            updatedUser.setEmail(email);
            updatedUser.setName(name);

            Optional<User> userByEmail = userDao.findUserByEmail(updatedUser);
            if (!userByEmail.isPresent() || userByEmail.get().getUserId() == updatedUser.getUserId()) {
                Optional<User> userByName = userDao.findUserByName(updatedUser);
                if (!userByName.isPresent() || userByName.get().getUserId() == user.getUserId()) {
                    File fileSaveDir = new File(path);
                    if (!fileSaveDir.exists()) {
                        fileSaveDir.mkdirs();
                    }

                    Part part = content.getRequestPart(PARAM_NAME_PHOTO);

                    String loadPath = UploadPath.uploadPhoto(path, part);
                    if (loadPath != null){
                        updatedUser.setPhoto(loadPath);
                    }

                    userDao.update(updatedUser);
                    content.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER, updatedUser);

                    content.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
                } else {
                    content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                }
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (CloneNotSupportedException e) {
            throw new LogicLayerException("Exception in cloning user.", e);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting user from db.", e);
        }
    }

    public void updateBonus(RequestContent content) throws LogicLayerException {
        long id = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        String strBonus = content.getRequestParam(PARAM_NAME_BONUS);
        UserDao userDao = UserDaoImpl.getInstance();

        try {
            double bonus = Double.parseDouble(strBonus);
            Optional<User> user = userDao.findEntityById(id);
            if (user.isPresent()) {
                User userObj = user.get();
                userObj.setBonus(bonus);
                userDao.update(userObj);
                content.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, NO_SUCH_USER);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting user from db.", e);
        } catch (NumberFormatException e){
            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_BONUS);
        }
    }

    public List<User> loadAllUsers() throws LogicLayerException {
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> users;

        try {
            users = userDao.findAll();
            return users;
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting users from db.", e);
        }
    }

    public void addMoney(RequestContent content) throws LogicLayerException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        UserDao userDao = UserDaoImpl.getInstance();

        try{
            double money = Double.parseDouble(content.getRequestParam(PARAM_NAME_MONEY));
            double current = user.getMoney();
            //todo: check maximum money
            money += current;
            user.setMoney(money);
            userDao.update(user);
        } catch (NumberFormatException e){
            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_MONEY);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while adding money.", e);
        }
    }

    //todo: ask Правильно ли сделан метод оплаты?
    public void buySong(RequestContent content) throws LogicLayerException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        Long songId = Long.parseLong(content.getRequestParam(RequestParams.PARAM_NAME_ID));
        SongDao songDao = SongDaoImpl.getInstance();
        String paymentType = content.getRequestParam(RequestParams.PARAM_NAME_PAYMENT);
        UserDao userDao = UserDaoImpl.getInstance();
        double bonus;
        double cost;
        double money;

        try{
            Song song  = songDao.findEntityById(songId).get();
            switch (paymentType){
                case RequestParams.PARAM_VALUE_BONUS:
                    bonus = user.getBonus();
                    cost = song.getCost();
                    if (bonus >= cost){
                        user.setBonus(bonus - cost);
                        userDao.songPay(user, song);
                    } else {
                        content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_BONUS_PAYMENT);
                    }
                    break;
                case RequestParams.PARAM_VALUE_POCKET:
                    money = user.getMoney();
                    cost = song.getCost();
                    if (money >= cost){
                        user.setMoney(money - cost);
                        userDao.songPay(user, song);
                    } else {
                        content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_POCKET_PAYMENT);
                    }
                    break;
                    default:
                        throw new LogicLayerException("Unknown parameter : " + paymentType);
            }
        } catch (DaoException e){
            throw new LogicLayerException("Exception in payment.", e);
        }
    }
}
