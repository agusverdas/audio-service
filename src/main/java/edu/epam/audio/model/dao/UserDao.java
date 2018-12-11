package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Privileges;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;

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

    private static final String INSERT_USER = "INSERT INTO USER(" + EMAIL +
            ", " + PASSWORD + ", " + NAME + ", " + ROLE + ") values(?, ?, ?, 'USER')";
    private UserDao(){}

    public static UserDao getInstance() {
        return instance;
    }

    //todo: check cases when row can't be inserted
    public Optional<User> insertUser(String email, String password, String name) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)){
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();
            return findUserByEmail(email);
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    public Optional<User> findUserByEmail(String email) throws DaoException {
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
                                    .addRole(Privileges.valueOf(resultSet.getString(ROLE)))
                                    .addBonus(resultSet.getDouble(BONUS))
                                    .build();

                user = Optional.of(userObject);
            }

            return user;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    public Optional<String> findPasswordByEmail(String email) throws DaoException {
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
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Exception while executing statement.", e);
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
