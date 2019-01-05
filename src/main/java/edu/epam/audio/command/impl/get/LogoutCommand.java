package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    public String execute(RequestContent content) {
        content.setLogout(true);
        return PagePath.LOGIN_PAGE;
    }
}
