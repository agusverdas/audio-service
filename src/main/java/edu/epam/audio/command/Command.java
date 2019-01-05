package edu.epam.audio.command;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(RequestContent content) throws CommandException;
}
