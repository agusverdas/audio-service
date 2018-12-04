package edu.epam.audio.model.command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    //todo: check if action is null
    private static final String PARAMETER_NAME = "command";

    public Command defineCommand(HttpServletRequest request) {
        String action = request.getParameter(PARAMETER_NAME);

        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
        return currentEnum.getCommand();
    }
}
