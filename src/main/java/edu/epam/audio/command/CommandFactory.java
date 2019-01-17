package edu.epam.audio.command;

import edu.epam.audio.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Класс-фабрика для определения команды
 */
public class CommandFactory {
    private static Logger logger = LogManager.getLogger();

    private static final String DASH = "-";
    private static final String UNDERSCORE = "_";

    /**
     * Метод определения команды
     * @param request Запрос
     * @return Команда
     * @throws CommandException
     */
    public Command defineCommand(HttpServletRequest request) throws CommandException {
        String action = request.getParameter(RequestParams.PARAM_NAME_COMMAND);
        logger.debug(RequestParams.PARAM_NAME_COMMAND + " : value :" + action);

        if (action == null || action.isEmpty()){
            throw new CommandException("Exception in command value.");
        }

        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase().replaceAll(DASH, UNDERSCORE));
        return currentEnum.getCommand();
    }
}
