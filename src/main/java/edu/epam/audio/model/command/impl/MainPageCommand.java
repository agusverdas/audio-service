package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.AlbumService;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainPageCommand implements Command {
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try{
            List<Song> songs = songService.loadAllSongs();
            List<Album> albums = albumService.loadAllAlbums();

            request.setAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
            request.setAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);

            return PagePath.MAIN_PAGE;
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
