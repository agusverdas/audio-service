package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestParams;

import javax.servlet.http.HttpServletRequest;

import static edu.epam.audio.util.RequestParams.EQUALS_SIGN;
import static edu.epam.audio.util.RequestParams.PARAM_NAME_ENTITY_ID;
import static edu.epam.audio.util.RequestParams.QUESTION_MARK;

public class EditAlbumPageCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        String id = content.getRequestParam(RequestParams.PARAM_NAME_ENTITY_ID);
        return PagePath.EDIT_ALBUM_PAGE + QUESTION_MARK + PARAM_NAME_ENTITY_ID + EQUALS_SIGN + id;
    }
}
