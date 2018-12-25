package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.WebParamWrapper;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.WebValuesNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogoutCommand implements Command {
    private UserService userService = new UserService();

    public String execute(HttpServletRequest request) {
        WebParamWrapper valuesWrapper = new WebParamWrapper();
        valuesWrapper.init(request);

        userService.logoutUser(valuesWrapper);
        valuesWrapper.extractValues(request);
        return PagePath.LOGIN_PAGE;
    }
}
