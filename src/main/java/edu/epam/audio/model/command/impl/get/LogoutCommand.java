package edu.epam.audio.model.command.impl.get;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return PagePath.LOGIN_PAGE;
    }
}
