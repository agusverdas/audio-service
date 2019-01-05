package edu.epam.audio.util;

public class ParamsValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$";
    private static final String NAME_REGEX = "\\w{6,16}";

    private ParamsValidator(){}

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name){
        return name.matches(NAME_REGEX);
    }
}
