package edu.epam.audio.model.dao.impl;

import edu.epam.audio.model.dao.DBMetaInfo;
import edu.epam.audio.model.dao.SongDao;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.SongBuilder;
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

//todo: change field names in db
public final class SongDaoImpl implements SongDao {
    private static SongDaoImpl instance = new SongDaoImpl();

    private static final String INSERT_SONG = "insert into SONG(" + DBMetaInfo.SONG_TITLE + ", " + DBMetaInfo.PATH + ", "
            + DBMetaInfo.SONG_COST + ") values(?, ?, ?)";
    private static final String SELECT_ALL_SONGS = "select * from SONG";
    private static final String SELECT_SONG_BY_ID = "select * from SONG where " + DBMetaInfo.SONG_ID + "=?";
    private static final String SELECT_SONGS_BY_AUTHOR = "select * from SONG natural join SONG_AUTHOR natural join AUTHOR where "
            + DBMetaInfo.AUTHOR_NAME + "=?";
    private static final String SELECT_SONGS_BY_ALBUM = "select * from SONG where " + DBMetaInfo.ALBUM_ID + "=?";
    private static final String SELECT_ALL_USER_SONGS = "select * from USER natural join USER_SONG natural join SONG where " + DBMetaInfo.USER_ID + "=?";
    private static final String UPDATE_SONG = "update SONG set " + DBMetaInfo.SONG_TITLE + "=?, "
            + DBMetaInfo.PATH + "=?, " + DBMetaInfo.ALBUM_ID + "=?, " + DBMetaInfo.SONG_COST + "=? where "
            + DBMetaInfo.SONG_ID + "=?";

    private SongDaoImpl(){}

    public static SongDaoImpl getInstance(){
        return instance;
    }

    @Override
    public void create(Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SONG)){
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getPath());
            preparedStatement.setDouble(3, song.getCost());

            preparedStatement.executeUpdate();
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Song> findAll() throws DaoException {
        List<Song> songs = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SONGS);
            while (resultSet.next()){
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
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public Optional<Song> findEntityById(long id) throws DaoException {
        Optional<Song> song = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONG_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Song songObject = new SongBuilder()
                        .addId(resultSet.getLong(DBMetaInfo.SONG_ID))
                        .addTitle(resultSet.getString(DBMetaInfo.SONG_TITLE))
                        .addPath(resultSet.getString(DBMetaInfo.PATH))
                        .addAlbumId(resultSet.getLong(DBMetaInfo.ALBUM_ID))
                        .addCost(resultSet.getDouble(DBMetaInfo.SONG_COST))
                        .build();

                song = Optional.of(songObject);
            }
            return song;
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Song> findSongsByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Song> findSongsByAuthor(String author) {
        return null;
    }

    @Override
    public List<Song> findSongsByAlbum(Album album) throws DaoException {
        List<Song> songs = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONGS_BY_ALBUM)){
            preparedStatement.setLong(1, album.getAlbumId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
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

    @Override
    public void update(Song entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SONG)){
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getPath());
            preparedStatement.setLong(3, entity.getAlbumId());
            preparedStatement.setDouble(4, entity.getCost());
            preparedStatement.setDouble(5, entity.getSongId());

            preparedStatement.executeUpdate();
        } catch (InterruptedException e) {
            throw new DaoException("Exception while getting connection from connection pool.", e);
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public boolean delete(Song entity) throws DaoException {
        return false;
    }
}
