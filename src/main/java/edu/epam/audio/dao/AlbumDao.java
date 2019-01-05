package edu.epam.audio.dao;

import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.User;
import edu.epam.audio.exception.DaoException;

import java.util.List;

public interface AlbumDao extends BaseDao<Album> {
    List<Album> findUserAlbums(User user) throws DaoException;
}
