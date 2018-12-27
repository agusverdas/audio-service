package edu.epam.audio.model.custom;

import edu.epam.audio.model.entity.Privileges;
import edu.epam.audio.model.entity.User;
import edu.epam.audio.model.util.WebValuesNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AdminButtonTag extends TagSupport {
    //todo: ask Ситуация такая: Проверка на null нужна для разлогина с обоих вкладок, но фильтр кидает исключение, если не проверить на null,
    //todo: получается, что в любом случае, сначала работает jsp, а после фильтр на нее? Можно ли изменить порядок?
    @Override
    public int doStartTag() throws JspException {
        try{
            HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
            HttpSession session = request.getSession();
            String requestPath = request.getContextPath();

            String button = "<a class=\"nav-item nav-link\" href=\"" + requestPath + "/pages/admin_panel.jsp\">Admin panel</a>";

            User user = (User) session.getAttribute(WebValuesNames.SESSION_ATTRIBUTE_USER);

            if (user != null && user.getRole() == Privileges.ADMIN) {
                pageContext.getOut().write(button);
            }
        } catch (IOException e) {
            throw new JspException("Exception in custom tag.", e);
        }
        return SKIP_BODY;
    }
}
