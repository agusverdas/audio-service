package edu.epam.audio.model.service;

import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DAOException;
import edu.epam.audio.model.exception.LogicLayerException;

import java.util.Optional;

public class UserService {
    public boolean checkPassword(String email, String password) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try {
            Optional<String> queryPassword = userDao.findPasswordByEmail(email);
            return queryPassword.isPresent() && queryPassword.get().equals(password);
        } catch (DAOException e) {
            throw new LogicLayerException("Exception while checking password", e);
        }
    }

    public Optional<User> createUser(String email) throws LogicLayerException {
        UserDao userDao = UserDao.getInstance();

        try {
            return userDao.findUserByEmail(email);
        } catch (DAOException e) {
            throw new LogicLayerException("Exception while creating user.", e);
        }
    }
}
