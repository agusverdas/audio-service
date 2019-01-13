package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.util.RequestAttributes;

import static edu.epam.audio.util.RequestParams.*;

public class EditBonusCommand implements Command {
    private UserService userService = new UserService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        long id = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        try {
            userService.updateBonus(content);
            if (content.getRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_ADMIN.name();
            } else {
                return CommandEnum.GET_EDIT_BONUS.name() + AMPERSAND + PARAM_NAME_ENTITY_ID + EQUALS_SIGN + id;
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in updating user.", e);
        }
    }
}
