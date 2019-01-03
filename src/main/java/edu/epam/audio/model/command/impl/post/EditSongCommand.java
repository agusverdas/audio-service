package edu.epam.audio.model.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.util.RequestAttributes;
import edu.epam.audio.model.util.RequestParams;
import edu.epam.audio.model.util.UploadPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EditSongCommand implements Command {
    private SongService songService = new SongService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent content = new RequestContent();
        content.init(request);

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UploadPath.UPLOAD_SONGS_DIR;

        content.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        try {
            content.setRequestPart(RequestParams.PARAM_NAME_SONG, request.getPart(RequestParams.PARAM_NAME_SONG));
            songService.updateSong(content);
            content.extractValues(request);

            if (request.getAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_ADMIN.name();
            } else {
                return CommandEnum.GET_EDIT_BONUS.name();
            }
        } catch (ServletException | IOException e) {
            throw new CommandException("Exception in reading params from request.", e);
        }catch (LogicLayerException e) {
            e.printStackTrace();
            throw new CommandException("Exception in updating user.", e);
        }
    }
}
