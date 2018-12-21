package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.DaoException;

import java.util.List;

//todo: add buy methods
public interface SongDao extends BaseDao<Song> {
    List<Song> findSongsByAuthor(Author author);
    List<Song> findSongsByAuthor(String author);
    List<Song> findSongsByAlbum(Album album) throws DaoException;
    List<Song> findUserSongs(User user) throws DaoException;

}
