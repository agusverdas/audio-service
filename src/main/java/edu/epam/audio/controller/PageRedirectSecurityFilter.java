package edu.epam.audio.controller;

import edu.epam.audio.model.util.WebValuesNames;

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
        initParams = { @WebInitParam(name = "LOGIN_PATH", value = "/pages/login.jsp")})
public class PageRedirectSecurityFilter implements Filter {
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("LOGIN_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        System.out.println("Filter for security : " + httpRequest.getRequestURI());

        if(!httpRequest.getRequestURI().equalsIgnoreCase(indexPath) &&
                httpRequest.getSession().getAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER) == null) {
            System.out.println("Came here");
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        indexPath = null;
    }
}
