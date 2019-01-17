package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.command.PagePath;

public class RegistrationPageCommand implements Command {
    /**
     * Команда перехода на страницу регистрации
     * @param content Оболочка над запросом
     * @return Путь к странице
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        return PagePath.REGISTRATION_PAGE;
    }
}
