package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.ParamsValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegistrationCommand implements Command {
    //todo: ask Повторяются названия параметров на разных формах
    private static final String EMAIL_PARAM = "e-mail";
    private static final String PASSWORD_PARAM = "password";
    private static final String NICKNAME_PARAM = "nick";

    private static final String ERROR_ATTRIBUTE = "errorRegistrationMsg";

    private UserService userService = new UserService();

    private static Lock lock = new ReentrantLock();
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        String name = request.getParameter(NICKNAME_PARAM);

        logger.debug("E-mail : " + email);
        logger.debug("Password : " + password);
        logger.debug("Name : " + name);
        try {
            if(!userService.checkUserByEmail(email)){
                if (ParamsValidator.validatePassword(password)){
                    if (ParamsValidator.validateName(name)){
                        Optional<User> user = userService.createUser(email, password, name);
                        logger.debug("User from service : " + user);
                        if (user.isPresent()) {
                            User userObject = user.get();

                            HttpSession session = request.getSession();
                            //todo: ask Как избежать повторения этих строк?
                            try {
                                lock.lock();
                                session.setAttribute(SESSION_ATTRIBUTE_ID, userObject.getUserId());
                                session.setAttribute(SESSION_ATTRIBUTE_NAME, userObject.getName());
                                session.setAttribute(SESSION_ATTRIBUTE_ROLE, userObject.getRole());
                            } finally {
                                lock.unlock();
                            }
                            return PagePath.MAIN_PAGE;
                        }
                        else {
                            request.setAttribute(ERROR_ATTRIBUTE, "Try again.");
                        }
                    }
                    else {
                        request.setAttribute(ERROR_ATTRIBUTE, "Incorrect nickname.");
                    }
                }
                else {
                    request.setAttribute(ERROR_ATTRIBUTE, "You can't use this password."); //TODO: change message
                }
            } else {
                request.setAttribute(ERROR_ATTRIBUTE,  "User with this e-mail already exists.");
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception while user register", e);
        }

        return PagePath.REGISTRATION_PAGE;
    }
}
