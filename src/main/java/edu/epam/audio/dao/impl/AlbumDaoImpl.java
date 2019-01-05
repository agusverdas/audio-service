package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.AlbumDao;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
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

import static edu.epam.audio.dao.DbColumnNames.*;

public final class AlbumDaoImpl implements AlbumDao {
    private static final AlbumDaoImpl instance = new AlbumDaoImpl();

    private static final String MAX_ALBUM_ID = "select MAX(" + ALBUM_ID + ") from ALBUM";
    private static final String INSERT_ALBUM = "insert into ALBUM(" + ALBUM_TITLE + ", "
            + ALBUM_AUTHOR_ID + ", " + ALBUM_PHOTO + ", "
            + ALBUM_COST + ") values(?, ?, ?, ?)";

    private static final String SELECT_ALL_ALBUMS = "select * from ALBUM where " + ALBUM_DELETED + "=0";
    private static final String SELECT_ALBUM_BY_ID = "select * from ALBUM where " + ALBUM_ID + "=? AND "
            + ALBUM_DELETED + "=0";

    private static final String SELECT_USER_ALBUMS = "select * from USER natural join USER_ALBUM natural join ALBUM where "
            + USER_ID + "=?";

    private static final String UPDATE_ALBUM = "update ALBUM set " + ALBUM_TITLE + "=?, "
            + ALBUM_AUTHOR_ID + "=?, " + ALBUM_PHOTO + "=?, "
            + ALBUM_COST + "=? where " + ALBUM_ID + "=?";

    private static final String UPDATE_DELETE = "update ALBUM set " + ALBUM_DELETED + "=1 where "
            + ALBUM_ID + "=?";

    private AlbumDaoImpl(){}

    public static AlbumDaoImpl getInstance(){
        return instance;
    }

    //todo: think уровень изоляции
    @Override
    public long create(Album entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALBUM)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, entity.getAlbumTitle());
            preparedStatement.setLong(2, entity.getAuthor().getAuthorId());
            preparedStatement.setString(3, entity.getPhoto());
            preparedStatement.setDouble(4, entity.getCost());

            preparedStatement.executeUpdate();
            long id = maxId(connection, MAX_ALBUM_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating album.", e);
        }
    }

    private Optional<Album> buildAlbum(ResultSet resultSet) throws SQLException {
        Album album = null;
        if(resultSet.next()){
            Author author = new Author();
            long authorId = resultSet.getLong(ALBUM_AUTHOR_ID);
            author.setAuthorId(authorId);

            album = new AlbumBuilder()
                    .addId(resultSet.getLong(ALBUM_ID))
                    .addTitle(resultSet.getString(ALBUM_TITLE))
                    .addAuthor(author)
                    .addSongs(new ArrayList<>())
                    .addPhoto(resultSet.getString(ALBUM_PHOTO))
                    .addCost(resultSet.getDouble(ALBUM_COST))
                    .build();
        }
        return Optional.ofNullable(album);
    }

    private List<Album> buildAlbumList(ResultSet resultSet) throws SQLException {
        List<Album> albums = new ArrayList<>();
        while (resultSet.next()){
            Author author = new Author();
            long authorId = resultSet.getLong(ALBUM_AUTHOR_ID);
            author.setAuthorId(authorId);

            Album albumObject = new AlbumBuilder()
                    .addId(resultSet.getLong(ALBUM_ID))
                    .addTitle(resultSet.getString(ALBUM_TITLE))
                    .addAuthor(author)
                    .addSongs(new ArrayList<>())
                    .addPhoto(resultSet.getString(ALBUM_PHOTO))
                    .addCost(resultSet.getDouble(ALBUM_COST))
                    .build();

            albums.add(albumObject);
        }
        return albums;
    }

    @Override
    public List<Album> findAll() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ALBUMS);
            return buildAlbumList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding all the albums.", e);
        }
    }

    @Override
    public Optional<Album> findEntityById(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALBUM_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildAlbum(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding album by id.", e);
        }
    }

    @Override
    public List<Album> findUserAlbums(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ALBUMS)){
            preparedStatement.setLong(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();

            return buildAlbumList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding user albums.", e);
        }
    }

    @Override
    public void update(Album entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ALBUM)){
            preparedStatement.setString(1, entity.getAlbumTitle());
            preparedStatement.setLong(2, entity.getAuthor().getAuthorId());
            preparedStatement.setString(3, entity.getPhoto());
            preparedStatement.setDouble(4, entity.getCost());
            preparedStatement.setDouble(5, entity.getAlbumId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating album.", e);
        }
    }

    @Override
    public void delete(Album entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DELETE)){
            preparedStatement.setLong(1, entity.getAlbumId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting album.", e);
        }
    }
}
