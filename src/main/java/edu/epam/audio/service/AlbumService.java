package edu.epam.audio.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.dao.AlbumDao;
import edu.epam.audio.dao.AuthorDao;
import edu.epam.audio.dao.impl.AlbumDaoImpl;
import edu.epam.audio.dao.impl.AuthorDaoImpl;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.AlbumBuilder;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.util.SessionAttributes;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static edu.epam.audio.util.RequestParams.*;
import static edu.epam.audio.util.UploadPath.*;

//todo: rework
public class AlbumService {
    public void addAlbum(RequestContent wrapper) throws LogicLayerException {
        String title = wrapper.getRequestParam(PARAM_NAME_TITLE);
        String author  = wrapper.getRequestParam(PARAM_NAME_AUTHOR);
        Author authorObject = new Author();
        authorObject.setName(author);

        String path = (String) wrapper.getRequestAttribute(PARAM_NAME_PATH);

        //todo: validation
        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        AlbumDao albumDao = AlbumDaoImpl.getInstance();

        try {
            Optional<Author> authorOptional = authorDao.findAuthorByName(authorObject);
            if (!authorOptional.isPresent()){
                authorDao.create(authorObject);
                authorOptional = authorDao.findAuthorByName(authorObject);
            }

            Album album = new AlbumBuilder()
                    .addTitle(title)
                    .build();

            File fileSaveDir = new File(path);
            if(!fileSaveDir.exists()){
                fileSaveDir.mkdirs();
            }

            String formedPath;
            Part part = wrapper.getRequestPart(PARAM_NAME_PHOTO);

            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                formedPath = path + File.separator + part.getSubmittedFileName();
                part.write(formedPath);
                String pathToLoad = PATH_TO_SAVE + UPLOAD_PHOTOS_DIR
                        + PATH_DELIMITER + part.getSubmittedFileName();
                album.setPhoto(pathToLoad);
            }

            authorObject = authorOptional.get();

            album.setAuthorId(authorObject.getAuthorId());
            long id = albumDao.create(album);
            Optional<Album> albumFromDb = albumDao.findEntityById(id);
            album = albumFromDb.get();

            //todo: add method
            //songDao.mergeSongAuthor(song, authorObject);*/
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting objects from db.", e);
        } catch (IOException e) {
            throw new LogicLayerException("Exception in writing song.", e);
        }
    }

    public List<Album> loadAllAlbums() throws LogicLayerException {
        AlbumDao albumDao = AlbumDaoImpl.getInstance();

        try {
            return albumDao.findAll();
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading albums.", e);
        }
    }

    public List<Album> findUserAlbums(RequestContent content) throws LogicLayerException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        AlbumDao albumDao = AlbumDaoImpl.getInstance();
        try {
            return albumDao.findUserAlbums(user);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading songs.", e);
        }
    }
}
