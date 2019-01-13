package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.UserDao;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Privileges;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.UserBuilder;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.pool.ConnectionPool;
import edu.epam.audio.pool.ProxyConnection;

import javax.swing.text.html.Option;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.dao.DbColumnNames.*;

public final class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();

    private static final String MAX_USER_ID = "select MAX(" + USER_ID + ") from USER";
    private static final String INSERT_USER = "insert into USER(" + EMAIL + ", "
            + PASSWORD + ", " + USER_NAME + ", " + PHOTO + ", " + ROLE + ", "
            + MONEY + ", " + BONUS + ") values(?, ?, ?, ?, 'USER', ?, ?)";
    private static final String INSERT_USER_BUY_SONG = "insert into USER_SONG(" + USER_ID + ", "
            + SONG_ID + ") values(?,?)";
    private static final String INSERT_USER_BUY_ALBUM = "insert into USER_ALBUM(" + USER_ID + ", "
            + ALBUM_ID + ") values(?, ?)";
    private static final String SELECT_ADMIN = "select * from USER where " + ROLE + "='ADMIN'";
    private static final String SELECT_ALL_USERS = "select * from USER where " + USER_DELETED + "=0";
    private static final String SELECT_USER_BY_ID = "select * from USER where " + USER_ID + "=? AND "
            + USER_DELETED + "=0";
    private static final String SELECT_USER_BY_EMAIL = "select * from USER where " + EMAIL + "=? AND "
            + USER_DELETED + "=0";
    private static final String SELECT_USER_BY_NAME = "select * from USER where " + USER_NAME + "=? AND "
            + USER_DELETED + "=0";
    private static final String UPDATE_USER = "update USER set " + EMAIL + "=?, " + PASSWORD + "=?, "
            + USER_NAME + "=?, " + PHOTO + "=?, " + ROLE + "=?, " + MONEY + "=?, "
            + BONUS + "=? where " + USER_ID + "=?";
    private static final String UPDATE_DELETE = "update USER set " + USER_DELETED + "=1 where " + USER_ID + "=?";

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

    private Optional<User> buildUser(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()){
            user = new UserBuilder()
                    .addId(resultSet.getLong(USER_ID))
                    .addEmail(resultSet.getString(EMAIL))
                    .addPassword(resultSet.getString(PASSWORD))
                    .addName(resultSet.getString(USER_NAME))
                    .addPhoto(resultSet.getString(PHOTO))
                    .addRole(Privileges.valueOf(resultSet.getString(ROLE)))
                    .addMoney(resultSet.getDouble(MONEY))
                    .addBonus(resultSet.getDouble(BONUS))
                    .build();
        }

        return Optional.ofNullable(user);
    }

    private List<User> buildUserList(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()){
            User user = new UserBuilder()
                    .addId(resultSet.getLong(USER_ID))
                    .addEmail(resultSet.getString(EMAIL))
                    .addPassword(resultSet.getString(PASSWORD))
                    .addName(resultSet.getString(USER_NAME))
                    .addPhoto(resultSet.getString(PHOTO))
                    .addRole(Privileges.valueOf(resultSet.getString(ROLE)))
                    .addMoney(resultSet.getDouble(MONEY))
                    .addBonus(resultSet.getDouble(BONUS))
                    .build();

            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> findAll() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);

            return buildUserList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding all users.", e);
        }
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding user by id.", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding user by email.", e);
        }
    }

    @Override
    public Optional<User> findUserByName(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
            preparedStatement.setString(1, user.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            return buildUser(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding user by name.", e);
        }
    }

    private void buildUserUpdate(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getPhoto());
        preparedStatement.setString(5, user.getRole().toString());
        preparedStatement.setDouble(6, user.getMoney());
        preparedStatement.setDouble(7, user.getBonus());
        preparedStatement.setDouble(8, user.getUserId());
    }

    @Override
    public void update(User entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            System.out.println("ENTITY : " + entity);
            buildUserUpdate(preparedStatement, entity);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating user.", e);
        }
    }

    @Override
    public void buySong(User user, Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement selectAdmin = connection.createStatement();
             PreparedStatement adminUpdate = connection.prepareStatement(UPDATE_USER);
             PreparedStatement userUpdate = connection.prepareStatement(UPDATE_USER);
             PreparedStatement mergeSong = connection.prepareStatement(INSERT_USER_BUY_SONG)) {
            connection.setAutoCommit(false);
            ResultSet resultSet = selectAdmin.executeQuery(SELECT_ADMIN);
            Optional<User> adminOptional = buildUser(resultSet);
            if (!adminOptional.isPresent()){
                throw new DaoException("Can't find admin.");
            }

            User admin = adminOptional.get();
            double adminMoney = admin.getMoney() + song.getCost();
            admin.setMoney(adminMoney);

            buildUserUpdate(adminUpdate, admin);
            adminUpdate.executeUpdate();
            buildUserUpdate(userUpdate, user);
            userUpdate.executeUpdate();

            mergeSong.setLong(1, user.getUserId());
            mergeSong.setLong(2, song.getSongId());
            mergeSong.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Exception while buying song.", e);
        }
    }

    @Override
    public void buyAlbum(User user, Album album) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement selectAdmin = connection.createStatement();
             PreparedStatement adminUpdate = connection.prepareStatement(UPDATE_USER);
             PreparedStatement userUpdate = connection.prepareStatement(UPDATE_USER);
             PreparedStatement mergeAlbum = connection.prepareStatement(INSERT_USER_BUY_ALBUM);
             PreparedStatement mergeSong = connection.prepareStatement(INSERT_USER_BUY_SONG)) {
            connection.setAutoCommit(false);
            ResultSet resultSet = selectAdmin.executeQuery(SELECT_ADMIN);
            Optional<User> adminOptional = buildUser(resultSet);
            if (!adminOptional.isPresent()){
                throw new DaoException("Can't find admin.");
            }

            User admin = adminOptional.get();
            double adminMoney = admin.getMoney() + album.getCost();
            admin.setMoney(adminMoney);

            buildUserUpdate(adminUpdate, admin);
            adminUpdate.executeUpdate();
            buildUserUpdate(userUpdate, user);
            userUpdate.executeUpdate();

            List<Song> songs = album.getSongs();
            for (Song song: songs) {
                mergeSong.setLong(1, user.getUserId());
                mergeSong.setLong(2, song.getSongId());
                mergeSong.executeUpdate();
            }

            mergeAlbum.setLong(1, user.getUserId());
            mergeAlbum.setLong(2, album.getAlbumId());
            mergeAlbum.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Exception while buying album.", e);
        }
    }

    @Override
    public void delete(User entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DELETE)) {
            preparedStatement.setLong(1, entity.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting user.", e);
        }
    }
}
