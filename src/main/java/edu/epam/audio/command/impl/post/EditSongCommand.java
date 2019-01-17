package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.SongService;
import edu.epam.audio.command.RequestAttributes;
import edu.epam.audio.command.RequestParams;
import edu.epam.audio.command.UploadPath;

public class EditSongCommand implements Command {
    private SongService songService = new SongService();
    /**
     * Команда изменения песни
     * @param content Оболочка над запросом
     * @return Имя команды для перехода на страницу администратора
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        String applicationPath = content.getRequestPath();
        String uploadFilePath = applicationPath + UploadPath.UPLOAD_SONGS_DIR;

        content.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        try {
            songService.updateSong(content);

            if (content.getRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_ADMIN.name();
            } else {
                return CommandEnum.GET_EDIT_BONUS.name();
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in updating user.", e);
        }
    }
}
