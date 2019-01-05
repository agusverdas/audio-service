package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.AlbumDao;
import edu.epam.audio.dao.DbColumnNames;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.AlbumBuilder;
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

public final class AlbumDaoImpl implements AlbumDao {
    private static final AlbumDaoImpl instance = new AlbumDaoImpl();

    private static final String MAX_ALBUM_ID = "select MAX(" + DbColumnNames.ALBUM_ID + ") from ALBUM";
    private static final String INSERT_ALBUM = "insert into ALBUM(" + DbColumnNames.ALBUM_TITLE +
            ", " + DbColumnNames.ALBUM_AUTHOR_ID + ", " + DbColumnNames.ALBUM_PHOTO + ", " + DbColumnNames.ALBUM_COST + ") values(?, ?, ?, ?)";

    private static final String SELECT_ALL_ALBUMS = "select * from ALBUM";
    private static final String SELECT_ALBUM_BY_ID = "select * from ALBUM where " + DbColumnNames.ALBUM_ID + "=?";

    private static final String SELECT_USER_ALBUMS = "select * from USER natural join USER_ALBUM natural join ALBUM where " +
            DbColumnNames.USER_ID + "=?";

    private static final String SELECT_ALBUMS_BY_AUTHOR = "select * from ALBUM where " + DbColumnNames.ALBUM_AUTHOR_ID + "=?";
    private static final String UPDATE_ALBUM = "update ALBUM set " + DbColumnNames.ALBUM_TITLE + "=?, " + DbColumnNames.ALBUM_AUTHOR_ID +
            "=?, " + DbColumnNames.ALBUM_PHOTO + "=?, " + DbColumnNames.ALBUM_COST + "=? where " + DbColumnNames.ALBUM_ID + "=?";

    private AlbumDaoImpl(){}

    public static AlbumDaoImpl getInstance(){
        return instance;
    }

    @Override
    public long create(Album entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALBUM)){
            preparedStatement.setString(1, entity.getAlbumTitle());
            preparedStatement.setLong(2, entity.getAuthorId());
            preparedStatement.setString(3, entity.getPhoto());
            preparedStatement.setDouble(4, entity.getCost());

            preparedStatement.executeUpdate();
            long id = maxId(connection, MAX_ALBUM_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Album> findAll() throws DaoException {
        List<Album> albums = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ALBUMS);
            while (resultSet.next()){
                Album albumObject = new AlbumBuilder()
                        .addId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addTitle(resultSet.getString(DbColumnNames.ALBUM_TITLE))
                        .addAuthorId(resultSet.getLong(DbColumnNames.ALBUM_AUTHOR_ID))
                        .addPhoto(resultSet.getString(DbColumnNames.ALBUM_PHOTO))
                        .addCost(resultSet.getDouble(DbColumnNames.ALBUM_COST))
                        .build();

                albums.add(albumObject);
            }
            return albums;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public Optional<Album> findEntityById(long id) throws DaoException {
        Optional<Album> album = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALBUM_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Album albumObject = new AlbumBuilder()
                        .addId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addTitle(resultSet.getString(DbColumnNames.ALBUM_TITLE))
                        .addAuthorId(resultSet.getLong(DbColumnNames.ALBUM_AUTHOR_ID))
                        .addPhoto(resultSet.getString(DbColumnNames.ALBUM_PHOTO))
                        .addCost(resultSet.getDouble(DbColumnNames.ALBUM_COST))
                        .build();

                album = Optional.of(albumObject);
            }
            return album;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Album> findUserAlbums(User user) throws DaoException {
        List<Album> albums = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ALBUMS)){
            preparedStatement.setLong(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Album albumObject = new AlbumBuilder()
                        .addId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addTitle(resultSet.getString(DbColumnNames.ALBUM_TITLE))
                        .addAuthorId(resultSet.getLong(DbColumnNames.ALBUM_AUTHOR_ID))
                        .addPhoto(resultSet.getString(DbColumnNames.ALBUM_PHOTO))
                        .addCost(resultSet.getDouble(DbColumnNames.ALBUM_COST))
                        .build();

                albums.add(albumObject);
            }
            return albums;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public void update(Album entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ALBUM)){
            preparedStatement.setString(1, entity.getAlbumTitle());
            preparedStatement.setLong(2, entity.getAuthorId());
            preparedStatement.setString(3, entity.getPhoto());
            preparedStatement.setDouble(4, entity.getCost());
            preparedStatement.setDouble(5, entity.getAlbumId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public boolean delete(Album entity) throws DaoException {
        return false;
    }
}
