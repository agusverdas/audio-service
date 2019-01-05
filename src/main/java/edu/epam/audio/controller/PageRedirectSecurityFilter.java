package edu.epam.audio.controller;

import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.SessionAttributes;

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
        initParams = { @WebInitParam(name = "INDEX_PATH", value = "index.jsp")})
public class PageRedirectSecurityFilter implements Filter {
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if(!httpRequest.getRequestURI().equalsIgnoreCase(PagePath.LOGIN_PAGE) &&
                !httpRequest.getRequestURI().equalsIgnoreCase(PagePath.REGISTRATION_PAGE) &&
                httpRequest.getSession().getAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER) == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        indexPath = null;
    }
}
