package edu.epam.audio.model.pool;

import edu.epam.audio.model.util.DBConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//todo: ask Должен ли он быть синглтоном?
//todo: ask Нужна ли в этом классе параметризация?
//todo: ask Из-за того, что класс синглтон, с ленивой инициализацией, инициализация в методе init() сервлета происходит с помощью getInstance.

//todo: add dynamic creation connections
public final class ConnectionPool {
    private static Logger logger = LogManager.getLogger();

    private static ConnectionPool instance;
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;

    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

    //todo: ask Можно ли кидать тут runtime.
    //todo: ask Параметризация самого синглтона.
    public static ConnectionPool getInstance(){
        if (!initialized.get()){
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    initialized.set(true);
                }
            } catch (SQLException e) {
                logger.fatal("Error while creating connection pool.", e);
                throw new RuntimeException("Error while creating connection pool.", e);
            } catch (ClassNotFoundException e) {
                logger.fatal("Error while registering driver.", e);
                throw new RuntimeException("Error while registering driver.", e);
            } finally {
                lock.unlock();
            }
        }

        return instance;
    }

    private ConnectionPool() throws SQLException, ClassNotFoundException {
        DBConfigReader config = DBConfigReader.getInstance();

        Class.forName(config.getDriver());
        int poolSize = config.getPoolSize();

        logger.debug("Pool size : " + poolSize);

        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        init(poolSize);
    }

    private void init(int size) throws SQLException {
        DBConfigReader config = DBConfigReader.getInstance();

        String url = config.getUrl();
        String user = config.getUser();
        String password = config.getPassword();

        logger.debug("URL : " + url);
        logger.debug("USER : " + user);
        logger.debug("Password : " + password);

        for (int i = 0; i < size; i++) {
            ProxyConnection connection = createConnection(url, user, password);
            connectionQueue.offer(connection);
        }
    }


    public ProxyConnection getConnection() throws InterruptedException {
        return connectionQueue.take();
    }

    //todo: ask В каком случае могут возникнуть проблемы с возвращением конекшена.
    //todo: change method
    public void releaseConnection(ProxyConnection connection){
        connectionQueue.offer(connection);
    }
    
    private ProxyConnection createConnection(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return (new ProxyConnection(connection));
    }
}
