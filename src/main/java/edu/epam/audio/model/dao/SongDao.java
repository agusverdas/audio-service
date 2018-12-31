package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

//todo: add buy methods
public interface SongDao extends BaseDao<Song> {
    List<Song> findSongsByAuthor(Author author);
    List<Song> findSongsByAuthor(String author);
    List<Song> findSongsByAlbum(Album album) throws DaoException;
    List<Song> findUserSongs(User user) throws DaoException;
    void mergeSongAuthor(Song song, Author author) throws DaoException;
    Optional<Song> findSongByPath(Song entity) throws DaoException;

}
