package edu.epam.audio.model.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.dao.AuthorDao;
import edu.epam.audio.model.dao.SongDao;
import edu.epam.audio.model.dao.impl.AuthorDaoImpl;
import edu.epam.audio.model.dao.impl.SongDaoImpl;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.entity.builder.impl.SongBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.SessionAttributes;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.model.util.RequestParams.*;
import static edu.epam.audio.model.util.UploadPath.*;

public class SongService {
    public void addSong(RequestContent wrapper) throws LogicLayerException {
        String title = wrapper.getRequestParam(PARAM_NAME_TITLE);
        String author  = wrapper.getRequestParam(PARAM_NAME_AUTHOR);
        Author authorObject = new Author();
        authorObject.setName(author);

        String cost = wrapper.getRequestParam(PARAM_NAME_COST);
        String path = (String) wrapper.getRequestAttribute(PARAM_NAME_PATH);

        //todo: validation
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

            String formedPath;
            Part part = wrapper.getRequestPart(PARAM_NAME_SONG);

            //todo: WHAT SYMBOLS EXCEPT SPACES CAN BE BAD THERE
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                formedPath = path + File.separator + part.getSubmittedFileName();
                part.write(formedPath.replaceAll(SYMBOL_TO_REPLACE, PATH_REPLACEMENT));
                String pathToLoad = PATH_TO_SAVE + UPLOAD_SONGS_DIR
                        + PATH_DELIMITER + part.getSubmittedFileName();
                song.setPath(pathToLoad.replaceAll(SYMBOL_TO_REPLACE, PATH_REPLACEMENT));
            }

            long id = songDao.create(song);
            Optional<Song> songFromDb = songDao.findEntityById(id);
            song = songFromDb.get();

            songDao.mergeSongAuthor(song, authorObject);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting objects from db.", e);
        } catch (IOException e) {
            throw new LogicLayerException("Exception in writing song.", e);
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

    public List<Song> findUserSongs(RequestContent content) throws LogicLayerException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        SongDao songDao = SongDaoImpl.getInstance();
        try {
            return songDao.findUserSongs(user);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading songs.", e);
        }
    }
}
