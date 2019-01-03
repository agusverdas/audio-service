package edu.epam.audio.model.dao.impl;

import edu.epam.audio.model.dao.DBMetaInfo;
import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.entity.Privileges;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.UserBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();

    private static final String MAX_USER_ID = "select MAX(" + DBMetaInfo.USER_ID + ") from USER";
    private static final String INSERT_USER = "insert into USER(" + DBMetaInfo.EMAIL +
            ", " + DBMetaInfo.PASSWORD + ", " + DBMetaInfo.USER_NAME + ", " + DBMetaInfo.PHOTO + ", " + DBMetaInfo.ROLE +
            ", " + DBMetaInfo.MONEY + ", " + DBMetaInfo.BONUS + ") values(?, ?, ?, ?, 'USER', ?, ?)";
    private static final String INSERT_USER_BUY_SONG = "insert into USER_SONG(" + DBMetaInfo.USER_ID +
            ", " + DBMetaInfo.SONG_ID + ") values(?,?)";

    private static final String SELECT_ADMIN = "select * from USER where role='ADMIN'";
    private static final String SELECT_ALL_USERS = "select * from USER";
    private static final String SELECT_USER_BY_ID = "select * from USER where " + DBMetaInfo.USER_ID + "=?";
    private static final String SELECT_USER_BY_EMAIL = "select * from USER where " + DBMetaInfo.EMAIL + "=?";
    private static final String SELECT_USER_BY_NAME = "select * from USER where " + DBMetaInfo.USER_NAME + "=?";
    private static final String SELECT_PASSWORD_BY_EMAIL = "select " + DBMetaInfo.PASSWORD + " from USER where " + DBMetaInfo.EMAIL + "=?";

    private static final String SELECT_ALL_USER_ALBUMS = "SELECT * FROM USER NATURAL JOIN USER_ALBUM NATURAL JOIN ALBUM WHERE user_id=?";

    private static final String UPDATE_USER = "update USER set " + DBMetaInfo.EMAIL + "=?, " + DBMetaInfo.PASSWORD + "=?, "
            + DBMetaInfo.USER_NAME + "=?, " + DBMetaInfo.PHOTO + "=?, " + DBMetaInfo.ROLE + "=?, " + DBMetaInfo.MONEY + "=?, "
            + DBMetaInfo.BONUS + "=? where " + DBMetaInfo.USER_ID + "=?";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public long create(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getPhoto());
            preparedStatement.setDouble(5, user.getMoney());
            preparedStatement.setDouble(6, user.getBonus());

            preparedStatement.executeUpdate();
            return maxId(MAX_USER_ID);
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
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
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
                        .addMoney(resultSet.getDouble(DBMetaInfo.MONEY))
                        .addBonus(resultSet.getDouble(DBMetaInfo.BONUS))
                        .build();

                users.add(userObject);
            }
            return users;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
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
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addPassword(resultSet.getString(DBMetaInfo.PASSWORD))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
                        .addMoney(resultSet.getDouble(DBMetaInfo.MONEY))
                        .addBonus(resultSet.getDouble(DBMetaInfo.BONUS))
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
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addPassword(resultSet.getString(DBMetaInfo.PASSWORD))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
                        .addMoney(resultSet.getDouble(DBMetaInfo.MONEY))
                        .addBonus(resultSet.getDouble(DBMetaInfo.BONUS))
                        .build();

                findUser = Optional.of(userObject);
            }

            return findUser;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
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
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addPassword(resultSet.getString(DBMetaInfo.PASSWORD))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
                        .addMoney(resultSet.getDouble(DBMetaInfo.MONEY))
                        .addBonus(resultSet.getDouble(DBMetaInfo.BONUS))
                        .build();

                findUser = Optional.of(userObject);
            }

            return findUser;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public String findPasswordByEmail(User user) throws DaoException {
        String password = null;
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD_BY_EMAIL)) {
            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(DBMetaInfo.PASSWORD);
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
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    //todo: ask Транзакция с одним коннекшном
    @Override
    public void songPay(User user, Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        Statement selectAdmin = null;
        PreparedStatement adminUpdate = null;
        PreparedStatement userUpdate = null;
        PreparedStatement mergeSong = null;

        try (ProxyConnection connection = pool.getConnection()) {
            connection.setAutoCommit(false);
            selectAdmin = connection.createStatement();
            ResultSet resultSet = selectAdmin.executeQuery(SELECT_ADMIN);

            User admin;
            if (resultSet.next()){
                admin = new UserBuilder()
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addPassword(resultSet.getString(DBMetaInfo.PASSWORD))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
                        .addMoney(resultSet.getDouble(DBMetaInfo.MONEY))
                        .addBonus(resultSet.getDouble(DBMetaInfo.BONUS))
                        .build();
            } else {
                throw new DaoException("Can't find admin.");
            }

            double songCost = song.getCost();
            double adminMoney = admin.getMoney() + songCost;
            admin.setMoney(adminMoney);

            adminUpdate = connection.prepareStatement(UPDATE_USER);
            adminUpdate.setString(1, admin.getEmail());
            adminUpdate.setString(2, admin.getPassword());
            adminUpdate.setString(3, admin.getName());
            adminUpdate.setString(4, admin.getPhoto());
            adminUpdate.setString(5, admin.getRole().toString());
            adminUpdate.setDouble(6, admin.getMoney());
            adminUpdate.setDouble(7, admin.getBonus());
            adminUpdate.setDouble(8, admin.getUserId());
            adminUpdate.executeUpdate();

            userUpdate = connection.prepareStatement(UPDATE_USER);
            userUpdate.setString(1, user.getEmail());
            userUpdate.setString(2, user.getPassword());
            userUpdate.setString(3, user.getName());
            userUpdate.setString(4, user.getPhoto());
            userUpdate.setString(5, user.getRole().toString());
            userUpdate.setDouble(6, user.getMoney());
            userUpdate.setDouble(7, user.getBonus());
            userUpdate.setDouble(8, user.getUserId());
            userUpdate.executeUpdate();

            mergeSong = connection.prepareStatement(INSERT_USER_BUY_SONG);
            mergeSong.setLong(1, user.getUserId());
            mergeSong.setLong(2, song.getSongId());
            mergeSong.executeUpdate();

            connection.commit();
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        } finally {
            if (selectAdmin != null){
                try {
                    selectAdmin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (adminUpdate != null){
                try {
                    selectAdmin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (userUpdate != null){
                try {
                    selectAdmin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (mergeSong != null){
                try {
                    selectAdmin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }
}
