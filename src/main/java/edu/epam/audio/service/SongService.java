package edu.epam.audio.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.dao.AuthorDao;
import edu.epam.audio.dao.SongDao;
import edu.epam.audio.dao.impl.AuthorDaoImpl;
import edu.epam.audio.dao.impl.SongDaoImpl;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.SongBuilder;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.util.UploadPath;

import javax.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.util.RequestAttributes.ATTRIBUTE_NAME_ERROR;
import static edu.epam.audio.util.RequestParams.*;

public class SongService {
    private static final String INCORRECT_COST = "Cost should be decimal";

    public void addSong(String title, String[] authorParams, String cost, String path, Part part) throws ServiceException {
        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        SongDao songDao = SongDaoImpl.getInstance();
        List<String> authorNames = Arrays.asList(authorParams);
        List<Author> authorsList = new ArrayList<>();
        authorNames.forEach(name -> {
            Author author = new Author();
            author.setName(name.trim());
            authorsList.add(author);
        });
        try {
            List<Author> authors = new ArrayList<>();
            for (Author author: authorsList) {
                Optional<Author> authorOptional = authorDao.findAuthorByName(author);
                if (!authorOptional.isPresent()){
                    authorDao.create(author);
                    authorOptional = authorDao.findAuthorByName(author);
                }
                authors.add(authorOptional.get());
            }
            Song song = new SongBuilder()
                    .addTitle(title)
                    .addCost(Double.parseDouble(cost))
                    .build();

            File fileSaveDir = new File(path);
            if(!fileSaveDir.exists()){
                fileSaveDir.mkdirs();
            }

            String uploadPath = UploadPath.uploadSong(path, part);
            if (uploadPath != null) {
                song.setPath(uploadPath);
            }

            long id = songDao.create(song);
            Optional<Song> songFromDb = songDao.findEntityById(id);
            if (!songFromDb.isPresent()){
                throw new DaoException("No such song in db.");
            }
            song = songFromDb.get();
            songDao.mergeSongAuthor(song, authors);
        } catch (DaoException e) {
            throw new ServiceException("Exception in adding song to db.", e);
        }
    }

    private void loadSongAuthor(Song song) throws DaoException {
        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        List<Author> authors = authorDao.findAuthorsBySong(song);
        song.setAuthorList(authors);
    }

    public Song loadSong(long songId) throws ServiceException {
        SongDao songDao = SongDaoImpl.getInstance();
        try {
            Optional<Song> songOptional = songDao.findEntityById(songId);
            if (songOptional.isPresent()) {
                Song song = songOptional.get();
                loadSongAuthor(song);
                return song;
            } else {
                throw new ServiceException("No such song in db.");
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while loading song.", e);
        }
    }

    public List<Song> loadAllSongs() throws ServiceException {
        SongDao songDao = SongDaoImpl.getInstance();
        try {
            List<Song> songs = songDao.findAll();
            for (Song song: songs) {
               loadSongAuthor(song);
            }
            return songs;
        } catch (DaoException e) {
            throw new ServiceException("Exception while loading songs.", e);
        }
    }

    public List<Song> loadUserSongs(User user) throws ServiceException {
        SongDao songDao = SongDaoImpl.getInstance();
        try {
            List<Song> songs = songDao.findUserSongs(user);
            for (Song song: songs) {
                loadSongAuthor(song);
            }
            return songs;
        } catch (DaoException e) {
            throw new ServiceException("Exception while loading user's songs.", e);
        }
    }

    public void updateSong(RequestContent content) throws ServiceException {
        SongDao songDao = SongDaoImpl.getInstance();
        long id = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        try {
            Optional<Song> songOptional = songDao.findEntityById(id);
            if (!songOptional.isPresent()){
                throw new DaoException("No such song in db.");
            }
            Song song = songOptional.get();
            String costStr = content.getRequestParam(PARAM_NAME_COST);
            if (costStr != null && !costStr.isEmpty()){
                double cost = Double.parseDouble(content.getRequestParam(PARAM_NAME_COST));
                song.setCost(cost);
            }
            String path = (String) content.getRequestAttribute(PARAM_NAME_PATH);
            Part part = content.getRequestPart(PARAM_NAME_SONG);
            String loadPath = UploadPath.uploadSong(path, part);
            if (loadPath != null) {
                song.setPath(loadPath);
            }
            songDao.update(song);
        } catch (DaoException e) {
            throw new ServiceException("Exception in updating song from db.", e);
        } catch (NumberFormatException e){
            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_COST);
        }
    }
}
