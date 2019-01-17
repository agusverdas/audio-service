package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.command.PagePath;

import static edu.epam.audio.command.RequestParams.*;

public class EditBonusPageCommand implements Command {
    /**
     * Команда измненения бонуса
     * @param content Оболочка над запросом
     * @return Путь к странице
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        Long userId = Long.parseLong(content.getRequestParam(PARAM_NAME_ENTITY_ID));
        content.setRequestAttribute(PARAM_NAME_ENTITY_ID, userId);
        return PagePath.EDIT_BONUS_PAGE;
    }
}
