package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.service.SongService;
import edu.epam.audio.service.UserService;
import edu.epam.audio.command.PagePath;
import edu.epam.audio.command.RequestAttributes;

import java.util.List;

public class AdminPageCommand implements Command {
    private UserService userService = new UserService();
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();

    /**
     * Команда перехода на страницу администратора
     * @param content Оболочка над запросом
     * @return Путь к странице
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            List<User> users = userService.loadAllUsers();
            List<Song> songs = songService.loadAllSongs();
            List<Album> albums = albumService.loadAllAlbums();

            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_USERS, users);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
        } catch (ServiceException e) {
            throw new CommandException("Exception in loading admin page.", e);
        }
        return PagePath.ADMIN_PAGE;
    }
}
