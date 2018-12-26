package edu.epam.audio.model.command;

import edu.epam.audio.model.command.impl.*;

public enum CommandEnum {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    ADD_SONG(new AddSongCommand());

    private Command command;

    CommandEnum(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
