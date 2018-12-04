package edu.epam.audio.model.command;

import edu.epam.audio.model.command.impl.LoginCommand;
import edu.epam.audio.model.command.impl.LogoutCommand;

public enum CommandEnum {
    LOGIN(new LoginCommand()), LOGOUT(new LogoutCommand());

    private Command command;

    CommandEnum(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
