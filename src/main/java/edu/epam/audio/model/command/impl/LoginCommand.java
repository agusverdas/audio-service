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

public class LoginCommand implements Command {
    private static final String SESSION_ATTRIBUTE_NAME = "name";
    private static final String SESSION_ATTRIBUTE_ROLE = "role";

    private static final String PARAM_NAME_EMAIL = "e-mail";
    private static final String PARAM_NAME_PASSWORD = "password";

    private UserService receiver = new UserService();

    private static Logger logger = LogManager.getLogger();
    //todo: think about optional, String password
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        logger.info("Email param : " + email);
        logger.info("Password param : " + password);
        try {
            if (receiver.checkPassword(email, password)){
                logger.info("Password is walid.");
                Optional<User> user = receiver.createUser(email);
                System.out.println("user is walid.");
                if (user.isPresent()){
                    User userObject = user.get();

                    HttpSession session = request.getSession();
                    //todo: think about what to store in session.
                    session.setAttribute(SESSION_ATTRIBUTE_NAME, userObject.getName());
                    session.setAttribute(SESSION_ATTRIBUTE_ROLE, userObject.getRole());
                    return PagePath.MAIN_PAGE;
                } else {
                    //todo: установить атрибут, сообщающий об ошибке.
                    return PagePath.LOGIN_PAGE;
                }
            } else {
                logger.info("Password is invalid.");
                //todo: установить атрибут, сообщающий об ошибке.
                return PagePath.LOGIN_PAGE;
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
