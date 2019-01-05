package edu.epam.audio.util;

import edu.epam.audio.exception.ServiceException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public final class UploadPath {
    public static final String UPLOAD_PHOTOS_DIR = "photos";
    public static final String UPLOAD_SONGS_DIR = "songs";

    private static final String PATH_TO_SAVE = "http://localhost:8080/";
    private static final String PATH_DELIMITER = "/";
    private static final String SYMBOL_TO_REPLACE = "\\s+";
    private static final String PATH_REPLACEMENT = "_";
    public static final String AUTHOR_DELIMITER = ",";

    private UploadPath(){}

    public static String uploadSong(String path, Part part) throws ServiceException {
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
            throw new ServiceException("Exception in song uploading.", e);
        }
        return null;
    }

    public static String uploadPhoto(String path, Part part) throws ServiceException {
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
            throw new ServiceException("Exception in photo uploading.", e);
        }
        return null;
    }
}
