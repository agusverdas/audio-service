package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.util.RequestAttributes;
import edu.epam.audio.util.RequestParams;

import static edu.epam.audio.util.RequestParams.AMPERSAND;
import static edu.epam.audio.util.RequestParams.EQUALS_SIGN;
import static edu.epam.audio.util.RequestParams.PARAM_NAME_ENTITY_ID;

public class BuySongCommand implements Command {
    private  UserService userService = new UserService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        Long songId = Long.parseLong(content.getRequestParam(RequestParams.PARAM_NAME_ID));
        try {
            userService.buySong(content);
            if (content.getRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_PROFILE.name();
            } else {
                return CommandEnum.GET_BUY_SONG.name() + AMPERSAND + PARAM_NAME_ENTITY_ID + EQUALS_SIGN + songId;
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in buying song.", e);
        }
    }
}
