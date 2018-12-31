package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Entity;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    default long maxId(String maxIdSql) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        long id = 1;

        try(ProxyConnection connection = pool.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(maxIdSql);
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }
}
