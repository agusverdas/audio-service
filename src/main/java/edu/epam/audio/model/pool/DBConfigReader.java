package edu.epam.audio.model.pool;

import java.util.Properties;
import java.util.ResourceBundle;

//todo: add config params

/*
useJDBCCompliantTimezoneShift=true
useLegacyDatetimeCode=false
serverTimezone=UTC
useSSL=true
serverSslCert=classpath:server.crt
*/

final class DBConfigReader {
    private static final String BUNDLE_NAME = "dbconfig";
    private static final String DRIVER_PROPERTY = "driver";
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String SIZE_PROPERTY = "poolsize";
    private static final String RECONNECT_PROPERTY = "autoReconnect";
    private static final String ENCODING_PROPERTY = "characterEncoding";
    private static final String UNICODE_PROPERTY = "useUnicode";
    private static final String SSL_PROPERTY = "useSSL";

    private static DBConfigReader instance;

    private String driver;
    private String url;
    private int poolSize;

    private DBConfigReader() {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

        driver = bundle.getString(DRIVER_PROPERTY);
        url = bundle.getString(URL_PROPERTY);
        poolSize = Integer.parseInt(bundle.getString(SIZE_PROPERTY));
    }

     static DBConfigReader getInstance(){
        if (instance == null){
            instance = new DBConfigReader();
        }
        return instance;
    }

    int getPoolSize() {
        return poolSize;
    }

    String getDriver() {
        return driver;
    }

    String getUrl() {
        return url;
    }

    public Properties createProperties(){
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

        Properties properties = new Properties();
        properties.setProperty(USER_PROPERTY, bundle.getString(USER_PROPERTY));
        properties.setProperty(PASSWORD_PROPERTY, bundle.getString(PASSWORD_PROPERTY));
        properties.setProperty(RECONNECT_PROPERTY, bundle.getString(RECONNECT_PROPERTY));
        properties.setProperty(ENCODING_PROPERTY, bundle.getString(ENCODING_PROPERTY));
        properties.setProperty(UNICODE_PROPERTY, bundle.getString(UNICODE_PROPERTY));
        properties.setProperty(SSL_PROPERTY, bundle.getString(SSL_PROPERTY));

        return properties;
    }
}
