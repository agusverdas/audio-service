package edu.epam.audio.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

public class RequestContent {
    private HashMap<String, String[]> requestParamsMap = new HashMap<>();
    private HashMap<String, Object> requestAttributesMap = new HashMap<>();
    private HashMap<String, Object> sessionAttributesMap = new HashMap<>();
    private HashMap<String, Part> requestPartMap = new HashMap<>();
    private boolean logout = false;
    private String requestPath;

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
        if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
            Collection<Part> parts = request.getParts();
            parts.forEach(p -> {
                String name = p.getName();
                requestPartMap.put(name, p);
            });
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

    public void setRequestPart(String key, Part part) { requestPartMap.put(key,part); }

    public Part getRequestPart(String key) { return requestPartMap.get(key); }

    public void removeRequestAttribute(String key) { requestAttributesMap.remove(key); }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }
}
