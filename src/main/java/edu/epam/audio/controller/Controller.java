package edu.epam.audio.controller;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandFactory;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final int ERROR_CODE = 500;

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post");
        CommandFactory commandFactory = new CommandFactory();
        Command command;
        RequestDispatcher dispatcher;
        try {
            command = commandFactory.defineCommand(req);
            String page = command.execute(req);
            System.out.println("Page : " + page);

            dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req,resp);
        } catch (CommandException e) {
            resp.sendError(ERROR_CODE);
        }
    }
}
