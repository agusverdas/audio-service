package edu.epam.audio.model.command.impl;

import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.service.UserService;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.WebValuesNames;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LoginCommand implements Command {
    private UserService userService = new UserService();
    private SongService songService = new SongService();

    public String execute(HttpServletRequest request) throws CommandException {
        RequestContent valuesWrapper = new RequestContent();
        valuesWrapper.init(request);

        try{
            userService.loginUser(valuesWrapper);
            valuesWrapper.extractValues(request);
            if (request.getSession().getAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER) != null){
                List<Song> songs = songService.loadAllSongs();
                request.setAttribute(WebValuesNames.SONGS, songs);
                System.out.println(songs);
                return PagePath.MAIN_PAGE;
            }
            else {
                return PagePath.LOGIN_PAGE;
            }
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
