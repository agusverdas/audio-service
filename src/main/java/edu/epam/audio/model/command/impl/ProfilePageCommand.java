package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.Album;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.AlbumService;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.RequestAttributes;
import edu.epam.audio.model.util.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ProfilePageCommand implements Command {
    private SongService songService = new SongService();
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            RequestContent wrapper = new RequestContent();
            wrapper.init(request);

            List<Song> songs = songService.findUserSongs(wrapper);
            List<Album> albums = albumService.findUserAlbums(wrapper);
            request.setAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);
            request.setAttribute(RequestAttributes.ATTRIBUTE_ALBUMS, albums);
            return PagePath.PROFILE_PAGE;
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in loading users.", e);
        }
    }
}
