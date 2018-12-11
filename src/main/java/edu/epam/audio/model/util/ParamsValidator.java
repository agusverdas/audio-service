package edu.epam.audio.model.util;

//todo: change regex
public final class ParamsValidator {
    private static final String PASSWORD_REGEX = "[\\w|\\d]+";
    private static final String NAME_REGEX = "\\w+";

    private ParamsValidator(){

    }

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name){
        return name.matches(NAME_REGEX);
    }
}
