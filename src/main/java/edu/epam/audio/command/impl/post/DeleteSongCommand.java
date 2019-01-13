package edu.epam.audio.command.impl.post;

import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.SongService;

public class DeleteSongCommand implements Command {
    private SongService songService = new SongService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            songService.deleteSong(content);
        } catch (ServiceException e) {
            throw new CommandException("Exception in deleting song.", e);
        }
        return CommandEnum.GET_MAIN.name();
    }
}
