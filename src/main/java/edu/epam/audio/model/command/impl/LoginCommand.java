package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.service.UserServiceLogin;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final String SESSION_ATTRIBUTE_NAME = "name";
    private static final String SESSION_ATTRIBUTE_ROLE = "role";

    private static final String PARAM_NAME_EMAIL = "e-mail";
    private static final String PARAM_NAME_PASSWORD = "password";

    private UserServiceLogin receiver = new UserServiceLogin();

    //todo: think about optional, String password
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        //todo: ask some validation, валидация должна происходить здесь или в бизнес логике?
        //todo: ask Это правильное разделение бизнес логики и Command?
        try {
            if (receiver.checkPassword(email, password)){
                Optional<User> user = receiver.createUser(email);
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
                //todo: установить атрибут, сообщающий об ошибке.
                return PagePath.LOGIN_PAGE;
            }
        } catch (LogicLayerException e) {
            //todo: ask Нужен ли exception на уровень команды?
            throw new CommandException("Exception in login command.", e);
        }
    }
}
