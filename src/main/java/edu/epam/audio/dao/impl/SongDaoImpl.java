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
    private static final String SELECT_SONGS_NOT_IN_ALBUM = "select * from SONG where " + ALBUM_ID + " IS NULL AND " + SONG_DELETED + "=0";
    private static final String SELECT_SONGS_BY_USER = "select * from USER natural join USER_SONG natural join SONG where "
            + USER_ID + "=?";
    private static final String SELECT_SONG_ALBUM = "select " + ALBUM_ID + " from SONG where " + SONG_ID + "=?";
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

    /**
     * Создание песни
     * @param song Песня
     * @return id Песни
     * @throws DaoException
     */
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

    /**
     * Создание пенсни на основе ResultSet
     * @param resultSet Результат запроса
     * @return Опционал песни
     * @throws SQLException
     */
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

    /**
     * Создание списка песен на основе запроса
     * @param resultSet Результат запроса
     * @return Список песен
     * @throws SQLException
     */
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

    /**
     * Соединение песен и авторов
     * @param song Песня
     * @param authors Список авторов
     * @throws DaoException
     */
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

    /**
     * ВЫборка всех песен
     * @return Список песен
     * @throws DaoException
     */
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

    /**
     * Поиск песен не в альбоме
     * @return Список песен
     * @throws DaoException
     */
    @Override
    public List<Song> findSongsNotInAlbum() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_SONGS_NOT_IN_ALBUM);
            return buildSongList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding not in album songs.", e);
        }
    }

    /**
     * Поиск песни по id
     * @param id id Песни
     * @return Опционал песни
     * @throws DaoException
     */
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

    /**
     * Поиск песен по альбому
     * @param album Альбом
     * @return Список песен
     * @throws DaoException
     */
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

    /**
     * Поиск песен пользователя
     * @param user Пользователь
     * @return Список песен
     * @throws DaoException
     */
    @Override
    public List<Song> findUserSongs(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONGS_BY_USER)) {
            preparedStatement.setLong(1, user.getUserId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildSongList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding user songs.", e);
        }
    }

    /**
     * Поиск альбома песни
     * @param song Песня
     * @return id Альбома
     * @throws DaoException
     */
    @Override
    public long findSongAlbum(Song song) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (ProxyConnection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONG_ALBUM)) {
            preparedStatement.setLong(1, song.getSongId());

            long albumId = 0;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                albumId = resultSet.getLong(1);
            }
            return albumId;
        } catch (SQLException e) {
            throw new DaoException("Exception while finding song album.", e);
        }
    }

    /**
     * СОединение песни и альбома
     * @param album Альбом
     * @throws DaoException
     */
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

    /**
     * Обновление песни
     * @param entity Песня
     * @throws DaoException
     */
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

    /**
     * Удаление песни
     * @param entity Песня
     * @throws DaoException
     */
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
