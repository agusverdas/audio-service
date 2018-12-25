package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.WebParamWrapper;
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

public class EditProfileCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        WebParamWrapper wrapper = new WebParamWrapper();
        wrapper.init(request);

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + WebValuesNames.UPLOAD_PHOTOS_DIR;

        wrapper.setRequestAttribute(WebValuesNames.PARAM_NAME_PATH, uploadFilePath);
        try {
            wrapper.setRequestPart(WebValuesNames.PARAM_NAME_PHOTO, request.getPart(WebValuesNames.PARAM_NAME_PHOTO));
            userService.updateProfile(wrapper);
            wrapper.extractValues(request);

            if (wrapper.getSessionAttribute(WebValuesNames.ATTRIBUTE_NAME_ERROR) == null){
                return PagePath.MAIN_PAGE;
            } else {
                return PagePath.EDIT_PAGE;
            }
        } catch (IOException | ServletException e) {
            throw new CommandException("Exception in reading params from request.", e);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in updating user.", e);
        }
    }
}