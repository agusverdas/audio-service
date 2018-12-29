package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class ProfilePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        //todo: add here loading user songs/albums
        return PagePath.PROFILE_PAGE;
    }
}
