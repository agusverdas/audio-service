package edu.epam.audio.model.command;

import edu.epam.audio.model.command.impl.*;

public enum CommandEnum {
    POST_LOGIN(new LoginCommand()),
    POST_REGISTRATION(new RegistrationCommand()),
    POST_EDIT_PROFILE(new EditProfileCommand()),
    POST_ADD_SONG(new AddSongCommand()),

    GET_LOGIN(new LoginPageCommand()),
    GET_REGISTRATION(new RegistrationPageCommand()),
    GET_MAIN(new MainPageCommand()),
    GET_LOGOUT(new LogoutCommand()),
    GET_PROFILE(new ProfilePageCommand()),
    GET_ADMIN(new AdminPageCommand()),
    GET_EDIT(new EditPageCommand());

    private Command command;

    CommandEnum(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
