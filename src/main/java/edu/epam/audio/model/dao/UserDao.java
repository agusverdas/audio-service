package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

//todo: add buy methods
public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByEmail(User user) throws DaoException;
    Optional<User> findUserByName(User user) throws DaoException;

    String findPasswordByEmail(User user) throws DaoException;

    List<Song> findUserSongs(User user) throws DaoException;
    List<Album> findUserAlbums(User user) throws DaoException;
}
