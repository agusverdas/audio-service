package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends BaseDao<Author> {
    Optional<Author> findAuthorByName(Author entity) throws DaoException;
    List<Author> findAuthorsBySongId(Song entity) throws DaoException;
}
