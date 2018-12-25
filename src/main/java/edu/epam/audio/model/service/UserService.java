package edu.epam.audio.model.service;

import edu.epam.audio.controller.WebParamWrapper;
import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.dao.impl.UserDaoImpl;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.ParamsValidator;
import edu.epam.audio.model.util.WebValuesNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

//todo: encrypt passwords
public class UserService {
    private static final String INCORRECT_EMAIL_LOG = "There is no user with such email";
    private static final String INCORRECT_EMAIL_REG = "There is user with such email";

    private static final String INCORRECT_NAME_REG = "There is user with this name";

    private static final String INCORRECT_PASSWORD_MES = "Incorrect password";
    private static final String INCORRECT_PASSWORD_REGEX_MES = "Incorrect password, password should be 8 characters " +
            "length at least one letter and one number";
    private static final String INCORRECT_NAME_MES = "Incorrect name, name should be at least 6 characters length, " +
            "all the characters should be letters";

    public void loginUser(WebParamWrapper wrapper) throws LogicLayerException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();

        String email = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_EMAIL);
        String password = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_PASSWORD);

        User potentialUser = new UserBuilder()
                .addEmail(email)
                .build();

        try {
            Optional<User> userFromDb = userDao.findUserByEmail(potentialUser);

            if (userFromDb.isPresent()) {
                if (userFromDb.get().getPassword().equals(password)) {
                    wrapper.setSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER, userFromDb.get());
                    wrapper.removeRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR);
                } else {
                    wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_PASSWORD_MES);
                }
            } else {
                wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_LOG);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while logging into app.", e);
        }
    }

    public void logoutUser(WebParamWrapper wrapper) {
        wrapper.removeSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER);
    }

    public void registerUser(WebParamWrapper wrapper) throws LogicLayerException {
        String email = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_EMAIL);
        String password = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_PASSWORD);
        String name = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_NICK);

        User user = new UserBuilder()
                        .addEmail(email)
                        .addPassword(password)
                        .addName(name)
                        .build();

        UserDaoImpl userDao = UserDaoImpl.getInstance();

        try {
            Optional<User> userFormDb = userDao.findUserByEmail(user);
            if (!userFormDb.isPresent()){
                if(ParamsValidator.validatePassword(password)){
                    if (ParamsValidator.validateName(name)) {
                        userFormDb = userDao.findUserByName(user);
                        if (!userFormDb.isPresent()){
                            userDao.create(user);
                            wrapper.setSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER, user);
                            wrapper.removeRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR);
                        } else {
                            wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                        }
                    } else {
                        wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_MES);
                    }
                } else{
                    wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_PASSWORD_REGEX_MES);
                }
            } else {
                wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while register into the app.", e);
        }
    }

    public void updateProfile(WebParamWrapper wrapper) throws LogicLayerException {
        User user = (User) wrapper.getSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER);
        UserDaoImpl userDao = UserDaoImpl.getInstance();

        String email = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_EMAIL);
        String name = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_NICK);
        String path = (String) wrapper.getRequestAttribute(WebValuesNames.PARAM_NAME_PATH);

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

                    String formedPath = null;
                    Part part = wrapper.getRequestPart(WebValuesNames.PARAM_NAME_PHOTO);
                    System.out.println("Updating begins");

                    if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                        formedPath = path + File.separator + part.getSubmittedFileName();
                        part.write(formedPath);
                    }

                    if (formedPath != null){
                        updatedUser.setPhoto(formedPath);
                    }
                    System.out.println(updatedUser);
                    userDao.update(updatedUser);
                    wrapper.setSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER, updatedUser);

                    wrapper.removeRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR);
                }
                else {
                    wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_NAME_REG);
                }
            }
            else {
                wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, INCORRECT_EMAIL_REG);
            }
        } catch (CloneNotSupportedException e) {
            throw new LogicLayerException("Exception in cloning user.", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LogicLayerException("Exception in writing photo.", e);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new LogicLayerException("Exception in getting user from db.", e);
        }
    }
}
