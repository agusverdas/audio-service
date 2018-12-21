package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;

import java.util.Optional;

//todo: add buy methods, method to add money
public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByEmail(User user) throws DaoException;
    Optional<User> findUserByName(User user) throws DaoException;

    String findPasswordByEmail(User user) throws DaoException;
}
