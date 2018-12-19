package edu.epam.audio.model.dao.impl;

import edu.epam.audio.model.dao.DBMetaInfo;
import edu.epam.audio.model.dao.UserDao;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Privileges;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.SongBuilder;
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

    private static final String INSERT_USER = "insert into USER(" + DBMetaInfo.EMAIL +
            ", " + DBMetaInfo.PASSWORD + ", " + DBMetaInfo.USER_NAME + ", " + DBMetaInfo.ROLE + ") values(?, ?, ?, 'USER')";

    private static final String SELECT_ALL_USERS = "select * from USER";
    private static final String SELECT_USER_BY_ID = "select * from USER where user_id=?";
    private static final String SELECT_USER_BY_EMAIL = "select * from USER where email=?";
    private static final String SELECT_USER_BY_NAME = "select * from USER where user_name=?";
    private static final String SELECT_PASSWORD_BY_EMAIL = "select password from USER where email=?";

    private static final String SELECT_ALL_USER_SONGS = "select * from USER natural join USER_SONG natural join SONG where user_id=?";
    private static final String SELECT_ALL_USER_ALBUMS = "SELECT * FROM USER NATURAL JOIN USER_ALBUM NATURAL JOIN ALBUM WHERE user_id=?";

    private static final String UPDATE_USER = "update USER set email=?, user_name=?, photo=? where user_id=?";

    private UserDaoImpl(){}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void create(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)){
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());

            preparedStatement.executeUpdate();
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
             Statement preparedStatement = connection.createStatement()){

            ResultSet resultSet = preparedStatement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()){
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
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

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
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

        try(ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)){
            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User userObject = new UserBuilder()
                                    .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                                    .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                                    .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                                    .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                                    .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
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
    public Optional<User> findUserByName(User user) throws DaoException{
        Optional<User> findUser = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME)){
            preparedStatement.setString(1, user.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User userObject = new UserBuilder()
                        .addId(resultSet.getLong(DBMetaInfo.USER_ID))
                        .addEmail(resultSet.getString(DBMetaInfo.EMAIL))
                        .addName(resultSet.getString(DBMetaInfo.USER_NAME))
                        .addPhoto(resultSet.getString(DBMetaInfo.PHOTO))
                        .addRole(Privileges.valueOf(resultSet.getString(DBMetaInfo.ROLE)))
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

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD_BY_EMAIL)){
            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
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
    public List<Song> findUserSongs(User user) throws DaoException {
        List<Song> songs = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER_SONGS)){
            preparedStatement.setLong(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Song songObject = new SongBuilder()
                        .addId(resultSet.getLong(DBMetaInfo.SONG_ID))
                        .addTitle(resultSet.getString(DBMetaInfo.SONG_TITLE))
                        .addPath(resultSet.getString(DBMetaInfo.PATH))
                        .addAlbumId(resultSet.getLong(DBMetaInfo.ALBUM_ID))
                        .addCost(resultSet.getDouble(DBMetaInfo.SONG_COST))
                        .build();

                songs.add(songObject);
            }
            return songs;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    //todo: add realization
    @Override
    public List<Album> findUserAlbums(User user) throws DaoException {
        return null;
    }

    @Override
    public void update(User entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)){
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getPhoto());
            preparedStatement.setLong(4, entity.getUserId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }
}
