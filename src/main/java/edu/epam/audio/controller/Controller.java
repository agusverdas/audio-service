package edu.epam.audio.controller;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandFactory;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.epam.audio.model.util.RequestAttributes.*;
import static edu.epam.audio.model.util.RequestParams.*;

//todo: checking in js availability song for user
//todo: edition of entities by admin. validation
//todo: . and ,
//todo: ask Нужно ли пополнение на один счет делать как транзакцию?
//todo: ask Что делать с загрузкой одного и того же трека дважды?
//todo: ask Это неправильная пагинация?

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = commandExecute(req, resp);

        String error = req.getParameter(ATTRIBUTE_NAME_ERROR);
        if (error != null) {
            req.setAttribute(ATTRIBUTE_NAME_ERROR, error);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getCommand = commandExecute(req, resp);
        String pathToRedirect = req.getServletPath() + QUESTION_MARK + PARAM_NAME_COMMAND + EQUALS_SIGN + getCommand;
        Object error = req.getAttribute(ATTRIBUTE_NAME_ERROR);
        if (error != null) {
            pathToRedirect += AMPERSAND + ATTRIBUTE_NAME_ERROR +
                    EQUALS_SIGN + req.getAttribute(ATTRIBUTE_NAME_ERROR);
        }
        resp.sendRedirect(pathToRedirect);
    }

    //todo: ask Нужно ли в константы?
    private String commandExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setDateHeader("Expires", 0);

        CommandFactory commandFactory = new CommandFactory();
        Command command;
        try {
            command = commandFactory.defineCommand(request);
            return command.execute(request);
        } catch (CommandException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}