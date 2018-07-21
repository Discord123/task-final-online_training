package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.constant.SessionAttributes;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.entity.enums.Role;
import by.epam.onlinetraining.exception.CommandException;

import java.util.Map;

public class LocaleCommand extends ActionCommand {
    private static final String EN_LOCALE_MARKER = "EN";
    private static final String EN_LOCALE_ATTRIBUTE = "en_US";
    private static final String RU_LOCALE_ATTRIBUTE = "ru_RU";
    private static final String STUDENT_PAGE = "/controller?command=getPage&expectedPage=studentpage";
    private static final String TEACHER_PAGE = "/controller?command=getPage&expectedPage=teacherpage";
    private static final String ADMIN_PAGE = "/controller?command=getPage&expectedPage=adminpage";
    private static final String EMPTY_COMMAND_PATTERN = "/controller";

    public LocaleCommand() {
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String requestLocale = content.getSingleRequestParameter(SessionAttributes.LOCALE);
        String locale = null;
        if(EN_LOCALE_MARKER.equals(requestLocale)) {
            locale = EN_LOCALE_ATTRIBUTE;
        } else {
            locale = RU_LOCALE_ATTRIBUTE;
        }
        content.setSessionAttributes(SessionAttributes.LOCALE, locale);

        String targetUrl = defineTargetPage(content);

        return new RequestResult(targetUrl, NavigationType.REDIRECT);
    }

    private String defineTargetPage(RequestContent content) {
        String currentUrl = content.getUrl();
        String targetUrl = null;

        if(currentUrl.endsWith(EMPTY_COMMAND_PATTERN)){
            Map<String, Object> sessionAttributes = content.getSessionAttributes();
            User user = (User) sessionAttributes.get(SessionAttributes.USER);
            Role userRole = user.getRole();
            switch (userRole){
                case STUDENT:
                    targetUrl = STUDENT_PAGE;
                    break;
                case TEACHER:
                    targetUrl = TEACHER_PAGE;
                    break;
                case ADMIN:
                    targetUrl = ADMIN_PAGE;
            }
        } else {
            targetUrl = currentUrl;
        }
        return targetUrl;
    }
}
