package edu.epam.audio.util;

/**
 * Класс для защиты от Xss атаки
 */
public class FilterXSS {
    /**
     * Метод фильтрующий входную строку
     * @param string Входная строка
     * @return Измененная строка
     */
    public static String filterXSS(String string){
        return string.replaceAll("<", "&lt;").replaceAll(">", "gt;");
    }
}
