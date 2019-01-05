package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.entity.Song;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.LogicLayerException;
import edu.epam.audio.service.SongService;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static edu.epam.audio.util.RequestParams.*;

public class SongInfoPageCommand implements Command {
    private SongService songService = new SongService();

    @Override
    public String execute(RequestContent content) throws CommandException {
        String id = content.getRequestParam(PARAM_NAME_ENTITY_ID);
        Long songId = Long.parseLong(id);

        try {
            Song song = songService.loadSong(songId);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_SONG, song);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception while loading song to buying page.", e);
        }
        return PagePath.INFO_SONG_PAGE;
    }
}
