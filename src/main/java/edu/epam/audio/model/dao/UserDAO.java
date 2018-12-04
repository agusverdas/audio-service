package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.DAOException;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

//todo: ask DAO должны быть синглтонами?
//todo: read about other levels dao
public class UserDAO {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHOTO = "photo";
    private static final String ROLE = "role";
    private static final String BONUS = "bonus";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM db WHERE email=?";
    private static final String SELECT_PASSWORD_BY_EMAIL = "SELECT password FROM db WHERE email=?";

    private static Logger logger = LogManager.getLogger();

    //todo: ask findUserById, findUserByKey
    public Optional<User> findUserByEmail(String email) throws DAOException {
        Optional<User> user = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        //todo: ask Можно ли пользоваться прокси? Мешает параметризация.
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User userObject = new UserBuilder()
                                    .addEmail(resultSet.getString(EMAIL))
                                    .addName(resultSet.getString(NAME))
                                    .addPhoto(resultSet.getString(PHOTO))
                                    .addRole(resultSet.getString(ROLE))
                                    .addBonus(resultSet.getDouble(BONUS))
                                    .build();
                user = Optional.of(userObject);
            }

            return user;
        } catch (InterruptedException e) {
            throw new DAOException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DAOException("Exception while executing statement.", e);
        }
        finally {
            if (connection != null) {
                if (preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        //todo: ask Как это обрабатывать?
                        logger.warn("Prepared statement wasn't closed properly.", e);
                    }
                }
                pool.releaseConnection(connection);
            }
        }
    }

    public Optional<String> findPasswordByEmail(String email) throws DAOException {
        Optional<String> password = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = pool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PASSWORD_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String passwordObject = resultSet.getString(PASSWORD);
                password = Optional.of(passwordObject);
            }

            return password;
        } catch (InterruptedException e) {
            throw new DAOException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DAOException("Exception while executing statement.", e);
        }
        finally {
            if (connection != null) {
                if (preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        logger.warn("Prepared statement wasn't closed properly.", e);
                    }
                }
                pool.releaseConnection(connection);
            }
        }
    }
}
