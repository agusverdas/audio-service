package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.entity.Song;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.SongService;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestAttributes;
import edu.epam.audio.util.RequestParams;

import java.util.List;

public class EditAlbumPageCommand implements Command {
    private SongService songService = new SongService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            Long albumId = Long.parseLong(content.getRequestParam(RequestParams.PARAM_NAME_ENTITY_ID));
            List<Song> songs = songService.loadSongsNotInAlbum();
            content.setRequestAttribute(RequestParams.PARAM_NAME_ENTITY_ID, albumId);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_SONGS, songs);
        } catch (ServiceException e) {
            throw new CommandException("Exception in loading edit album page.", e);
        }
        return PagePath.EDIT_ALBUM_PAGE;
    }
}
