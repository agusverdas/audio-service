package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.util.UploadPath;

import javax.servlet.http.Part;

import static edu.epam.audio.util.RequestParams.PARAM_NAME_AUTHOR;
import static edu.epam.audio.util.RequestParams.PARAM_NAME_PHOTO;
import static edu.epam.audio.util.RequestParams.PARAM_NAME_TITLE;

public class AddAlbumCommand implements Command {
    private AlbumService albumService = new AlbumService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        String title = content.getRequestParam(PARAM_NAME_TITLE);
        String authorName  = content.getRequestParam(PARAM_NAME_AUTHOR);
        String uploadFilePath = content.getRequestPath() + UploadPath.UPLOAD_PHOTOS_DIR;
        Part part = content.getRequestPart(PARAM_NAME_PHOTO);
        try {
            albumService.addAlbum(title, authorName, uploadFilePath, part);
            return CommandEnum.GET_MAIN.name();
        } catch (ServiceException e) {
            throw new CommandException("Exception in creating album.", e);
        }
    }
}
