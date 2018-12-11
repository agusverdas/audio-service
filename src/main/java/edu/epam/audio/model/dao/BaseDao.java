package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Entity;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

//todo: autoclosable
public interface BaseDao<K, T extends Entity> {
    Logger logger = LogManager.getLogger();

    List<T> findAll();
    T findEntityById(K id);
    boolean delete(K id);
    boolean delete(T entity);
    boolean create(T entity);
    T update(T entity);
}
