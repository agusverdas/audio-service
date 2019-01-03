package edu.epam.audio.model.util;

import edu.epam.audio.model.exception.LogicLayerException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public final class UploadPath {
    public static final String UPLOAD_PHOTOS_DIR = "photos";
    public static final String UPLOAD_SONGS_DIR = "songs";

    public static final String PATH_TO_SAVE = "http://localhost:8080/";
    public static final String PATH_DELIMITER = "/";
    public static final String SYMBOL_TO_REPLACE = " ";
    public static final String PATH_REPLACEMENT = "_";

    private UploadPath(){}

    //todo: WHAT SYMBOLS EXCEPT SPACES CAN BE BAD THERE
    public static String uploadSong(String path, Part part) throws LogicLayerException {
        String formedPath;
        try {
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                formedPath = path + File.separator + part.getSubmittedFileName();
                part.write(formedPath.replaceAll(SYMBOL_TO_REPLACE, PATH_REPLACEMENT));
                String pathToLoad = PATH_TO_SAVE + UPLOAD_SONGS_DIR
                        + PATH_DELIMITER + part.getSubmittedFileName();
                return pathToLoad.replaceAll(SYMBOL_TO_REPLACE, PATH_REPLACEMENT);
            }
        } catch (IOException e) {
            throw new LogicLayerException("Exception in song uploading.", e);
        }
        return null;
    }

    public static String uploadPhoto(String path, Part part) throws LogicLayerException {
        String formedPath;
        try {
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                formedPath = path + File.separator + part.getSubmittedFileName();
                part.write(formedPath);
                String pathToLoad = PATH_TO_SAVE + UPLOAD_PHOTOS_DIR
                        + PATH_DELIMITER + part.getSubmittedFileName();
                return pathToLoad.replaceAll(SYMBOL_TO_REPLACE, PATH_REPLACEMENT);
            }
        } catch (IOException e) {
            throw new LogicLayerException("Exception in photo uploading.", e);
        }
        return null;
    }
}
