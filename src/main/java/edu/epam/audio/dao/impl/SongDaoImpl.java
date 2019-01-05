package edu.epam.audio.dao.impl;

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

import static edu.epam.audio.dao.DbColumnNames.*;

public final class SongDaoImpl implements SongDao {
    private static SongDaoImpl instance = new SongDaoImpl();

    private static final String MAX_SONG_ID = "select MAX(" + SONG_ID + ") from SONG";
    private static final String INSERT_SONG = "insert into SONG(" + SONG_TITLE + ", " + PATH
            + ", " + SONG_COST + ") values(?, ?, ?)";
    private static final String INSERT_SONG_AUTHOR = "insert into SONG_AUTHOR(" + SONG_ID + ", "
            + AUTHOR_ID + ") values(?,?)";

    private static final String SELECT_ALL_SONGS = "select * from SONG where " + SONG_DELETED + "=0";
    private static final String SELECT_SONG_BY_ID = "select * from SONG where " + SONG_ID + "=? AND "
            + SONG_DELETED + "=0";
    private static final String SELECT_SONGS_BY_ALBUM = "select * from SONG where " + ALBUM_ID + "=? AND " + SONG_DELETED + "=0";
    private static final String SELECT_SONGS_BY_USER = "select * from USER natural join USER_SONG natural join SONG where "
            + USER_ID + "=?";
    private static final String UPDATE_SONG = "update SONG set " + SONG_TITLE + "=?, "
            + PATH + "=?, " + SONG_COST + "=? where " + SONG_ID + "=?";
    private static final String UPDATE_SONG_ALBUM = "update SONG set " + ALBUM_ID + "=? "
            + " where " + SONG_ID + "=?";
    private static final String UPDATE_DELETE = "update SONG set " + SONG_DELETED + "=1 where " + SONG_ID + "=?";

    private SongDaoImpl() {
    }

    public static SongDaoImpl getInstance() {
        return instance;
    }

    //todo: think уровень изоляции транзакции
    @Override
    public long create(Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SONG)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getPath());
            preparedStatement.setDouble(3, song.getCost());

            preparedStatement.executeUpdate();
            long id = maxId(connection, MAX_SONG_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating song.", e);
        }
    }

    private Optional<Song> buildSong(ResultSet resultSet) throws SQLException {
        Song song = null;
        if (resultSet.next()) {
            song = new SongBuilder()
                    .addId(resultSet.getLong(SONG_ID))
                    .addTitle(resultSet.getString(SONG_TITLE))
                    .addPath(resultSet.getString(PATH))
                    .addCost(resultSet.getDouble(SONG_COST))
                    .build();
        }
        return Optional.ofNullable(song);
    }

    private List<Song> buildSongList(ResultSet resultSet) throws SQLException {
        List<Song> songs = new ArrayList<>();
        while (resultSet.next()) {
            Song song = new SongBuilder()
                    .addId(resultSet.getLong(SONG_ID))
                    .addTitle(resultSet.getString(SONG_TITLE))
                    .addPath(resultSet.getString(PATH))
                    .addCost(resultSet.getDouble(SONG_COST))
                    .build();

            songs.add(song);
        }
        return songs;
    }

    @Override
    public void mergeSongAuthor(Song song, List<Author> authors) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SONG_AUTHOR)) {
            for (Author author : authors) {
                preparedStatement.setLong(1, song.getSongId());
                preparedStatement.setLong(2, author.getAuthorId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while merging song with authors.", e);
        }
    }

    @Override
    public List<Song> findAll() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SONGS);
            return buildSongList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding all the songs.", e);
        }
    }

    @Override
    public Optional<Song> findEntityById(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONG_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildSong(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding song by id.", e);
        }
    }

    @Override
    public List<Song> findSongsByAlbum(Album album) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONGS_BY_ALBUM)) {
            preparedStatement.setLong(1, album.getAlbumId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildSongList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding songs by album.", e);
        }
    }

    @Override
    public List<Song> findUserSongs(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONGS_BY_USER)) {
            preparedStatement.setLong(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildSongList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Exception while finding user songs.", e);
        }
    }

    @Override
    public void mergeSongAlbum(Album album) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SONG_ALBUM)) {
            for (Song song : album.getSongs()) {
                preparedStatement.setLong(1, album.getAlbumId());
                preparedStatement.setLong(2, song.getSongId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while merging song with album.", e);
        }
    }

    @Override
    public void update(Song entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SONG)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getPath());
            preparedStatement.setDouble(3, entity.getCost());
            preparedStatement.setDouble(4, entity.getSongId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating song.", e);
        }
    }

    @Override
    public void delete(Song entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DELETE)) {
            preparedStatement.setLong(1, entity.getSongId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while deleting song.", e);
        }
    }
}
