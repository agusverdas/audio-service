package edu.epam.audio.controller;

import edu.epam.audio.command.Command;
import edu.epam.audio.command.CommandFactory;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.epam.audio.util.RequestAttributes.*;
import static edu.epam.audio.util.RequestParams.*;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
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

    private String commandExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setDateHeader("Expires", 0);

        CommandFactory commandFactory = new CommandFactory();
        Command command;
        try {
            command = commandFactory.defineCommand(request);
            RequestContent content = new RequestContent();
            content.init(request);
            String page = command.execute(content);
            content.extractValues(request);
            if (content.isLogout()){
                request.getSession().invalidate();
            }
            return page;
        } catch (CommandException e) {
            throw new ServletException(e.getMessage(), e);
            //response.sendError(1, e.getMessage());
        }
    }

    @Override
    public void destroy() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.destroy();
    }
}