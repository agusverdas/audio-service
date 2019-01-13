package edu.epam.audio.dao;

import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends BaseDao<Author> {
    Optional<Author> findAuthorByName(Author entity) throws DaoException;
    List<Author> findAuthorsBySong(Song entity) throws DaoException;
}
