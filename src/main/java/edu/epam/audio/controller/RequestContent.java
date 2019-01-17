package edu.epam.audio.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

/**
 * Класс-оболочка над запросом
 */
public class RequestContent {
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    private HashMap<String, String[]> requestParamsMap = new HashMap<>();
    private HashMap<String, Object> requestAttributesMap = new HashMap<>();
    private HashMap<String, Object> sessionAttributesMap = new HashMap<>();
    private HashMap<String, Part> requestPartMap = new HashMap<>();
    private boolean logout = false;
    private String requestPath;

    /**
     * Инициализация объекта параметрами запроса, его аттрибутами, аттрибутами сессии, сохранение пути запроса
     * @param request Запрос
     * @throws IOException
     * @throws ServletException
     */
    public void init(HttpServletRequest request) throws IOException, ServletException {
        requestPath = request.getServletContext().getRealPath("");
        requestParamsMap = new HashMap<>(request.getParameterMap());

        Enumeration e = request.getAttributeNames();
        while (e.hasMoreElements()){
            String key = (String) e.nextElement();
            Object value = request.getAttribute(key);

            requestAttributesMap.put(key, value);
        }

        HttpSession session = request.getSession();

        Enumeration<String> en = session.getAttributeNames();
        while (en.hasMoreElements()){
            String key = en.nextElement();
            Object value = session.getAttribute(key);

            sessionAttributesMap.put(key, value);
        }
        if (request.getContentType() != null && request.getContentType().toLowerCase().contains(MULTIPART_FORM_DATA)) {
            Collection<Part> parts = request.getParts();
            parts.forEach(p -> {
                String name = p.getName();
                requestPartMap.put(name, p);
            });
        }
    }

    /**
     * Извлечение значений объекта в запрос
     * @param request Запрос
     */
    public void extractValues(HttpServletRequest request){
        requestAttributesMap.forEach(request::setAttribute);

        HttpSession session = request.getSession();

        sessionAttributesMap.forEach(session::setAttribute);
    }

    /**
     * Получение параметра
     * @param key Ключ
     */
    public String getRequestParam(String key){
        return new ArrayList<>(Arrays.asList(requestParamsMap.get(key))).get(0);
    }

    /**
     * Получение параметров
     * @param key Ключ
     */
    public List<String> getRequestParams(String key){
        return new ArrayList<>(Arrays.asList(requestParamsMap.get(key)));
    }

    /**
     * Установка аттрибута
     * @param key Ключ
     * @param value Значение
     */
    public void setRequestAttribute(String key, Object value){
        requestAttributesMap.put(key, value);
    }

    /**
     * Получение аттрибута
     * @param key Ключ
     */
    public Object getRequestAttribute(String key) { return requestAttributesMap.get(key); }

    /**
     * Установка аттрибута сессии
     * @param key Ключ
     * @param value Значение
     */
    public void setSessionAttribute(String key, Object value){
        sessionAttributesMap.put(key, value);
    }

    /**
     * Получение аттрибута сессии
     * @param key Ключ
     */
    public Object getSessionAttribute(String key) { return sessionAttributesMap.get(key); }

    /**
     * Получение параметра типа file
     * @param key Ключ
     */
    public Part getRequestPart(String key) { return requestPartMap.get(key); }

    /**
     * Проверка авториации
     */
    boolean isLogout() {
        return logout;
    }

    /**
     * Установка авториации
     */
    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    /**
     * Получение пути запроса
     */
    public String getRequestPath() {
        return requestPath;
    }
}
