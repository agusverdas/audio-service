package edu.epam.audio.model.command.impl.get;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.util.PagePath;

import javax.servlet.http.HttpServletRequest;

import static edu.epam.audio.model.util.RequestParams.*;

public class EditBonusPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(PARAM_NAME_ENTITY_ID);
        request.setAttribute(PARAM_NAME_ENTITY_ID, id);
        return PagePath.EDIT_BONUS_PAGE;
    }
}
