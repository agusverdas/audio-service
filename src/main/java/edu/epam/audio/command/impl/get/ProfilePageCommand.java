package edu.epam.audio.command.impl.get;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.service.SongService;
import edu.epam.audio.command.PagePath;
import edu.epam.audio.command.RequestAttributes;
import edu.epam.audio.command.SessionAttributes;

import java.util.List;

public class ProfilePageCommand implements Command {
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();
    /**
     * Команда перехода на страницу профиля
     * @param content Оболочка над запросом
     * @return Путь к странице
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);
        try {
            List<Song> songs = songService.loadUserSongs(user);
            List<Album> albums = albumService.loadUserAlbums(user);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
            return PagePath.PROFILE_PAGE;
        } catch (ServiceException e) {
            throw new CommandException("Exception in loading profile page.", e);
        }
    }
}
