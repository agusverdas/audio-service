package edu.epam.audio.controller;

import edu.epam.audio.model.util.SessionAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE
        },
        urlPatterns = {"/pages/*"},
        initParams = { @WebInitParam(name = "INDEX_PATH", value = "index.jsp"),
                        @WebInitParam(name = "LOGIN_PATH", value = "/pages/login.jsp"),
                        @WebInitParam(name = "REGISTRATION_PATH", value = "/pages/registration.jsp")})
public class PageRedirectSecurityFilter implements Filter {
    private String indexPath;
    private String loginPath;
    private String regPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
        loginPath = filterConfig.getInitParameter("LOGIN_PATH");
        regPath = filterConfig.getInitParameter("REGISTRATION_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if(!httpRequest.getRequestURI().equalsIgnoreCase(loginPath) &&
                !httpRequest.getRequestURI().equalsIgnoreCase(regPath) &&
                httpRequest.getSession().getAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        indexPath = null;
        loginPath = null;
    }
}
