package edu.epam.audio.controller;

import edu.epam.audio.model.util.LocaleNames;
import edu.epam.audio.model.util.SessionAttributes;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListenerImpl implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttributes.SESSION_ATTRIBUTE_LOCALE, LocaleNames.EN_US);
    }
}
