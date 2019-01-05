package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.DbColumnNames;
import edu.epam.audio.dao.SongDao;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.SongBuilder;
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

public final class SongDaoImpl implements SongDao {
    private static SongDaoImpl instance = new SongDaoImpl();

    private static final String MAX_SONG_ID = "select MAX(" + DbColumnNames.SONG_ID + ") from SONG";
    private static final String INSERT_SONG = "insert into SONG(" + DbColumnNames.SONG_TITLE + ", " + DbColumnNames.PATH + ", "
            + DbColumnNames.SONG_COST + ") values(?, ?, ?)";
    private static final String INSERT_SONG_AUTHOR = "insert into SONG_AUTHOR(" + DbColumnNames.SONG_ID + ", " + DbColumnNames.AUTHOR_ID
            + ") values(?,?)";

    private static final String SELECT_ALL_SONGS = "select * from SONG";
    private static final String SELECT_SONG_BY_ID = "select * from SONG where " + DbColumnNames.SONG_ID + "=?";
    private static final String SELECT_SONGS_BY_AUTHOR = "select * from SONG natural join SONG_AUTHOR natural join AUTHOR where "
            + DbColumnNames.AUTHOR_NAME + "=?";
    private static final String SELECT_SONGS_BY_ALBUM = "select * from SONG where " + DbColumnNames.ALBUM_ID + "=?";
    private static final String SELECT_ALL_USER_SONGS = "select * from USER natural join USER_SONG natural join SONG where " + DbColumnNames.USER_ID + "=?";
    private static final String UPDATE_SONG = "update SONG set " + DbColumnNames.SONG_TITLE + "=?, "
            + DbColumnNames.PATH + "=?, " + DbColumnNames.SONG_COST + "=? where " + DbColumnNames.SONG_ID + "=?";

    private SongDaoImpl(){}

    public static SongDaoImpl getInstance(){
        return instance;
    }

    @Override
    public long create(Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SONG)){
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getPath());
            preparedStatement.setDouble(3, song.getCost());

            preparedStatement.executeUpdate();
            long id = maxId(connection,MAX_SONG_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public void mergeSongAuthor(Song song, Author author) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SONG_AUTHOR)){
            preparedStatement.setLong(1, song.getSongId());
            preparedStatement.setLong(2, author.getAuthorId());

            preparedStatement.executeUpdate();
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
                        .addId(resultSet.getLong(DbColumnNames.SONG_ID))
                        .addTitle(resultSet.getString(DbColumnNames.SONG_TITLE))
                        .addPath(resultSet.getString(DbColumnNames.PATH))
                        .addAlbumId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addCost(resultSet.getDouble(DbColumnNames.SONG_COST))
                        .build();

                songs.add(songObject);
            }
            return songs;
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
                        .addId(resultSet.getLong(DbColumnNames.SONG_ID))
                        .addTitle(resultSet.getString(DbColumnNames.SONG_TITLE))
                        .addPath(resultSet.getString(DbColumnNames.PATH))
                        .addAlbumId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addCost(resultSet.getDouble(DbColumnNames.SONG_COST))
                        .build();

                song = Optional.of(songObject);
            }
            return song;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
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
                        .addId(resultSet.getLong(DbColumnNames.SONG_ID))
                        .addTitle(resultSet.getString(DbColumnNames.SONG_TITLE))
                        .addPath(resultSet.getString(DbColumnNames.PATH))
                        .addAlbumId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addCost(resultSet.getDouble(DbColumnNames.SONG_COST))
                        .build();

                songs.add(songObject);
            }
            return songs;
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
            while (resultSet.next()){
                Song songObject = new SongBuilder()
                        .addId(resultSet.getLong(DbColumnNames.SONG_ID))
                        .addTitle(resultSet.getString(DbColumnNames.SONG_TITLE))
                        .addPath(resultSet.getString(DbColumnNames.PATH))
                        .addAlbumId(resultSet.getLong(DbColumnNames.ALBUM_ID))
                        .addCost(resultSet.getDouble(DbColumnNames.SONG_COST))
                        .build();

                songs.add(songObject);
            }
            return songs;
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
            preparedStatement.setDouble(3, entity.getCost());
            preparedStatement.setDouble(4, entity.getSongId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public boolean delete(Song entity) throws DaoException {
        return false;
    }


}
