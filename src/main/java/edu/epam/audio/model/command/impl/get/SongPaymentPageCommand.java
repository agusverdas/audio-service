package edu.epam.audio.model.command.impl.get;

import edu.epam.audio.model.command.Command;
import edu.epam.audio.model.entity.Song;
import edu.epam.audio.model.exception.CommandException;
import edu.epam.audio.model.exception.LogicLayerException;
import edu.epam.audio.model.service.SongService;
import edu.epam.audio.model.util.PagePath;
import edu.epam.audio.model.util.RequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static edu.epam.audio.model.util.RequestParams.PARAM_NAME_ENTITY_ID;

public class SongPaymentPageCommand implements Command {
    private SongService songService = new SongService();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(PARAM_NAME_ENTITY_ID);
        Long songId = Long.parseLong(id);

        try {
            Song song = songService.loadSong(songId);
            request.setAttribute(RequestAttributes.ATTRIBUTE_NAME_SONG, song);
        } catch (LogicLayerException e) {
            throw new CommandException("Exception while loading song to buying page.", e);
        }
        return PagePath.PAYMENT_SONG_PAGE;
    }
}
