package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.command.PagePath;

public class LogoutCommand implements Command {
    /**
     * Команда выхода
     * @param content Оболочка над запросом
     * @return Путь к странице входа
     */
    public String execute(RequestContent content) {
        content.setLogout(true);
        return PagePath.LOGIN_PAGE;
    }
}
