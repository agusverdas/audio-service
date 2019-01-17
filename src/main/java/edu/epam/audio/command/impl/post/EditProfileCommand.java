package edu.epam.audio.command.impl.post;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.UserService;
import edu.epam.audio.command.RequestAttributes;
import edu.epam.audio.command.RequestParams;
import edu.epam.audio.command.UploadPath;

public class EditProfileCommand implements Command {
    private UserService userService = new UserService();
    /**
     * Команда изменения профиля
     * @param content Оболочка над запросом
     * @return Имя команды для перехода на главную
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        String uploadFilePath = content.getRequestPath() + UploadPath.UPLOAD_PHOTOS_DIR;
        content.setRequestAttribute(RequestParams.PARAM_NAME_PATH, uploadFilePath);
        try {
            userService.updateProfile(content);

            if (content.getRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR) == null){
                return CommandEnum.GET_MAIN.name();
            } else {
                return CommandEnum.GET_EDIT.name();
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in updating user.", e);
        }
    }
}
