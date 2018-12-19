package edu.epam.audio.model.service;

import edu.epam.audio.model.dao.impl.UserDaoImpl;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

//todo: encrypt passwords
public class UserService {
    private static Logger logger = LogManager.getLogger();

    public boolean checkUserByEmail(String email) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        boolean isUserPresent;
        try {
            isUserPresent = userDaoImpl.findUserByEmail(email).isPresent();
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while checking user", e);
        }

        return isUserPresent;
    }
    
    public boolean checkPassword(String email, String password) throws LogicLayerException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();

        try {
            Optional<String> queryPassword = userDaoImpl.findPasswordByEmail(email);
            return queryPassword.isPresent() && queryPassword.get().equals(password);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while checking password", e);
        }
    }

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
    }

}
