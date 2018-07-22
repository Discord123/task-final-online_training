package by.epam.onlinetraining.tag;

import by.epam.onlinetraining.bundles.MessageManager;
import by.epam.onlinetraining.entity.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class WelcomeTag extends TagSupport {
    private static final Logger Logger = LogManager.getLogger(WelcomeTag.class);
    private static final String MESSAGE_BEGIN_KEY = "label.mainpage.begin";
    private static final String MESSAGE_END_ADMIN_KEY = "label.mainpage.end-admin";
    private static final String MESSAGE_END_TEACHER_KEY = "label.mainpage.end-teacher";
    private static final String MESSAGE_END_STUDENT_KEY = "label.mainpage.end-student";
    private static final String OUTPUT_FORMAT_BEGIN = "<hr><h2 align=\"center\">";
    private static final String OUTPUT_FORMAT_END = "</h2><hr>";

    private Role role;
    private String locale;
    private String fullName;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        MessageManager manager = MessageManager.getManager(locale);
        String messageBegin = manager.getMessage(MESSAGE_BEGIN_KEY);
        String messageEnd = null;

        if(role == Role.ADMIN){
            messageEnd = manager.getMessage(MESSAGE_END_ADMIN_KEY);
        } else if (role == Role.TEACHER){
            messageEnd = manager.getMessage(MESSAGE_END_TEACHER_KEY);
        } else {
            messageEnd = manager.getMessage(MESSAGE_END_STUDENT_KEY);
        }

        String outputMessage = messageBegin + fullName + messageEnd;
        try{
            JspWriter writer = pageContext.getOut();
            writer.write(OUTPUT_FORMAT_BEGIN + outputMessage + OUTPUT_FORMAT_END);
        } catch (IOException e){
            Logger.log(Level.FATAL, "Fail to send an email:\n" + e.getMessage(), e);
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
