package edu.epam.audio.model.command.impl;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.WebValuesNames;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainPageCommand implements Command {
    private SongService songService = new SongService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try{
            List<Song> songs = songService.loadAllSongs();
            request.setAttribute(WebValuesNames.SONGS, songs);

            return PagePath.MAIN_PAGE;
        } catch (LogicLayerException e) {
            throw new CommandException("Exception in login command.", e);
        }
    }
}
