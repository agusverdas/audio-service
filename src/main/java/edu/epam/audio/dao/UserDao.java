package edu.epam.audio.dao;

import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByEmail(User user) throws DaoException;
    Optional<User> findUserByName(User user) throws DaoException;
    void buySong(User user, Song song) throws DaoException;
    void buyAlbum(User user, Album album) throws DaoException;
}
