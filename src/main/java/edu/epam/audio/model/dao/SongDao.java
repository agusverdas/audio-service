package edu.epam.audio.model.dao;

import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;

import java.util.List;

//todo: add buy methods
public interface SongDao extends BaseDao<Song> {
    List<Song> findSongsByAuthor(Author author);
    List<Song> findSongsByAuthor(String author);

}
