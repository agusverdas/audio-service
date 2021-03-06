package edu.epam.audio.service;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.dao.AlbumDao;
import edu.epam.audio.dao.AuthorDao;
import edu.epam.audio.dao.SongDao;
import edu.epam.audio.dao.impl.AlbumDaoImpl;
import edu.epam.audio.dao.impl.AuthorDaoImpl;
import edu.epam.audio.dao.impl.SongDaoImpl;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Author;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.entity.builder.impl.AlbumBuilder;
import edu.epam.audio.exception.DaoException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.command.RequestParams;
import edu.epam.audio.command.UploadPath;
import edu.epam.audio.util.FilterXSS;

import javax.servlet.http.Part;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlbumService {
    /**
     * Добавление альбома
     * @param title Название
     * @param authorName Имя автора
     * @param path Путь запроса
     * @param part Картинка альбома
     * @throws ServiceException
     */
    public void addAlbum(String title, String authorName, String path, Part part) throws ServiceException {
        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        AlbumDao albumDao = AlbumDaoImpl.getInstance();

        Author author = new Author();
        author.setName(FilterXSS.filterXSS(authorName));
        try {
            Optional<Author> authorOptional = authorDao.findAuthorByName(author);
            if (!authorOptional.isPresent()){
                authorDao.create(author);
                authorOptional = authorDao.findAuthorByName(author);
            }
            author = authorOptional.get();

            Album album = new AlbumBuilder()
                    .addTitle(FilterXSS.filterXSS(title))
                    .build();

            File fileSaveDir = new File(path);
            if(!fileSaveDir.exists()){
                fileSaveDir.mkdirs();
            }

            String uploadPath = UploadPath.uploadPhoto(path, part);
            if (uploadPath != null){
                album.setPhoto(uploadPath);
            }

            album.setAuthor(author);
            albumDao.create(album);
        } catch (DaoException e) {
            throw new ServiceException("Exception in adding album to db.", e);
        }
    }

    /**
     * Установка автора альбома
     * @param album Альбом
     * @throws DaoException
     */
    private void loadAlbumAuthor(Album album) throws DaoException {
        AuthorDao authorDao = AuthorDaoImpl.getInstance();
        Optional<Author> authorOptional = authorDao.findEntityById(album.getAuthor().getAuthorId());
        if (authorOptional.isPresent()){
            Author author = authorOptional.get();
            album.setAuthor(author);
        }
    }

    /**
     * Установка песен альбома
     * @param album Альбом
     * @throws DaoException
     */
    private void loadAlbumSongs(Album album) throws DaoException {
        SongDao songDao = SongDaoImpl.getInstance();
        AuthorDao authorDao = AuthorDaoImpl.getInstance();

        List<Song> songs = songDao.findSongsByAlbum(album);
        for (Song song: songs) {
            List<Author> authors = authorDao.findAuthorsBySong(song);
            song.setAuthorList(authors);
        }
        album.setSongs(songs);
    }

    /**
     * Нахождение альбома по id
     * @param albumId id альбома
     * @return Альбом
     * @throws ServiceException
     */
    public Album loadAlbum(long albumId) throws ServiceException {
        AlbumDao albumDao = AlbumDaoImpl.getInstance();

        try {
            Optional<Album> albumOptional = albumDao.findEntityById(albumId);
            if (albumOptional.isPresent()) {
                Album album = albumOptional.get();
                loadAlbumAuthor(album);
                loadAlbumSongs(album);
                return album;
            } else {
                throw new ServiceException("No such album album.");
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception while loading album.", e);
        }
    }

    /**
     * Загрузка всех альбомов
     * @return Все альбомы
     * @throws ServiceException
     */
    public List<Album> loadAllAlbums() throws ServiceException {
        AlbumDao albumDao = AlbumDaoImpl.getInstance();

        try {
            List<Album> albums = albumDao.findAll();
            for (Album album: albums) {
                loadAlbumAuthor(album);
                loadAlbumSongs(album);
            }
            return albums;
        } catch (DaoException e) {
            throw new ServiceException("Exception while loading all the albums.", e);
        }
    }

    /**
     * Загрузка альбомов пользователя
     * @param user Пользователь
     * @return Альбомы
     * @throws ServiceException
     */
    public List<Album> loadUserAlbums(User user) throws ServiceException {
        AlbumDao albumDao = AlbumDaoImpl.getInstance();

        try {
            List<Album> albums = albumDao.findUserAlbums(user);
            for (Album album: albums) {
                loadAlbumAuthor(album);
                loadAlbumSongs(album);
            }
            return albums;
        } catch (DaoException e) {
            throw new ServiceException("Exception while loading all the user's albums.", e);
        }
    }

    /**
     * Добавление песен в альбом
     * @param content Оболочка над запросом
     * @throws ServiceException
     */
    public void songsToAlbum(RequestContent content) throws ServiceException {
        Long albumId = Long.parseLong(content.getRequestParam(RequestParams.PARAM_NAME_ID));
        List<String> values = content.getRequestParams(RequestParams.PARAM_NAME_ROWS);
        List<Long> songIds = values.stream().map(Long::parseLong).collect(Collectors.toList());

        SongDao songDao = SongDaoImpl.getInstance();
        AlbumDao albumDao = AlbumDaoImpl.getInstance();
        try {
            Optional<Album> albumOptional = albumDao.findEntityById(albumId);
            if (!albumOptional.isPresent()){
                throw new ServiceException("No such album in db.");
            }
            Album album = albumOptional.get();

            for (Long id : songIds) {
                Optional<Song> songOptional = songDao.findEntityById(id);
                if (!songOptional.isPresent()){
                    throw new ServiceException("No such song in db.");
                }
                Song song = songOptional.get();
                album.getSongs().add(song);

                double cost = album.getCost() + song.getCost();
                album.setCost(cost);
            }
            songDao.mergeSongAlbum(album);
            albumDao.update(album);
        } catch (DaoException e){
            throw new ServiceException("Exception in merging songs with album.", e);
        }
    }
}
