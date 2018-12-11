package edu.epam.audio.model.service;

import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;

import java.util.Optional;

//todo: encrypt passwords
public class UserService {
    public boolean checkUserByEmail(String email) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        boolean isUserPresent;
        try {
            isUserPresent = userDao.findUserByEmail(email).isPresent();
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while checking user", e);
        }

        return isUserPresent;
    }
    
    public boolean checkPassword(String email, String password) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try {
            Optional<String> queryPassword = userDao.findPasswordByEmail(email);
            return queryPassword.isPresent() && queryPassword.get().equals(password);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while checking password", e);
        }
    }

    public Optional<User> extractUser(String email) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try {
            return userDao.findUserByEmail(email);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while extracting user.", e);
        }
    }

    public Optional<User> extractUser(long id) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try {
            return userDao.findEntityById(id);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while extracting user.", e);
        }
    }

    public Optional<User> extractByName(String name) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try{
            return userDao.findUserByName(name);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while finding user by name.", e);
        }
    }

    //todo: ask Метод относится к бизгнес логике, но по сути просто обращается к DAO.
    public Optional<User> createUser(String email, String password, String name) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try {
            return userDao.insertUser(email,password,name);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while creating user.");
        }
    }

    //todo: ask МОжно ли переделать так, чтобы посылать какое-то сообщение уровню команды?
    public Optional<User> updateUserInfo(String email, String name) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();
        try {
            if (Optional.of(userDao.findPasswordByEmail(email)).isPresent()) {
                return Optional.empty();
            }
            if (Optional.of(userDao.findUserByName(name)).isPresent()) {

            }
        } catch (DaoException e){

        }
        return null;
    }

}
