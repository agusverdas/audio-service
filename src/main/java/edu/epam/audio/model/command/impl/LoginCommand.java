package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.WebParamWrapper;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.WebValuesNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginCommand implements Command {
    private UserService userService = new UserService();

    public String execute(HttpServletRequest request) throws CommandException {
        WebParamWrapper valuesWrapper = new WebParamWrapper();
        valuesWrapper.init(request);

        try{
            userService.loginUser(valuesWrapper);
            valuesWrapper.extractValues(request);
            if (request.getSession().getAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER) != null){
                return PagePath.MAIN_PAGE;
            }
            else {
                return PagePath.LOGIN_PAGE;
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
