package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.command.PagePath;

import static edu.epam.audio.command.RequestParams.PARAM_NAME_ENTITY_ID;

public class EditSongPageCommand implements Command {
    /**
     * Команда перехода на страницу изменения песни
     * @param content Оболочка над запросом
     * @return Путь к странице
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        Long songId = Long.parseLong(content.getRequestParam(PARAM_NAME_ENTITY_ID));
        content.setRequestAttribute(PARAM_NAME_ENTITY_ID, songId);
        return PagePath.EDIT_SONG_PAGE;
    }
}
