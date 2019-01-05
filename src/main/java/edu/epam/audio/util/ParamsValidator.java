package edu.epam.audio.util;

public class ParamsValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$";
    private static final String NAME_REGEX = "\\w{6,16}";
    private static final double MAX_COST = 999_999_999.99;

    private ParamsValidator(){}

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateName(String name){
        return name.matches(NAME_REGEX);
    }

    public static boolean validateMoney(double cost) { return cost < MAX_COST; }
}
