package edu.epam.audio.model.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class AddMoneyCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent content = new RequestContent();
        content.init(request);

        try {
            userService.addMoney(content);
            content.extractValues(request);

            if (request.getAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_PROFILE.name();
            } else {
                return CommandEnum.GET_ADD_MONEY.name();
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in creating songs.", e);
        }
    }
}
