package edu.epam.audio.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//todo: add dynamic creation connections
public final class ConnectionPool {
    private static Logger logger = LogManager.getLogger();

    private static ConnectionPool instance;
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;

    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

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
        Properties properties = config.createProperties();

        for (int i = 0; i < size; i++) {
            ProxyConnection connection = createConnection(url, properties);
            connectionQueue.offer(connection);
        }
    }

    //todo: Этот метод не должен выкидывать InterruptedException
    public ProxyConnection getConnection() throws InterruptedException {
        return connectionQueue.take();
    }

    //todo: change method
    public void releaseConnection(ProxyConnection connection){
        connectionQueue.offer(connection);
    }
    
    private ProxyConnection createConnection(String url, Properties properties) throws SQLException {
        Connection connection = DriverManager.getConnection(url, properties);
        return new ProxyConnection(connection);
    }
}
