package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.UserService;

import static edu.epam.audio.command.RequestAttributes.*;

public class AddMoneyCommand implements Command {
    private UserService userService = new UserService();
    /**
     * Команда добавления денег
     * @param content Оболочка над запросом
     * @return Имя команды для перехода в профиль
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            userService.addMoney(content);
            if (content.getRequestAttribute(ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_PROFILE.name();
            } else {
                return CommandEnum.GET_ADD_MONEY.name();
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in adding song.", e);
        }
    }
}
