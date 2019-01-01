package edu.epam.audio.model.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.dao.impl.UserDaoImpl;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.model.util.RequestAttributes.*;
import static edu.epam.audio.model.util.RequestParams.*;
import static edu.epam.audio.model.util.UploadPath.*;

public class UserService {
    private static final String INCORRECT_LOGIN = "Wrong email or password.";
    private static final String INCORRECT_EMAIL_REG = "There is user with such email";
    private static final String NO_SUCH_USER = "There is no such user";

    private static final String INCORRECT_NAME_REG = "There is user with this name";

    private static final String INCORRECT_PASSWORD_REGEX_MES = "Incorrect password, password should be 8 characters " +
            "length at least one letter and one number";
    private static final String INCORRECT_NAME_MES = "Incorrect name, name should be at least 6 characters length, " +
            "all the characters should be letters";

    public void loginUser(RequestContent wrapper) throws LogicLayerException {
        UserDao userDao = UserDaoImpl.getInstance();

        String email = wrapper.getRequestParam(PARAM_NAME_EMAIL);
        String password = wrapper.getRequestParam(PARAM_NAME_PASSWORD);

        String encryptedPassword = DigestUtils.md5Hex(password);

        User potentialUser = new UserBuilder()
                .addEmail(email)
                .build();

        try {
            Optional<User> userFromDb = userDao.findUserByEmail(potentialUser);
            if (userFromDb.isPresent() && userFromDb.get().getPassword().equals(encryptedPassword)) {
                    wrapper.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER, userFromDb.get());
                    wrapper.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
            } else {
                wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_LOGIN);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while logging into app.", e);
        }
    }

    public void registerUser(RequestContent wrapper) throws LogicLayerException {
        String email = wrapper.getRequestParam(PARAM_NAME_EMAIL);
        String password = wrapper.getRequestParam(PARAM_NAME_PASSWORD);
        String name = wrapper.getRequestParam(PARAM_NAME_NICK);

        User user = new UserBuilder()
                .addEmail(email)
                .addPassword(password)
                .addName(name)
                .build();

        UserDao userDao = UserDaoImpl.getInstance();

        try {
            Optional<User> userFormDb = userDao.findUserByEmail(user);
            if (!userFormDb.isPresent()){
                if(ParamsValidator.validatePassword(password)){
                    if (ParamsValidator.validateName(name)) {
                        userFormDb = userDao.findUserByName(user);
                        if (!userFormDb.isPresent()){
                            String encryptedPassword = DigestUtils.md5Hex(password);
                            user.setPassword(encryptedPassword);
                            userDao.create(user);
                            wrapper.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER, user);
                            wrapper.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
                        } else {
                            wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                        }
                    } else {
                        wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_MES);
                    }
                } else{
                    wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_PASSWORD_REGEX_MES);
                }
            } else {
                wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while register into the app.", e);
        }
    }

    public void updateProfile(RequestContent wrapper) throws LogicLayerException {
        User user = (User) wrapper.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        UserDao userDao = UserDaoImpl.getInstance();

        String email = wrapper.getRequestParam(PARAM_NAME_EMAIL);
        String name = wrapper.getRequestParam(PARAM_NAME_NICK);
        String path = (String) wrapper.getRequestAttribute(PARAM_NAME_PATH);

        try {
            User updatedUser = user.clone();
            updatedUser.setEmail(email);
            updatedUser.setName(name);

            Optional<User> userByEmail = userDao.findUserByEmail(updatedUser);
            if (!userByEmail.isPresent() || userByEmail.get().getUserId() == updatedUser.getUserId()){
                Optional<User> userByName = userDao.findUserByName(updatedUser);
                if(!userByName.isPresent() || userByName.get().getUserId() == user.getUserId()){
                    File fileSaveDir = new File(path);
                    if(!fileSaveDir.exists()){
                        fileSaveDir.mkdirs();
                    }

                    String formedPath;
                    Part part = wrapper.getRequestPart(PARAM_NAME_PHOTO);

                    if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                        formedPath = path + File.separator + part.getSubmittedFileName();
                        part.write(formedPath);
                        String pathToLoad = PATH_TO_SAVE + UPLOAD_PHOTOS_DIR
                                + PATH_DELIMITER + part.getSubmittedFileName();
                        updatedUser.setPhoto(pathToLoad);
                    }

                    userDao.update(updatedUser);
                    wrapper.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER, updatedUser);

                    wrapper.removeRequestAttribute(ATTRIBUTE_NAME_ERROR);
                }
                else {
                    wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                }
            }
            else {
                wrapper.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (CloneNotSupportedException e) {
            throw new LogicLayerException("Exception in cloning user.", e);
        } catch (IOException e) {
            throw new LogicLayerException("Exception in writing photo.", e);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting user from db.", e);
        }
    }

    public void updateBonus(RequestContent content) throws LogicLayerException {
        long id = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        String strBonus = content.getRequestParam(PARAM_NAME_BONUS);
        //todo: add validation
        double bonus = Double.parseDouble(strBonus);

        UserDao userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> user = userDao.findEntityById(id);
            if (user.isPresent()){
                User userObj = user.get();
                userObj.setBonus(bonus);
                userDao.update(userObj);
            } else {
                content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, NO_SUCH_USER);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting user from db.", e);
        }
    }

    public List<User> loadAllUsers() throws LogicLayerException {
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> users;

        try {
            users = userDao.findAll();
            return users;
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in gtting users from db.", e);
        }
    }
}
