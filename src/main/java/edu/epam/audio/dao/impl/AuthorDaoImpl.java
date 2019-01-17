package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.AuthorDao;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
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

public final class AuthorDaoImpl implements AuthorDao {
    private static AuthorDaoImpl instance = new AuthorDaoImpl();

    private static final String MAX_AUTHOR_ID = "select MAX(" + AUTHOR_ID + ") from AUTHOR";
    private static final String INSERT_AUTHOR = "insert into AUTHOR(" + AUTHOR_NAME + ") values(?)";
    private static final String SELECT_ALL_AUTHORS = "select * from AUTHOR";
    private static final String SELECT_AUTHOR_BY_ID = "select * from AUTHOR where " + AUTHOR_ID + "=?";
    private static final String SELECT_BY_NAME = "select * from AUTHOR where "+ AUTHOR_NAME + "=?";
    private static final String SELECT_BY_SONG = "select * from AUTHOR natural join SONG_AUTHOR natural join SONG where "
            + SONG_ID + "=?";
    private static final String UPDATE_AUTHOR = "update AUTHOR set " + AUTHOR_NAME + "=? where " + AUTHOR_ID + "=?";
    private AuthorDaoImpl(){}

    public static AuthorDaoImpl getInstance(){
        return instance;
    }

    /**
     * СОздание альбома
     * @param entity Альбом
     * @return id нового альбома
     * @throws DaoException
     */
    @Override
    public long create(Author entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, entity.getName());

            preparedStatement.executeUpdate();
            long id = maxId(connection, MAX_AUTHOR_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while creating author.", e);
        }
    }

    /**
     * Создание автора по ResultSet
     * @param resultSet Результат запроса
     * @return Опционал автора
     * @throws SQLException
     */
    private Optional<Author> buildAuthor(ResultSet resultSet) throws SQLException {
        Author author = null;
        if(resultSet.next()){
            author = new Author();
            author.setAuthorId(resultSet.getLong(AUTHOR_ID));
            author.setName(resultSet.getString(AUTHOR_NAME));
        }
        return Optional.ofNullable(author);
    }

    /**
     * Создание списка авторов по ResultSet
     * @param resultSet Результат запроса
     * @return Список аторов
     * @throws SQLException
     */
    private List<Author> buildAuthorList(ResultSet resultSet) throws SQLException {
        List<Author> authors = new ArrayList<>();
        while (resultSet.next()){
            Author author = new Author();
            author.setAuthorId(resultSet.getLong(AUTHOR_ID));
            author.setName(resultSet.getString(AUTHOR_NAME));

            authors.add(author);
        }
        return authors;
    }

    /**
     * Выбор всех аторов
     * @return Список аторов
     * @throws DaoException
     */
    @Override
    public List<Author> findAll() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_AUTHORS);

            return buildAuthorList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding all authors.", e);
        }
    }

    /**
     * Выбор автора ао id
     * @param id id Автора
     * @return Опционал автора
     * @throws DaoException
     */
    @Override
    public Optional<Author> findEntityById(long id) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildAuthor(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding author by id.", e);
        }
    }

    /**
     * Выбор автора по имени
     * @param entity Автор
     * @return Опционал автора
     * @throws DaoException
     */
    @Override
    public Optional<Author> findAuthorByName(Author entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)){
            preparedStatement.setString(1, entity.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildAuthor(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding author by name.", e);
        }
    }

    /**
     * ВЫбор авторов песни
     * @param entity Песня
     * @return Авторы
     * @throws DaoException
     */
    @Override
    public List<Author> findAuthorsBySong(Song entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_SONG)){
            preparedStatement.setLong(1, entity.getSongId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return buildAuthorList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception while finding authors by song.", e);
        }
    }

    /**
     * Обновление автора
     * @param entity Автор
     * @throws DaoException
     */
    @Override
    public void update(Author entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR)){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getAuthorId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while updating author.", e);
        }
    }

    /**
     * Удаление автора
     * @param entity Автор
     * @throws DaoException
     */
    @Override
    public void delete(Author entity) throws DaoException {
        throw new UnsupportedOperationException("You can't delete authors.");
    }
}
