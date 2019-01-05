package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.entity.User;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.service.SongService;
import edu.epam.audio.service.UserService;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminPageCommand implements Command {
    private UserService userService = new UserService();
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            List<User> users = userService.loadAllUsers();
            List<Song> songs = songService.loadAllSongs();
            List<Album> albums = albumService.loadAllAlbums();

            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_USERS, users);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in loading users.", e);
        }
        return PagePath.ADMIN_PAGE;
    }
}
