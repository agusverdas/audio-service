package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.command.SessionAttributes;

public class RegistrationCommand implements Command {
    private UserService userService = new UserService();
    /**
     * Команда регистрации
     * @param content Оболочка над запросом
     * @return Имя команды для перехода на главную
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            userService.registerUser(content);
            if (content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) != null){
                return CommandEnum.GET_MAIN.name();
            } else {
                return CommandEnum.GET_REGISTRATION.name();
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in registration.", e);
        }
    }
}
