package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.AlbumService;
import edu.epam.audio.util.RequestParams;
import edu.epam.audio.util.UploadPath;

public class AddAlbumCommand implements Command {
    private AlbumService albumService = new AlbumService();

    @Override
    public String execute(RequestContent content) throws CommandException {
        String uploadFilePath = content.getRequestPath() + UploadPath.UPLOAD_PHOTOS_DIR;

        content.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        try {
            albumService.addAlbum(content);
            return CommandEnum.GET_MAIN.name();
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in creating album.", e);
        }
    }
}
