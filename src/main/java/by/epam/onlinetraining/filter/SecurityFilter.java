package by.epam.onlinetraining.filter;

import by.epam.onlinetraining.command.constant.SessionAttribute;
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
    private static final String INVALIDATE_SESSION_MARKER = "invalidate";
    Map<String, String> accessMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap.put("LOGIN", "ALL");
        accessMap.put("LOGOUT", "ALL");
        accessMap.put("SIGNUP", "ALL");
        accessMap.put("LOCALE", "ALL");
        accessMap.put("GETPAGE", "ALL");
        accessMap.put("RECOVERPASSWORD", "ALL");

        accessMap.put("ADDTEACHER", "ADMIN");
        accessMap.put("GETSTATISTIC", "ADMIN");
        accessMap.put("SAVESTATISTIC", "ADMIN");
        accessMap.put("TAKEALLTEACHERS", "ADMIN");
        accessMap.put("DELETEUSER", "ADMIN");
        accessMap.put("TAKEALLCOURSES", "ADMIN");
        accessMap.put("EDITCOURSE", "ADMIN");
        accessMap.put("ADDCOURSE", "ADMIN");

        accessMap.put("TAKETEACHERRELATEDCOURSES", "TEACHER");
        accessMap.put("TAKECOURSERELATEDTASKS", "TEACHER");
        accessMap.put("TAKEREVIEWSBYTASKID", "TEACHER");
        accessMap.put("SENDREVIEW", "TEACHER");
        accessMap.put("ADDTASK", "TEACHER");

        accessMap.put("TAKEAVAILABLECOURSES", "STUDENT");
        accessMap.put("JOINCOURSE", "STUDENT");
        accessMap.put("GETETAKENCOURSES", "STUDENT");
        accessMap.put("TAKERECEIVEDTASKS", "STUDENT");
        accessMap.put("SENDANSWER", "STUDENT");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if(httpServletRequest.getSession() != null){
            Object invalidate = httpServletRequest.getSession().getAttribute(INVALIDATE_SESSION_MARKER);
            if(invalidate != null && (boolean) invalidate){
                httpServletRequest.getSession().invalidate();
                httpServletResponse.sendRedirect(INDEX_PAGE_PATH);
                return;
            }
        }

        String commandValue = httpServletRequest.getParameter(COMMAND_PARAMETER);
        if(isCommandValid(commandValue)){
            String command = commandValue.toUpperCase();

            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute(SessionAttribute.USER);

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
