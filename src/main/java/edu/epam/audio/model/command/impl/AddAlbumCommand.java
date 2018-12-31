package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.AlbumService;
import edu.epam.audio.model.util.RequestParams;
import edu.epam.audio.model.util.UploadPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AddAlbumCommand implements Command {
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent wrapper = new RequestContent();
        wrapper.init(request);

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UploadPath.UPLOAD_PHOTOS_DIR;

        wrapper.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        try {
            //todo: check error
            wrapper.setRequestPart(RequestParams.PARAM_NAME_PHOTO, request.getPart(RequestParams.PARAM_NAME_PHOTO));
            albumService.addAlbum(wrapper);
            wrapper.extractValues(request);

            return CommandEnum.GET_MAIN.name();
        } catch (LogicLayerException e) {
            e.printStackTrace();
            throw new CommandException("Exception in creating album.", e);
        } catch (ServletException | IOException e) {
            throw new CommandException("Exception in uploading file.", e);
        }
    }
}
