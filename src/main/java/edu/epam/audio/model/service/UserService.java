package edu.epam.audio.model.service;

import edu.epam.audio.controller.WebParamWrapper;
import edu.epam.audio.model.dao.impl.UserDaoImpl;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.WebValuesNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

//todo: encrypt passwords
public class UserService {
    private static Logger logger = LogManager.getLogger();

    //todo: ask Правильно ли я делаю? Есть ли способ лучше?
    //todo: Проверить изменение через параметр
    public void loginUser(WebParamWrapper wrapper) throws LogicLayerException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();

        String email = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_EMAIL);
        String password = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_PASSWORD);

        logger.debug("Email incoming : " + email);
        logger.debug("Password incoming : " + password);

        User potentialUser = new UserBuilder()
                .addEmail(email)
                .build();

        try {
            Optional<User> userFromDb = userDao.findUserByEmail(potentialUser);

            if (userFromDb.isPresent()) {
                logger.debug("User is present : " + userFromDb.get());
                if (userFromDb.get().getPassword().equals(password)) {
                    wrapper.setSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER, userFromDb.get());
                    logger.debug("User in session : " + wrapper.getSessionAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER));
                } else {
                    wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, WebValuesNames.INCORRECT_PASSWORD_MES);
                }
            } else {
                wrapper.setRequestAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR, WebValuesNames.INCORRECT_EMAIL_MES);
            }
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while logging into app.", e);
        }
    }

}

    /*
    public Optional<User> extractUser(String email) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        try {
            return userDaoImpl.findUserByEmail(email);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while extracting user.", e);
        }
    }

    public Optional<User> extractUser(long id) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        try {
            return userDaoImpl.findEntityById(id);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while extracting user.", e);
        }
    }

    public Optional<User> extractByName(String name) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        try{
            return userDaoImpl.findUserByName(name);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while finding user by name.", e);
        }
    }

    //todo: ask Метод относится к бизгнес логике, но по сути просто обращается к DAO.
    public Optional<User> createUser(String email, String password, String name) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        try {
            return userDaoImpl.insertUser(email,password,name);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while creating user.", e);
        }
    }

    public Optional<User> updateUserInfo(User user) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        try {
            logger.debug("Service level update started.");
            User updated = userDaoImpl.update(user);
            return Optional.ofNullable(updated);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while updating user.", e);
        }
    }*/

