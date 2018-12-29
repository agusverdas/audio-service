package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent valuesWrapper = new RequestContent();
        valuesWrapper.init(request);

        try {
            userService.registerUser(valuesWrapper);
            valuesWrapper.extractValues(request);
            if (request.getSession().getAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) != null){
                return CommandEnum.GET_MAIN.name();
            } else {
                return CommandEnum.GET_REGISTRATION.name();
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in registration.", e);
        }
    }
}
