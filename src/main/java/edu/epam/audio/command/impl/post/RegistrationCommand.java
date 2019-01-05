package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.util.SessionAttributes;

public class RegistrationCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            userService.registerUser(content);
            if (content.getSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) != null){
                return CommandEnum.GET_MAIN.name();
            } else {
                return CommandEnum.GET_REGISTRATION.name();
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in registration.", e);
        }
    }
}
