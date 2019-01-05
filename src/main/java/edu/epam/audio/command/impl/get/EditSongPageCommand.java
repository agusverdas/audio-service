package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.util.PagePath;

import javax.servlet.http.HttpServletRequest;

import static edu.epam.audio.util.RequestParams.PARAM_NAME_ENTITY_ID;

public class EditSongPageCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        String id = content.getRequestParam(PARAM_NAME_ENTITY_ID);
        content.setRequestAttribute(PARAM_NAME_ENTITY_ID, id);
        return PagePath.EDIT_SONG_PAGE;
    }
}
