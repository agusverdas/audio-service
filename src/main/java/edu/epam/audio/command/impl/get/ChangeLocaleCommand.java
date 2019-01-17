package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandEnum;
import edu.epam.audio.command.SessionAttributes;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.util.*;

import java.util.Locale;

import static edu.epam.audio.util.LocaleNames.*;
import static edu.epam.audio.command.PagePath.*;
import static edu.epam.audio.command.RequestParams.*;

public class ChangeLocaleCommand implements Command {
    /**
     * Команда изменения локали
     * @param content Оболочка над запросом
     * @return Путь к главной странице
     * @throws CommandException
     */
    @Override
    public String execute(RequestContent content) throws CommandException {
        String localeParam = content.getRequestParam(PARAM_NAME_LOCALE);

        Locale locale;
        switch (localeParam){
            case LocaleNames.RU_LANGUAGE:
                locale = new Locale(RU_LANGUAGE, RU_COUNTRY);
                break;
            case LocaleNames.EN_LANGUAGE:
                locale = new Locale(EN_LANGUAGE, US_COUNTRY);
                break;
                default:
                    throw new CommandException("Incorrect locale");
        }

        content.setSessionAttribute(SessionAttributes.SESSION_ATTRIBUTE_LOCALE, locale);
        return CONTROLLER_PATH + QUESTION_MARK + PARAM_NAME_COMMAND + EQUALS_SIGN + CommandEnum.GET_MAIN.name();
    }
}
