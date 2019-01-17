package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.service.UserService;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.command.SessionAttributes;

public class LoginCommand implements Command {
    private UserService userService = new UserService();
    /**
     * Команда логина
     * @param content Оболочка над запросом
     * @return Имя команды для перехода на главную
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        try{
            userService.loginUser(content);
            if (content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) != null){
                return CommandEnum.GET_MAIN.name();
            }
            else {
                return CommandEnum.GET_LOGIN.name();
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
