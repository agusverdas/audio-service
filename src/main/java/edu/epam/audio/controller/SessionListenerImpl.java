package edu.epam.audio.controller;

import edu.epam.audio.command.SessionAttributes;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

import static edu.epam.audio.util.LocaleNames.*;

@WebListener
public class SessionListenerImpl implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Locale locale = new Locale(EN_LANGUAGE, US_COUNTRY);
        session.setAttribute(SessionAttributes.SESSION_ATTRIBUTE_LOCALE, locale);
    }
}
