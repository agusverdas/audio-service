package edu.epam.audio.model.command;

import edu.epam.audio.model.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static Logger logger = LogManager.getLogger();

    private static final String PARAMETER_NAME = "command";
    private static final String DASH = "-";
    private static final String UNDERSCORE = "_";

    public Command defineCommand(HttpServletRequest request) throws CommandException {
        System.out.println(request.getParameter(PARAMETER_NAME));
        String action = request.getParameter(PARAMETER_NAME);

        logger.debug("Action : " + action);

        if (action == null || action.isEmpty()){
            throw new CommandException("Exception in command value.");
        }

        logger.info("Command " + action + " came to server.");
        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase().replaceAll(DASH, UNDERSCORE));
        return currentEnum.getCommand();
    }
}
