package edu.epam.audio.model.dao.impl;

import edu.epam.audio.model.dao.AlbumDao;
import edu.epam.audio.model.dao.DBMetaInfo;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.AlbumBuilder;
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

public class AlbumDaoImpl implements AlbumDao {
    private static final String INSERT_ALBUM = "insert into ALBUM(" + DBMetaInfo.ALBUM_TITLE +
            ", " + DBMetaInfo.ALBUM_AUTHOR_ID + ", " + DBMetaInfo.ALBUM_PHOTO + ", " + DBMetaInfo.ALBUM_COST + ") values(?, ?, ?, ?)";

    private static final String SELECT_ALL_ALBUMS = "select * from ALBUM";
    private static final String SELECT_ALBUM_BY_ID = "select * from ALBUM where " + DBMetaInfo.ALBUM_ID + "=?";

    private static final String SELECT_USER_ALBUMS = "select * from USER natural join USER_ALBUM natural join ALBUM where " +
            DBMetaInfo.USER_ID + "=?";

    private static final String SELECT_ALBUMS_BY_AUTHOR = "select * from ALBUM where " + DBMetaInfo.ALBUM_AUTHOR_ID + "=?";
    private static final String UPDATE_ALBUM = "update ALBUM set " + DBMetaInfo.ALBUM_TITLE + "=?, " + DBMetaInfo.ALBUM_AUTHOR_ID +
            "=?, " + DBMetaInfo.ALBUM_PHOTO + "=?, " + DBMetaInfo.ALBUM_COST + "=? where " + DBMetaInfo.ALBUM_ID + "=?";

    @Override
    public void create(Album entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALBUM)){
            preparedStatement.setString(1, entity.getAlbumTitle());
            preparedStatement.setLong(2, entity.getAuthorId());
            preparedStatement.setString(3, entity.getPhoto());
            preparedStatement.setDouble(4, entity.getCost());

            preparedStatement.executeUpdate();
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
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
                        .addId(resultSet.getLong(DBMetaInfo.ALBUM_ID))
                        .addTitle(resultSet.getString(DBMetaInfo.ALBUM_TITLE))
                        .addAuthorId(resultSet.getLong(DBMetaInfo.ALBUM_AUTHOR_ID))
                        .addPhoto(resultSet.getString(DBMetaInfo.ALBUM_PHOTO))
                        .addCost(resultSet.getDouble(DBMetaInfo.ALBUM_COST))
                        .build();

                albums.add(albumObject);
            }
            return albums;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
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
                        .addId(resultSet.getLong(DBMetaInfo.ALBUM_ID))
                        .addTitle(resultSet.getString(DBMetaInfo.ALBUM_TITLE))
                        .addAuthorId(resultSet.getLong(DBMetaInfo.ALBUM_AUTHOR_ID))
                        .addPhoto(resultSet.getString(DBMetaInfo.ALBUM_PHOTO))
                        .addCost(resultSet.getDouble(DBMetaInfo.ALBUM_COST))
                        .build();

                album = Optional.of(albumObject);
            }
            return album;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Album> findAlbumsByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Album> findAlbumsByAuthor(String author) {
        return null;
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
                        .addId(resultSet.getLong(DBMetaInfo.ALBUM_ID))
                        .addTitle(resultSet.getString(DBMetaInfo.ALBUM_TITLE))
                        .addAuthorId(resultSet.getLong(DBMetaInfo.ALBUM_AUTHOR_ID))
                        .addPhoto(resultSet.getString(DBMetaInfo.ALBUM_PHOTO))
                        .addCost(resultSet.getDouble(DBMetaInfo.ALBUM_COST))
                        .build();

                albums.add(albumObject);
            }
            return albums;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
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
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public boolean delete(Album entity) throws DaoException {
        return false;
    }
}
