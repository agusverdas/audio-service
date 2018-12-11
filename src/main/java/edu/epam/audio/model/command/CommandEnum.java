package edu.epam.audio.model.command;

import edu.epam.audio.model.command.impl.LoginCommand;
import edu.epam.audio.model.command.impl.LogoutCommand;
import edu.epam.audio.model.command.impl.RegistrationCommand;

public enum CommandEnum {
    LOGIN(new LoginCommand()), LOGOUT(new LogoutCommand()), REGISTRATION(new RegistrationCommand());

    private Command command;

    CommandEnum(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
