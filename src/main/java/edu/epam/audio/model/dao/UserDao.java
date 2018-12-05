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
import java.util.List;
import java.util.Optional;

//todo: ask DAO должны быть синглтонами?

public final class UserDao implements BaseDao<Long, User>{
    private static UserDao instance = new UserDao();

    private static final String ID = "user_id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "user_name";
    private static final String PHOTO = "photo";
    private static final String ROLE = "role";
    private static final String BONUS = "bonus";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM USER WHERE email=?";
    private static final String SELECT_PASSWORD_BY_EMAIL = "SELECT password FROM USER WHERE email=?";

    private UserDao(){}

    public static UserDao getInstance() {
        return instance;
    }

    public Optional<User> findUserByEmail(String email) throws DAOException {
        Optional<User> user = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        //todo: ask Это хороший AutoClosable?
        try(ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)){
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User userObject = new UserBuilder()
                                    .addId(resultSet.getLong(ID))
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
    }

    public Optional<String> findPasswordByEmail(String email) throws DAOException {
        Optional<String> password = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD_BY_EMAIL)){
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
            e.printStackTrace();
            throw new DAOException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntityById(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
