package edu.epam.audio.model.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandEnum;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.util.RequestAttributes;
import edu.epam.audio.model.util.RequestParams;

import javax.servlet.http.HttpServletRequest;

public class BuySongCommand implements Command {
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent content = new RequestContent();
        content.init(request);

        Long songId = Long.parseLong(content.getRequestParam(RequestParams.PARAM_NAME_ID));

        try {
            userService.buySong(content);
            content.extractValues(request);

            if (request.getAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
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
