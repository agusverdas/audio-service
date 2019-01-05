package edu.epam.audio.pool;

import java.util.Properties;
import java.util.ResourceBundle;

final class DbConfigReader {
    private static final String BUNDLE_NAME = "dbconfig";
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String SIZE_PROPERTY = "poolsize";
    private static final String RECONNECT_PROPERTY = "autoReconnect";
    private static final String ENCODING_PROPERTY = "characterEncoding";
    private static final String UNICODE_PROPERTY = "useUnicode";
    private static final String SSL_PROPERTY = "useSSL";

    private static DbConfigReader instance;

    private String url;
    private int poolSize;

    private DbConfigReader() {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        url = bundle.getString(URL_PROPERTY);
        poolSize = Integer.parseInt(bundle.getString(SIZE_PROPERTY));
    }

     static DbConfigReader getInstance(){
        if (instance == null){
            instance = new DbConfigReader();
        }
        return instance;
    }

    int getPoolSize() {
        return poolSize;
    }

    String getUrl() {
        return url;
    }

    Properties createProperties(){
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
