package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.util.RequestAttributes;

public class AddMoneyCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            userService.addMoney(content);

            if (content.getRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_PROFILE.name();
            } else {
                return CommandEnum.GET_ADD_MONEY.name();
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in creating songs.", e);
        }
    }
}
