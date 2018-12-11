package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//todo: think how to change it
public class LogoutCommand implements Command {
    private static Lock lock = new ReentrantLock();

    public String execute(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            lock.lock();
            session.removeAttribute(SESSION_ATTRIBUTE_USER);
        } finally {
            lock.unlock();
        }
        return PagePath.LOGIN_PAGE;
    }
}
