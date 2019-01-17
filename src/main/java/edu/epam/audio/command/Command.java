package edu.epam.audio.command;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;

public interface Command {
    /**
     * Метод команды
     * @param content Оболочка над запросом
     * @return В зависимотсти от типа(GET/POST) возвращается путь к странице/название GET команды.
     * @throws CommandException
     */
    String execute(RequestContent content) throws CommandException;
}
