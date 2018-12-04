package edu.epam.audio.controller;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.command.CommandFactory;
import edu.epam.audio.model.pool.ConnectionPool;
import edu.epam.audio.model.pool.ProxyConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ConnectionPool<ProxyConnection> instance = ConnectionPool.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.defineCommand(req);
        String page = command.execute(req);
    }
}
