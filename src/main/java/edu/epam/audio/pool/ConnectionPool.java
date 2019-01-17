package edu.epam.audio.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Пул соединений к базе данных
 */
public final class ConnectionPool {
    private static Logger logger = LogManager.getLogger();

    private static volatile ConnectionPool instance;
    private BlockingQueue<ProxyConnection> connectionQueue;
    private Set<ProxyConnection> usedConnections;

    private static Lock lock = new ReentrantLock();

    /**
     * Получение объекта пула
     * @return Объект пула
     */
    public static ConnectionPool getInstance(){
        if (instance == null){
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } catch (SQLException e) {
                logger.fatal("Error while creating connection pool.", e);
                throw new RuntimeException("Error while creating connection pool.", e);
            } finally {
                lock.unlock();
            }
        }

        return instance;
    }

    /**
     *
     * @throws SQLException
     */
    private ConnectionPool() throws SQLException {
        DbConfigReader config = DbConfigReader.getInstance();
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        int poolSize = config.getPoolSize();
        connectionQueue = new LinkedBlockingQueue<>(poolSize);
        usedConnections = new ConcurrentSkipListSet<>();
        init(poolSize);
    }

    /**
     * Метод инициализации пула
     * @param size Размер пула
     * @throws SQLException
     */
    private void init(int size) throws SQLException {
        DbConfigReader config = DbConfigReader.getInstance();
        String url = config.getUrl();
        Properties properties = config.createProperties();
        for (int i = 0; i < size; i++) {
            ProxyConnection connection = createConnection(url, properties);
            connectionQueue.offer(connection);
        }
    }

    /**
     * Получение соединения с базой данных
     * @return Соединение
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            logger.error("Error while taking connection from pool.", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Возвращение соединения в пул
     * @param connection Соединение
     */
    void releaseConnection(ProxyConnection connection){
        usedConnections.remove(connection);
        try {
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            logger.error("Error while placing connection back.", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Создание соединения
     * @param url URL базы данных
     * @param properties настройки соединения
     * @return Соединение
     * @throws SQLException
     */
    private ProxyConnection createConnection(String url, Properties properties) throws SQLException {
        Connection connection = DriverManager.getConnection(url, properties);
        return new ProxyConnection(connection);
    }

    /**
     * Уничтожение пула
     */
    public void destroy() {
        connectionQueue.forEach(ProxyConnection::destroy);
        usedConnections.forEach(ProxyConnection::destroy);
        deregisterDriver();
    }

    /**
     * Дерегистрация драйверов
     */
    private void deregisterDriver(){
        Enumeration<Driver> enumDrivers = DriverManager.getDrivers();
        while (enumDrivers.hasMoreElements()) {
            Driver driver = enumDrivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Error while deregistring drivers.", e);
                throw new RuntimeException("Error while deregistring drivers.", e);
            }
        }
    }
}
