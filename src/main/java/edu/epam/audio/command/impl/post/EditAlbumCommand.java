package edu.epam.audio.command.impl.post;

import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.AlbumService;

public class EditAlbumCommand implements Command {
    private AlbumService albumService = new AlbumService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            albumService.songsToAlbum(content);
            return CommandEnum.GET_MAIN.name();
        } catch (ServiceException e) {
            throw new CommandException("Exception in album edition.", e);
        }
    }
}
