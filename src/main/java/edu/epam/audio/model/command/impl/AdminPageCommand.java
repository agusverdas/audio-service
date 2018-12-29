package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class AdminPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        //todo: add loading users
        return PagePath.ADMIN_PAGE;
    }
}
