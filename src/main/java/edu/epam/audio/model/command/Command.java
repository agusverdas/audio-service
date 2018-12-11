package edu.epam.audio.model.command;

import edu.epam.audio.model.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    //todo: ask Норм?
    String SESSION_ATTRIBUTE_NAME = "name";
    String SESSION_ATTRIBUTE_ROLE = "role";
    String SESSION_ATTRIBUTE_ID = "id";

    String execute(HttpServletRequest request) throws CommandException;
}
