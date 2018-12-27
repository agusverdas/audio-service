package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.WebValuesNames;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AddSongCommand implements Command {
    private SongService songService = new SongService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent wrapper = new RequestContent();
        wrapper.init(request);

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + WebValuesNames.UPLOAD_SONGS_DIR;

        wrapper.setRequestAttribute(WebValuesNames.PARAM_NAME_PATH, uploadFilePath);
        try {
            //todo: check error
            wrapper.setRequestPart(WebValuesNames.PARAM_NAME_SONG, request.getPart(WebValuesNames.PARAM_NAME_SONG));
            songService.addSong(wrapper);
            wrapper.extractValues(request);

            return PagePath.MAIN_PAGE;
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in creating songs.", e);
        } catch (ServletException | IOException e) {
            throw new CommandException("Exception in uploading file.", e);
        }
    }
}