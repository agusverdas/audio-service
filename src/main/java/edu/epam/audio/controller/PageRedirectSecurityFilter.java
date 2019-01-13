package edu.epam.audio.controller;

import edu.epam.audio.entity.Privileges;
import edu.epam.audio.entity.User;
import edu.epam.audio.util.PagePath;
import edu.epam.audio.util.SessionAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.epam.audio.util.PagePath.*;

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
        User user = (User) httpRequest.getSession().getAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);

        if(!httpRequest.getRequestURI().equalsIgnoreCase(LOGIN_PAGE) &&
                !httpRequest.getRequestURI().equalsIgnoreCase(REGISTRATION_PAGE) &&
                user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }

        if(     (httpRequest.getRequestURI().equalsIgnoreCase(ADMIN_PAGE) ||
                httpRequest.getRequestURI().equalsIgnoreCase(EDIT_ALBUM_PAGE) ||
                httpRequest.getRequestURI().equalsIgnoreCase(EDIT_BONUS_PAGE) ||
                httpRequest.getRequestURI().equalsIgnoreCase(EDIT_SONG_PAGE)) &&
                user.getRole() != Privileges.ADMIN) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        indexPath = null;
    }
}
