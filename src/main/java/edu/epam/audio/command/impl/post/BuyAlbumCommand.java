package edu.epam.audio.command.impl.post;

import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.command.RequestAttributes;

import static edu.epam.audio.command.RequestParams.*;

public class BuyAlbumCommand implements Command {
    private UserService userService = new UserService();
    /**
     * Команда покупки альбома
     * @param content Оболочка над запросом
     * @return Имя команды для перехода в профиль
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        Long albumId = Long.parseLong(content.getRequestParam(PARAM_NAME_ID));
        try {
            userService.buyAlbum(content);
            if (content.getRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_PROFILE.name();
            } else {
                return CommandEnum.GET_BUY_ALBUM.name() + AMPERSAND + PARAM_NAME_ENTITY_ID + EQUALS_SIGN + albumId;
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in buying song.", e);
        }
    }
}
