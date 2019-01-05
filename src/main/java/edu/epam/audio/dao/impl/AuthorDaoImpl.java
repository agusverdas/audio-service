package edu.epam.audio.dao.impl;

import edu.epam.audio.dao.AuthorDao;
import edu.epam.audio.dao.DbColumnNames;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.pool.ConnectionPool;
import edu.epam.audio.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AuthorDaoImpl implements AuthorDao {
    private static AuthorDaoImpl instance = new AuthorDaoImpl();

    private static final String MAX_AUTHOR_ID = "select MAX(" + DbColumnNames.AUTHOR_ID + ") from AUTHOR";
    private static final String INSERT_AUTHOR = "insert into AUTHOR(" + DbColumnNames.AUTHOR_NAME + ") values(?)";
    private static final String SELECT_BY_NAME = "select * from AUTHOR where "+ DbColumnNames.AUTHOR_NAME + "=?";
    private static final String SELECT_BY_SONG = "select * from AUTHOR natural join SONG_AUTHOR natural join SONG where " + DbColumnNames.SONG_ID + "=?";

    private AuthorDaoImpl(){}

    public static AuthorDaoImpl getInstance(){
        return instance;
    }

    @Override
    public long create(Author entity) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR)){
            preparedStatement.setString(1, entity.getName());

            preparedStatement.executeUpdate();
            long id = maxId(connection, MAX_AUTHOR_ID);
            connection.commit();
            connection.setAutoCommit(true);
            return id;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Author> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Author> findEntityById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Author> findAuthorByName(Author entity) throws DaoException {
        Optional<Author> author = Optional.empty();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)){
            preparedStatement.setString(1, entity.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Author authorFromDb = new Author();
                authorFromDb.setAuthorId(resultSet.getLong(DbColumnNames.AUTHOR_ID));
                authorFromDb.setName(resultSet.getString(DbColumnNames.AUTHOR_NAME));

                author = Optional.of(authorFromDb);
            }
            return author;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public List<Author> findAuthorsBySongId(Song entity) throws DaoException {
        List<Author> authorList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_SONG)){
            preparedStatement.setLong(1, entity.getSongId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Author authorFromDb = new Author();
                authorFromDb.setAuthorId(resultSet.getLong(DbColumnNames.AUTHOR_ID));
                authorFromDb.setName(resultSet.getString(DbColumnNames.AUTHOR_NAME));

                authorList.add(authorFromDb);
            }
            return authorList;
        } catch (SQLException e) {
            throw new DaoException("Exception while executing statement.", e);
        }
    }

    @Override
    public void update(Author entity) throws DaoException {

    }

    @Override
    public boolean delete(Author entity) throws DaoException {
        return false;
    }
}
