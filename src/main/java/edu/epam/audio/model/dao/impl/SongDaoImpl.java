package edu.epam.audio.model.dao.impl;

import edu.epam.audio.model.dao.DBMetaInfo;
import edu.epam.audio.model.dao.SongDao;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public final class SongDaoImpl implements SongDao {
    private static SongDaoImpl instance = new SongDaoImpl();

    private static final String INSERT_SONG = "insert into SONG values(" + DBMetaInfo.SONG_TITLE
            + ", " + DBMetaInfo.PATH + ", " + DBMetaInfo.SONG_COST + ") values(?, ?, ?)";

    private SongDaoImpl(){}

    public static SongDaoImpl getInstance(){
        return instance;
    }

    @Override
    public void create(Song entity) throws DaoException {

    }

    @Override
    public List<Song> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Song> findEntityById(long id) throws DaoException {
        return Optional.empty();
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
    public void update(Song entity) throws DaoException {

    }

    @Override
    public boolean delete(Song entity) throws DaoException {
        return false;
    }
}
