package edu.epam.audio.model.command.impl.get;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return PagePath.REGISTRATION_PAGE;
    }
}
