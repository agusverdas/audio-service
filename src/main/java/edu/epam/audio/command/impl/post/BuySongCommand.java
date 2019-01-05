package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.util.RequestAttributes;
import edu.epam.audio.util.RequestParams;

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
                //todo: rewrite
                return CommandEnum.GET_BUY_SONG.name() + "&entityId=" + songId;
            }
        } catch (LogicLayerException e) {
            e.printStackTrace();
            throw new CommandException("Exception in buying song.", e);
        }
    }
}
