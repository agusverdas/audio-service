package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminPageCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> users = userService.loadAllUsers();
            request.setAttribute(RequestAttributes.ATTRIBUTE_USERS, users);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in loading users.", e);
        }
        return PagePath.ADMIN_PAGE;
    }
}
