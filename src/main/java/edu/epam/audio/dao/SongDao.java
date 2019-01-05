package edu.epam.audio.dao;

import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.exception.DaoException;

import java.util.List;

//todo: add buy methods
public interface SongDao extends BaseDao<Song> {
    List<Song> findSongsByAlbum(Album album) throws DaoException;
    List<Song> findUserSongs(User user) throws DaoException;
    void mergeSongAuthor(Song song, Author author) throws DaoException;
}
