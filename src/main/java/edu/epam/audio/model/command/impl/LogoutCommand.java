package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    public String execute(HttpServletRequest request) {
        return null;
    }
}
