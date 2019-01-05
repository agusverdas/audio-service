package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.entity.Album;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestAttributes;

import static edu.epam.audio.util.RequestParams.PARAM_NAME_ENTITY_ID;

public class AlbumInfoPageCommand implements Command {
    private AlbumService albumService = new AlbumService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        Long albumId = Long.parseLong(content.getRequestParam(PARAM_NAME_ENTITY_ID));
        try {
            Album album = albumService.loadAlbum(albumId);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ALBUM, album);
        } catch (ServiceException e) {
            throw new CommandException("Exception while loading album.", e);
        }
        return PagePath.INFO_ALBUM_PAGE;
    }
}
