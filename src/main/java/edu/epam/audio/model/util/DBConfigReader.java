package edu.epam.audio.model.util;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//todo: ask Нужен ли этот класс, есть метод getConnection(url, Properties)?
//todo: ask Нужен ли этот класс, если есть ServletContext?
//todo: ask Нужно ли называть классы, которые нужны для паттерна именем паттерна? Здесь DBConfigReaderSingleton.
//todo: ask Почему ExceptionInInitializerError это плохо?
//todo: add config params
public final class DBConfigReader {
    private static final String BUNDLE_NAME = "dbconfig";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";
    private static final String USER_PROPERTY = "db.user";
    private static final String PASSWORD_PROPERTY = "db.password";
    private static final String SIZE_PROPERTY = "db.poolsize";

    private static DBConfigReader instance;

    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean initialized = new AtomicBoolean(false);

    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private DBConfigReader() {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

        driver = bundle.getString(DRIVER_PROPERTY);
        url = bundle.getString(URL_PROPERTY);
        user = bundle.getString(USER_PROPERTY);
        password = bundle.getString(PASSWORD_PROPERTY);
        poolSize = Integer.parseInt(bundle.getString(SIZE_PROPERTY));
    }

    public static DBConfigReader getInstance(){
        if (!initialized.get()){
            try{
                lock.lock();
                if(instance == null){
                    instance = new DBConfigReader();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }

        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getDriver() {
        return driver;
    }
}
