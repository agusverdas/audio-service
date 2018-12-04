package edu.epam.audio.model.command.service;

import edu.epam.audio.model.dao.UserDAO;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DAOException;
import edu.epam.audio.model.exception.LogicLayerException;

import java.util.Optional;

//todo: ask Куда положить пакет.
//todo: ask В этот класс можно поместить много методов для пользователя или создать отдельные классы для отдельных подзадач пользователя?
public class UserServiceLogin {
    public boolean checkPassword(String email, String password) throws LogicLayerException {
        UserDAO userDAO = new UserDAO();

        try {
            Optional<String> queryPassword = userDAO.findPasswordByEmail(email);
            return queryPassword.isPresent() && queryPassword.get().equals(password);
        } catch (DAOException e) {
            //todo: ask Правильно я понял подход с уровневыми исключениями?
            throw new LogicLayerException("Exception while checking password", e);
        }
    }

    public Optional<User> createUser(String email) throws LogicLayerException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.findUserByEmail(email);
        } catch (DAOException e) {
            throw new LogicLayerException("Exception while creating user.", e);
        }
    }
}
