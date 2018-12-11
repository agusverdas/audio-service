package edu.epam.audio.model.command;

import edu.epam.audio.model.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    //todo: ask Что лучше хранить в сессии целый объект или его части?
    String SESSION_ATTRIBUTE_USER = "user";

    String execute(HttpServletRequest request) throws CommandException;
}
