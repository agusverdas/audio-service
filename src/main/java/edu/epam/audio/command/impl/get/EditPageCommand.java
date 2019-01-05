package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.util.PagePath;

public class EditPageCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        return PagePath.EDIT_PAGE;
    }
}
