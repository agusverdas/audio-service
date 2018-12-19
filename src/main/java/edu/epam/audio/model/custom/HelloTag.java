package edu.epam.audio.model.custom;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HelloTag extends TagSupport {
    private String role;

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        try{
            System.out.println("ROle : " + role);
            String to;
            if("ADMIN".equalsIgnoreCase(role)){
                to = "Hello, " + role;
            } else{
                to = "Welcome, " + role;
            }
            pageContext.getOut().write(to + "<hr/>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
