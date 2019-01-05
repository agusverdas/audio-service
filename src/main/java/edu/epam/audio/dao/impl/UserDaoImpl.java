package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.DbColumnNames;
import edu.epam.audio.dao.UserDao;
import edu.epam.audio.entity.Privileges;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.UserBuilder;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.pool.ConnectionPool;
import edu.epam.audio.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();

    private static final String MAX_USER_ID = "select MAX(" + DbColumnNames.USER_ID + ") from USER";
    private static final String INSERT_USER = "insert into USER(" + DbColumnNames.EMAIL +
            ", " + DbColumnNames.PASSWORD + ", " + DbColumnNames.USER_NAME + ", " + DbColumnNames.PHOTO + ", " + DbColumnNames.ROLE +
            ", " + DbColumnNames.MONEY + ", " + DbColumnNames.BONUS + ") values(?, ?, ?, ?, 'USER', ?, ?)";
    private static final String INSERT_USER_BUY_SONG = "insert into USER_SONG(" + DbColumnNames.USER_ID +
            ", " + DbColumnNames.SONG_ID + ") values(?,?)";

    private static final String SELECT_ADMIN = "select * from USER where role='ADMIN'";
    private static final String SELECT_ALL_USERS = "select * from USER";
    private static final String SELECT_USER_BY_ID = "select * from USER where " + DbColumnNames.USER_ID + "=?";
    private static final String SELECT_USER_BY_EMAIL = "select * from USER where " + DbColumnNames.EMAIL + "=?";
    private static final String SELECT_USER_BY_NAME = "select * from USER where " + DbColumnNames.USER_NAME + "=?";

    private static final String SELECT_ALL_USER_ALBUMS = "SELECT * FROM USER NATURAL JOIN USER_ALBUM NATURAL JOIN ALBUM WHERE user_id=?";

    private static final String UPDATE_USER = "update USER set " + DbColumnNames.EMAIL + "=?, " + DbColumnNames.PASSWORD + "=?, "
            + DbColumnNames.USER_NAME + "=?, " + DbColumnNames.PHOTO + "=?, " + DbColumnNames.ROLE + "=?, " + DbColumnNames.MONEY + "=?, "
            + DbColumnNames.BONUS + "=? where " + DbColumnNames.USER_ID + "=?";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    //todo: here transzaction
    @Override
    public long create(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getPhoto());
            preparedStatement.setDouble(5, user.getMoney());
            preparedStatement.setDouble(6, user.getBonus());

            preparedStatement.executeUpdate();
            long id = maxId(connection, MAX_USER_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DbColumnNames.USER_ID))
                        .addEmail(resultSet.getString(DbColumnNames.EMAIL))
                        .addName(resultSet.getString(DbColumnNames.USER_NAME))
                        .addPhoto(resultSet.getString(DbColumnNames.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DbColumnNames.ROLE)))
                        .addMoney(resultSet.getDouble(DbColumnNames.MONEY))
                        .addBonus(resultSet.getDouble(DbColumnNames.BONUS))
                        .build();

                users.add(userObject);
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        Optional<User> user = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DbColumnNames.USER_ID))
                        .addEmail(resultSet.getString(DbColumnNames.EMAIL))
                        .addPassword(resultSet.getString(DbColumnNames.PASSWORD))
                        .addName(resultSet.getString(DbColumnNames.USER_NAME))
                        .addPhoto(resultSet.getString(DbColumnNames.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DbColumnNames.ROLE)))
                        .addMoney(resultSet.getDouble(DbColumnNames.MONEY))
                        .addBonus(resultSet.getDouble(DbColumnNames.BONUS))
                        .build();

                user = Optional.of(userObject);
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(User user) throws DaoException {
        Optional<User> findUser = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DbColumnNames.USER_ID))
                        .addEmail(resultSet.getString(DbColumnNames.EMAIL))
                        .addPassword(resultSet.getString(DbColumnNames.PASSWORD))
                        .addName(resultSet.getString(DbColumnNames.USER_NAME))
                        .addPhoto(resultSet.getString(DbColumnNames.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DbColumnNames.ROLE)))
                        .addMoney(resultSet.getDouble(DbColumnNames.MONEY))
                        .addBonus(resultSet.getDouble(DbColumnNames.BONUS))
                        .build();

                findUser = Optional.of(userObject);
            }

            return findUser;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public Optional<User> findUserByName(User user) throws DaoException {
        Optional<User> findUser = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
            preparedStatement.setString(1, user.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DbColumnNames.USER_ID))
                        .addEmail(resultSet.getString(DbColumnNames.EMAIL))
                        .addPassword(resultSet.getString(DbColumnNames.PASSWORD))
                        .addName(resultSet.getString(DbColumnNames.USER_NAME))
                        .addPhoto(resultSet.getString(DbColumnNames.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DbColumnNames.ROLE)))
                        .addMoney(resultSet.getDouble(DbColumnNames.MONEY))
                        .addBonus(resultSet.getDouble(DbColumnNames.BONUS))
                        .build();

                findUser = Optional.of(userObject);
            }

            return findUser;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public void update(User entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setString(4, entity.getPhoto());
            preparedStatement.setString(5, entity.getRole().toString());
            preparedStatement.setDouble(6, entity.getMoney());
            preparedStatement.setDouble(7, entity.getBonus());
            preparedStatement.setDouble(8, entity.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public void songPay(User user, Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement selectAdmin = connection.createStatement();
             PreparedStatement adminUpdate = connection.prepareStatement(UPDATE_USER);
             PreparedStatement userUpdate = connection.prepareStatement(UPDATE_USER);
             PreparedStatement mergeSong = connection.prepareStatement(INSERT_USER_BUY_SONG)) {

            connection.setAutoCommit(false);
            ResultSet resultSet = selectAdmin.executeQuery(SELECT_ADMIN);

            User admin;
            if (resultSet.next()){
                admin = new UserBuilder()
                        .addId(resultSet.getLong(DbColumnNames.USER_ID))
                        .addEmail(resultSet.getString(DbColumnNames.EMAIL))
                        .addPassword(resultSet.getString(DbColumnNames.PASSWORD))
                        .addName(resultSet.getString(DbColumnNames.USER_NAME))
                        .addPhoto(resultSet.getString(DbColumnNames.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DbColumnNames.ROLE)))
                        .addMoney(resultSet.getDouble(DbColumnNames.MONEY))
                        .addBonus(resultSet.getDouble(DbColumnNames.BONUS))
                        .build();
            } else {
                throw new DaoException("Can't find admin.");
            }

            double songCost = song.getCost();
            double adminMoney = admin.getMoney() + songCost;
            admin.setMoney(adminMoney);

            adminUpdate.setString(1, admin.getEmail());
            adminUpdate.setString(2, admin.getPassword());
            adminUpdate.setString(3, admin.getName());
            adminUpdate.setString(4, admin.getPhoto());
            adminUpdate.setString(5, admin.getRole().toString());
            adminUpdate.setDouble(6, admin.getMoney());
            adminUpdate.setDouble(7, admin.getBonus());
            adminUpdate.setDouble(8, admin.getUserId());
            adminUpdate.executeUpdate();

            userUpdate.setString(1, user.getEmail());
            userUpdate.setString(2, user.getPassword());
            userUpdate.setString(3, user.getName());
            userUpdate.setString(4, user.getPhoto());
            userUpdate.setString(5, user.getRole().toString());
            userUpdate.setDouble(6, user.getMoney());
            userUpdate.setDouble(7, user.getBonus());
            userUpdate.setDouble(8, user.getUserId());
            userUpdate.executeUpdate();

            mergeSong.setLong(1, user.getUserId());
            mergeSong.setLong(2, song.getSongId());
            mergeSong.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }
}
