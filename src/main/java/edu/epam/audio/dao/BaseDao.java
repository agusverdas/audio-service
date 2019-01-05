package edu.epam.audio.dao;

import edu.epam.audio.entity.Entity;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.pool.ConnectionPool;
import edu.epam.audio.pool.ProxyConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends Entity> {
    long create(T entity) throws DaoException;

    List<T> findAll() throws DaoException;
    Optional<T> findEntityById(long id) throws DaoException;

    void update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

    default long maxId(Connection connection, String maxIdSql) throws DaoException {
        long id = 1;

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(maxIdSql);
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }
}
