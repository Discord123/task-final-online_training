package by.epam.onlinetraining.filter;

import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    private static final String COMMAND_PARAMETER = "command";
    private static final String UNAUTHORIZED_ROLE = "ALL";
    private static final String INDEX_PAGE_PATH = "/index.jsp";
    Map<String, String> accessMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap.put("LOGIN", "ALL");
        accessMap.put("LOGOUT", "ALL");
        accessMap.put("SIGNUP", "ALL");
        accessMap.put("LOCALE", "ALL");
        accessMap.put("GETPAGE", "ALL");
        accessMap.put("RECOVERPASSWORD", "ALL");

        accessMap.put("SHOWALLTEACHERS", "ADMIN");
        accessMap.put("DELETEUSER", "ADMIN");
        accessMap.put("SHOWALLCOURSES", "ADMIN");
        accessMap.put("EDITCOURSE", "ADMIN");
        accessMap.put("ADDCOURSE", "ADMIN");

        accessMap.put("SHOWTEACHERRELATEDCOURSES", "TEACHER");
        accessMap.put("SHOWCOURSERELATEDTASKS", "TEACHER");
        accessMap.put("SHOWREVIEWSBYTASKID", "TEACHER");
        accessMap.put("SENDREVIEW", "TEACHER");
        accessMap.put("ADDTASK", "TEACHER");

        accessMap.put("SHOWAVAILABLECOURSES", "STUDENT");
        accessMap.put("JOINCOURSE", "STUDENT");
        accessMap.put("SHOWTAKENCOURSES", "STUDENT");
        accessMap.put("SHOWRECEIVEDTASKS", "STUDENT");
        accessMap.put("SENDANSWER", "STUDENT");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String commandValue = httpServletRequest.getParameter(COMMAND_PARAMETER);
        if(isCommandValid(commandValue)){
            String command = commandValue.toUpperCase();

            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute(SessionAttributes.USER);

            String userRole = null;
            if (isUserAuthorised(user)) {
                Role roleValue = user.getRole();
                userRole = roleValue.toString();
            } else {
                userRole = UNAUTHORIZED_ROLE;
            }
            if (!isCommandAllowed(command, userRole)) {
                httpServletResponse.sendRedirect(INDEX_PAGE_PATH);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private boolean isUserAuthorised(User user) {
        return (user != null) && (user.getRole() != null);
    }

    private boolean isCommandValid(String commandValue) {
        boolean isValid = false;
        if(commandValue != null){
            String commandUpper = commandValue.toUpperCase();
            if(accessMap.containsKey(commandUpper)){
                isValid = true;
            }
        }
        return isValid;
    }

    private boolean isCommandAllowed(String command, String userRole){
        boolean isAllowed = false;
        String allowedFor = accessMap.get(command);
        if(UNAUTHORIZED_ROLE.equalsIgnoreCase(allowedFor) || allowedFor.equalsIgnoreCase(userRole)){
            isAllowed = true;
        }
        return isAllowed;
    }
}
