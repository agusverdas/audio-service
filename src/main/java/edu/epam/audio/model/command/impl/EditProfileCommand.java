package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.RequestAttributes;
import edu.epam.audio.model.util.RequestParams;
import edu.epam.audio.model.util.UploadPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EditProfileCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent wrapper = new RequestContent();
        wrapper.init(request);

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UploadPath.UPLOAD_PHOTOS_DIR;

        wrapper.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        try {
            wrapper.setRequestPart(RequestParams.PARAM_NAME_PHOTO, request.getPart(RequestParams.PARAM_NAME_PHOTO));
            userService.updateProfile(wrapper);
            wrapper.extractValues(request);

            if (wrapper.getSessionAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_MAIN.name();
            } else {
                return CommandEnum.GET_EDIT.name();
            }
        } catch (IOException | ServletException e) {
            throw new CommandException("Exception in reading params from request.", e);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in updating user.", e);
        }
    }
}
