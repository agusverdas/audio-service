package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.SongService;
import edu.epam.audio.command.RequestParams;
import edu.epam.audio.command.UploadPath;

import javax.servlet.http.Part;

import static edu.epam.audio.command.RequestParams.*;

public class AddSongCommand implements Command {
    private SongService songService = new SongService();
    /**
     * Команда добавления песни
     * @param content Оболочка над запросом
     * @return Имя команды для перехода на главную
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        String uploadFilePath = content.getRequestPath() + UploadPath.UPLOAD_SONGS_DIR;
        content.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        String title = content.getRequestParam(PARAM_NAME_TITLE);
        String[] authors = content.getRequestParam(PARAM_NAME_AUTHOR).split(UploadPath.AUTHOR_DELIMITER);
        String cost = content.getRequestParam(PARAM_NAME_COST);
        Part part = content.getRequestPart(PARAM_NAME_SONG);
        try {
            songService.addSong(title, authors, cost, uploadFilePath, part);
            return CommandEnum.GET_MAIN.name();
        } catch (ServiceException e) {
            throw new CommandException("Exception in creating songs.", e);
        }
    }
}
