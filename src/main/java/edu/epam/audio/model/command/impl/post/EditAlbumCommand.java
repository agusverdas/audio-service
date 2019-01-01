package edu.epam.audio.model.command.impl.post;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class EditAlbumCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
