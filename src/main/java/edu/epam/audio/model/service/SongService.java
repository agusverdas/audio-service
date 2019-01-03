package edu.epam.audio.model.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.dao.AlbumDao;
import edu.epam.audio.model.dao.AuthorDao;
import edu.epam.audio.model.dao.SongDao;
import edu.epam.audio.model.dao.impl.AlbumDaoImpl;
import edu.epam.audio.model.dao.impl.AuthorDaoImpl;
import edu.epam.audio.model.dao.impl.SongDaoImpl;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.SongBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.SessionAttributes;
import edu.epam.audio.model.util.UploadPath;

import javax.servlet.http.Part;
import java.io.File;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.model.util.RequestAttributes.ATTRIBUTE_NAME_ERROR;
import static edu.epam.audio.model.util.RequestParams.*;

public class SongService {
    private static final String INCORRECT_COST = "Cost should be decimal";

    public void addSong(RequestContent content) throws LogicLayerException {
        String title = content.getRequestParam(PARAM_NAME_TITLE);
        String author  = content.getRequestParam(PARAM_NAME_AUTHOR);
        Author authorObject = new Author();
        authorObject.setName(author);

        String cost = content.getRequestParam(PARAM_NAME_COST);
        String path = (String) content.getRequestAttribute(PARAM_NAME_PATH);

        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        SongDao songDao = SongDaoImpl.getInstance();

        try {
            Optional<Author> authorOptional = authorDao.findAuthorByName(authorObject);
            if (!authorOptional.isPresent()){
                authorDao.create(authorObject);
                authorOptional = authorDao.findAuthorByName(authorObject);
            }

            authorObject = authorOptional.get();
            Song song = new SongBuilder()
                    .addTitle(title)
                    .addCost(Double.parseDouble(cost))
                    .build();

            File fileSaveDir = new File(path);
            if(!fileSaveDir.exists()){
                fileSaveDir.mkdirs();
            }

            Part part = content.getRequestPart(PARAM_NAME_SONG);

            String loadPath = UploadPath.uploadSong(path, part);
            if (loadPath != null) {
                song.setPath(loadPath);
            }

            long id = songDao.create(song);
            Optional<Song> songFromDb = songDao.findEntityById(id);
            song = songFromDb.get();

            songDao.mergeSongAuthor(song, authorObject);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting objects from db.", e);
        }
    }

    public List<Song> loadAllSongs() throws LogicLayerException {
        SongDao songDao = SongDaoImpl.getInstance();
        AuthorDao authorDao = AuthorDaoImpl.getInstance();

        try {
            List<Song> songs = songDao.findAll();
            for (Song s: songs) {
                List<Author> authors = authorDao.findAuthorsBySongId(s);
                s.setAuthorList(authors);
            }
            return songs;
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading songs.", e);
        }
    }

    public Song loadSong(long songId) throws LogicLayerException {
        SongDao songDao = SongDaoImpl.getInstance();
        AuthorDao authorDao = AuthorDaoImpl.getInstance();

        try {
            //todo: change id methods to return objects, not optionals
            Optional<Song> songOptional = songDao.findEntityById(songId);
            Song song = songOptional.get();
            List<Author> authors = authorDao.findAuthorsBySongId(song);
            song.setAuthorList(authors);
            return song;
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading song.", e);
        }
    }

    public List<Song> findUserSongs(RequestContent content) throws LogicLayerException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        SongDao songDao = SongDaoImpl.getInstance();
        try {
            return songDao.findUserSongs(user);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading songs.", e);
        }
    }

    public void updateSong(RequestContent content) throws LogicLayerException {
        SongDao songDao = SongDaoImpl.getInstance();
        long id = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));

        try {
            Optional<Song> songOptional = songDao.findEntityById(id);
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
            throw new LogicLayerException("Exception in getting song from db.", e);
        } catch (NumberFormatException e){
            content.setRequestAttribute(ATTRIBUTE_NAME_ERROR, INCORRECT_COST);
        }
    }
}
