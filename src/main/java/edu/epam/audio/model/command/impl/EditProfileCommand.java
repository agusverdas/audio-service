package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//todo: add photo updating
public class EditProfileCommand implements Command {
    private static final String NAME_PARAM = "name";
    private static final String EMAIL_PARAM = "e-mail";
    private static final String PHOTO_PARAM = "photo";
    private static final String ERROR_EDIT = "errorEditMsg";

    private UserService userService = new UserService();
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(EMAIL_PARAM);
        String name = request.getParameter(NAME_PARAM);

        HttpSession session = request.getSession();
        session.getAttribute(SESSION_ATTRIBUTE_USER);

        try {
            if (!userService.extractUser(email).isPresent()){
                if(!userService.extractByName(name).isPresent()){

                }
                else {
                    request.setAttribute(ERROR_EDIT, "This nickname is already in use.");
                }
            }
            else {
                request.setAttribute(ERROR_EDIT, "This email is already in use.");

            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception while editing your profile info.", e);
        }
        return PagePath.EDIT_PAGE;
    }
}
