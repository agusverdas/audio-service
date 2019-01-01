package edu.epam.audio.model.command.impl.get;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.RequestParams;

import javax.servlet.http.HttpServletRequest;

import static edu.epam.audio.model.util.RequestParams.EQUALS_SIGN;
import static edu.epam.audio.model.util.RequestParams.PARAM_NAME_ENTITY_ID;
import static edu.epam.audio.model.util.RequestParams.QUESTION_MARK;

public class EditAlbumPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(RequestParams.PARAM_NAME_ENTITY_ID);
        return PagePath.EDIT_ALBUM_PAGE + QUESTION_MARK + PARAM_NAME_ENTITY_ID + EQUALS_SIGN + id;
    }
}
