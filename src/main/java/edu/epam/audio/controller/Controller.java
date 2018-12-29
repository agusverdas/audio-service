package edu.epam.audio.controller;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandFactory;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.util.RequestAttributes;
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

//todo: only mp3 files to upload as music, only jpg as photo
//todo: fmt set locale
//todo: * for main fields
//todo: checking in js availability song for user

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
        String path = commandExecute(req);

        String error = req.getParameter(RequestAttributes.ATTRIBUTE_NAME_ERROR);
        req.setAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR, error);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getCommand = commandExecute(req);
        //todo: change path
        resp.sendRedirect("/Controller?command=" + getCommand + "&" + RequestAttributes.ATTRIBUTE_NAME_ERROR +
                "=" + req.getAttribute(RequestAttributes.ATTRIBUTE_NAME_ERROR));
    }

    private String commandExecute(HttpServletRequest request) throws ServletException {
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