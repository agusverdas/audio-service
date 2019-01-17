package edu.epam.audio.custom;

import edu.epam.audio.entity.Privileges;
import edu.epam.audio.entity.User;
import edu.epam.audio.command.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Пользовательский тег, показывает кнопку Admin, когда пользователь имее права администратора.
 */
public class AdminButtonTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try{
            HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
            HttpSession session = request.getSession();
            String requestPath = request.getContextPath();

            String button = "<a class=\"nav-item nav-link\" href=\"" + requestPath + "/Controller?command=get-admin\">" +
                    "Admin</a>";

            User user = (User) session.getAttribute(SessionAttributes.SESSION_ATTRIBUTE_USER);

            if (user != null && user.getRole() == Privileges.ADMIN) {
                pageContext.getOut().write(button);
            }
        } catch (IOException e) {
            throw new JspException("Exception in custom tag.", e);
        }
        return SKIP_BODY;
    }
}
