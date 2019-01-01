package edu.epam.audio.model.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private UserService userService = new UserService();

    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent content = new RequestContent();
        content.init(request);

        try{
            userService.loginUser(content);
            content.extractValues(request);
            if (request.getSession().getAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) != null){
                return CommandEnum.GET_MAIN.name();
            }
            else {
                return CommandEnum.GET_LOGIN.name();
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
