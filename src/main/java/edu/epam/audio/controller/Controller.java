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

//todo: only mp3 files to upload as music, only jpg as photo
//todo: fmt set locale
//todo: * for main fields
//todo: make filter loading info to main page, checking in js availability song for user
//todo: Secure F5
//todo: Why i cant just redirect all the post requests?

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
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandFactory commandFactory = new CommandFactory();
        Command command;
        logger.debug("POST Request came.");
        try {
            command = commandFactory.defineCommand(req);
            String page = command.execute(req);

            resp.sendRedirect(req.getContextPath() + page);
        } catch (CommandException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage(), e);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        CommandFactory commandFactory = new CommandFactory();
        Command command;
        RequestDispatcher dispatcher;
        logger.debug("Request came.");
        try {
            command = commandFactory.defineCommand(req);
            String page = command.execute(req);

            dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req,resp);
        } catch (CommandException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage(), e);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }
}
