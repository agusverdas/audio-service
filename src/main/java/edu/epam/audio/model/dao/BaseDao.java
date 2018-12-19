package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Entity;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends Entity> {
    void create(T entity) throws DaoException;

    List<T> findAll() throws DaoException;
    Optional<T> findEntityById(long id) throws DaoException;

    void update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;
}
