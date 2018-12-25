package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.WebValuesNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

//todo: add photo updating
public class EditProfileCommand implements Command {
    private static final String NAME_PARAM = "nick";
    private static final String EMAIL_PARAM = "e-mail";
    private static final String PHOTO_PARAM = "photo";
    private static final String ERROR_EDIT = "errorEditMsg";

    private static final String UPLOAD_DIR = "uploads";

    private UserService userService = new UserService();
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }

    /*@Override
    public String execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(EMAIL_PARAM);
        String name = request.getParameter(NAME_PARAM);

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;

        File fileSaveDir = new File(uploadFilePath);
        if(!fileSaveDir.exists()){
            fileSaveDir.mkdirs();
        }

        String formedPath = null;
        try {
            Part part = request.getPart(PHOTO_PARAM);

            if (part.getSubmittedFileName() != null) {
                formedPath = uploadFilePath + File.separator + part.getSubmittedFileName();
                part.write(formedPath);
            }
        } catch (ServletException e) {
            throw new CommandException("Exception in writing file.", e);
        } catch (IOException e) {
            throw new CommandException("Exception in getting params from form.", e);
        }

        logger.debug("Formed path to load : " + formedPath);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER);

        //todo: ПОДУМАТЬ КАК ЭТО ПЕРЕПИСАТЬ
        try {
            Optional<User> userByEmail = userService.extractUser(email);
            if (!userByEmail.isPresent() || userByEmail.get().getUserId().equals(user.getUserId())){
                Optional<User> userByName = userService.extractByName(name);
                if(!userByName.isPresent() || userByName.get().getUserId().equals(user.getUserId())){
                    User userClone = user.clone();
                    userClone.setEmail(email);
                    userClone.setName(name);

                    if (formedPath != null){
                        userClone.setPhoto(formedPath);
                    }
                    logger.debug("User photo path : " + user.getPhoto());
                    userService.updateUserInfo(userClone);

                    request.removeAttribute(ERROR_EDIT);
                    return PagePath.MAIN_PAGE;
                }
                else {
                    request.setAttribute(ERROR_EDIT, "This nickname is already in use.");
                }
            }
            else {
                request.setAttribute(ERROR_EDIT, "This email is already in use.");
            }
        } catch (LogicLayerException e) {
            e.printStackTrace();
            throw new CommandException("Exception while editing your profile info.", e);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new CommandException("Exception while cloning existing user.", e);
        }
        return PagePath.MAIN_PAGE;
    }*/
}
