package edu.epam.audio.model.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.dao.impl.AuthorDaoImpl;
import edu.epam.audio.model.dao.impl.SongDaoImpl;
import edu.epam.audio.model.entity.Author;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.builder.impl.SongBuilder;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.WebValuesNames;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SongService {
    public void addSong(RequestContent wrapper) throws LogicLayerException {
        String title = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_TITLE);
        String author  = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_AUTHOR);
        Author authorObject = new Author();
        authorObject.setName(author);

        String cost = wrapper.getRequestParam(WebValuesNames.PARAM_NAME_COST);
        String path = (String) wrapper.getRequestAttribute(WebValuesNames.PARAM_NAME_PATH);

        //todo: validation
        AuthorDaoImpl authorDao = AuthorDaoImpl.getInstance();
        SongDaoImpl songDao = SongDaoImpl.getInstance();

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
            Part part = wrapper.getRequestPart(WebValuesNames.PARAM_NAME_SONG);

            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                formedPath = path + File.separator + part.getSubmittedFileName();
                part.write(formedPath);
                String pathToLoad = WebValuesNames.PATH_TO_SAVE + WebValuesNames.UPLOAD_SONGS_DIR
                        + WebValuesNames.PATH_DELIMITER + part.getSubmittedFileName();
                song.setPath(pathToLoad);
            }

            songDao.create(song);
            Optional<Song> songFromDb = songDao.findSongByPath(song);
            song = songFromDb.get();

            songDao.mergeSongAuthor(song, authorObject);
        } catch (DaoException e) {
            throw new LogicLayerException("Exception in getting objects from db.", e);
        } catch (IOException e) {
            throw new LogicLayerException("Exception in writing song.", e);
        }
    }
}
