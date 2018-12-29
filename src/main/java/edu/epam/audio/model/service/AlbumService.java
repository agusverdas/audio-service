package edu.epam.audio.model.service;

import edu.epam.audio.model.dao.impl.AlbumDaoImpl;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.exception.DaoException;
import edu.epam.audio.model.exception.LogicLayerException;

import java.util.List;

public class AlbumService {
    public List<Album> loadAllAlbums() throws LogicLayerException {
        AlbumDaoImpl albumDao = new AlbumDaoImpl();

        try {
            return albumDao.findAll();
        } catch (DaoException e) {
            throw new LogicLayerException("Exception while loading albums.", e);
        }
    }
}
