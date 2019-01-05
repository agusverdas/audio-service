package edu.epam.audio.command.impl.get;

import edu.epam.audio.command.Command;
import edu.epam.audio.controller.RequestContent;
import edu.epam.audio.entity.Song;
import edu.epam.audio.exception.CommandException;
import edu.epam.audio.exception.ServiceException;
import edu.epam.audio.service.SongService;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.RequestAttributes;

import static edu.epam.audio.util.RequestParams.*;

public class SongPaymentPageCommand implements Command {
    private SongService songService = new SongService();
    @Override
    public String execute(RequestContent content) throws CommandException {
        Long songId = Long.parseLong(content.getRequestParam(PARAM_NAME_ENTITY_ID));
        try {
            Song song = songService.loadSong(songId);
            content.setRequestAttribute(RequestAttributes.ATTRIBUTE_NAME_SONG, song);
        } catch (ServiceException e) {
            throw new CommandException("Exception while loading song to buying page.", e);
        }
        return PagePath.PAYMENT_SONG_PAGE;
    }
}
