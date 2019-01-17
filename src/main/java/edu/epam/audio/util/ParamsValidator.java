package edu.epam.audio.util;

/**
 * Валидатор веб-параметров
 */
public class ParamsValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$";
    private static final String NAME_REGEX = "\\w{6,16}";
    private static final double MAX_COST = 999_999_999.99;

    private ParamsValidator(){}

    /**
     * Проверка пароля
     * @param password Пароль
     * @return Результат проверки
     */
    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * Проверка имени
     * @param name Имя
     * @return Результат проверки
     */
    public static boolean validateName(String name){
        return name.matches(NAME_REGEX);
    }

    /**
     * Проверка денег
     * @param cost Сумма
     * @return Результат проверки
     */
    public static boolean validateMoney(double cost) { return cost < MAX_COST; }
}
