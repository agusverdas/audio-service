package edu.epam.audio.command;

import edu.epam.audio.command.impl.get.*;
import edu.epam.audio.command.impl.post.*;

public enum CommandEnum {
    POST_LOGIN(new LoginCommand()),
    POST_REGISTRATION(new RegistrationCommand()),
    POST_EDIT_PROFILE(new EditProfileCommand()),
    POST_ADD_SONG(new AddSongCommand()),
    POST_ADD_ALBUM(new AddAlbumCommand()),
    POST_EDIT_BONUS(new EditBonusCommand()),
    POST_EDIT_SONG(new EditSongCommand()),
    POST_EDIT_ALBUM(new EditAlbumCommand()),
    POST_ADD_MONEY(new AddMoneyCommand()),
    POST_BUY_SONG(new BuySongCommand()),

    GET_LOGIN(new LoginPageCommand()),
    GET_REGISTRATION(new RegistrationPageCommand()),
    GET_MAIN(new MainPageCommand()),
    GET_LOGOUT(new LogoutCommand()),
    GET_PROFILE(new ProfilePageCommand()),
    GET_ADMIN(new AdminPageCommand()),
    GET_EDIT(new EditPageCommand()),
    GET_EDIT_BONUS(new EditBonusPageCommand()),
    GET_EDIT_SONG(new EditSongPageCommand()),
    GET_EDIT_ALBUM(new EditAlbumPageCommand()),
    GET_ADD_MONEY(new AddMoneyPageCommand()),
    GET_INFO_SONG(new SongInfoPageCommand()),
    GET_BUY_SONG(new SongPaymentPageCommand());

    private Command command;

    CommandEnum(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
