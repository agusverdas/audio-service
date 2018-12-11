package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_EMAIL = "e-mail";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final String ATTRIBUTE_NAME_ERROR = "errorLoginMessage";

    private UserService userService = new UserService();
    private static Lock lock = new ReentrantLock();

    private static Logger logger = LogManager.getLogger();

    //todo: think about optional, String password
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        logger.debug("User with e-mail : " + email + "; Try to login into system.");
        try {
            if (userService.checkPassword(email, password)){
                logger.debug("Password is valid.");

                Optional<User> user = userService.extractUser(email);
                if (user.isPresent()){
                    User userObject = user.get();

                    HttpSession session = request.getSession();
                    //todo: ask Потокобезопасная запись в сессию?
                    try {
                        lock.lock();
                        session.setAttribute(SESSION_ATTRIBUTE_ID, userObject.getUserId());
                        session.setAttribute(SESSION_ATTRIBUTE_NAME, userObject.getName());
                        session.setAttribute(SESSION_ATTRIBUTE_ROLE, userObject.getRole());
                    } finally {
                        lock.unlock();
                    }
                    return PagePath.MAIN_PAGE;
                } else {
                    request.setAttribute(ATTRIBUTE_NAME_ERROR, "User doesn't exist.");
                }
            } else {
                logger.info("Password is invalid.");
                request.setAttribute(ATTRIBUTE_NAME_ERROR, "Password is invalid");
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }

        return PagePath.LOGIN_PAGE;
    }
}
