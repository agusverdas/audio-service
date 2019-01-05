package edu.epam.audio.command.impl.get;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.entity.Album;
import edu.epam.audio.entity.Song;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.service.SongService;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainPageCommand implements Command {
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(RequestContent content) throws CommandException {
        try{
            List<Song> songs = songService.loadAllSongs();
            List<Album> albums = albumService.loadAllAlbums();
            List<Song> userSongs = songService.findUserSongs(content);

            songs.removeAll(userSongs);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);

            return PagePath.MAIN_PAGE;
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
