package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;

import java.util.List;

//todo: add buy methods
public interface AlbumDao extends BaseDao<Album> {
    List<Album> findAlbumsByAuthor(Author author);
    List<Album> findAlbumsByAuthor(String author);
    List<Album> findUserAlbums(User user) throws DaoException;
}
