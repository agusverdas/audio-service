package edu.epam.audio.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class WebParamWrapper {
    private HashMap<String, String[]> requestParamsMap;
    private HashMap<String, Object> requestAttributesMap;
    private HashMap<String, Object> sessionAttributesMap;

    private static Logger logger = LogManager.getLogger();

    public void init(HttpServletRequest request){
        requestParamsMap = new HashMap<>(request.getParameterMap());
        requestAttributesMap = new HashMap<>();
        sessionAttributesMap = new HashMap<>();

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
    }

    public void extractValues(HttpServletRequest request){
        requestAttributesMap.forEach(request::setAttribute);

        HttpSession session = request.getSession();

        sessionAttributesMap.forEach(session::setAttribute);
    }

    public String getRequestParam(String key){
        return new ArrayList<>(Arrays.asList(requestParamsMap.get(key))).get(0);
    }

    public List<String> getRequestParams(String key){
        return new ArrayList<>(Arrays.asList(requestParamsMap.get(key)));
    }

    public void setRequestAttribute(String key, Object value){
        requestAttributesMap.put(key, value);
    }

    public Object getRequestAttribute(String key) { return requestAttributesMap.get(key); }

    public void setSessionAttribute(String key, Object value){
        sessionAttributesMap.put(key, value);
    }

    public Object getSessionAttribute(String key) { return sessionAttributesMap.get(key); }
}
