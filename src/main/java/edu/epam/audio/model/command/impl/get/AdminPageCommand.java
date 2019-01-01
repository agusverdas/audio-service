package edu.epam.audio.model.command.impl.get;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.AlbumService;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminPageCommand implements Command {
    private UserService userService = new UserService();
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> users = userService.loadAllUsers();
            List<Song> songs = songService.loadAllSongs();
            List<Album> albums = albumService.loadAllAlbums();

            request.setAttribute(RequestAttributes.ATTRIBUTE_USERS, users);
            request.setAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);
            request.setAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in loading users.", e);
        }
        return PagePath.ADMIN_PAGE;
    }
}
