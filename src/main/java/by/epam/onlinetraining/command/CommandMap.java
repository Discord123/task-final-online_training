package by.epam.onlinetraining.command;

import by.epam.onlinetraining.command.impl.*;
import java.util.EnumMap;
import java.util.Map;

import static by.epam.onlinetraining.command.CommandType.*;


public class CommandMap {
    private static Map<CommandType, ActionCommand> commandMap = new EnumMap<>(CommandType.class);

    static {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(SIGNUP, new StudentSignUpCommand());
        commandMap.put(LOCALE, new LocaleCommand());
        commandMap.put(GETPAGE, new GetPageCommand());
        commandMap.put(RECOVERPASSWORD, new RecoverPasswordCommand());

        commandMap.put(ADDTEACHER, new TeacherSignUpCommand());
        commandMap.put(TAKEALLTEACHERS, new ShowAllTeachersCommand());
        commandMap.put(DELETEUSER, new DeleteUserCommand());
        commandMap.put(TAKEALLCOURSES, new ShowAllCoursesCommand());
        commandMap.put(EDITCOURSE, new EditCourseCommand());
        commandMap.put(ADDCOURSE, new AddCourseCommand());

        commandMap.put(TAKETEACHERRELATEDCOURSES, new ShowTeacherRelatedCoursesCommand());
        commandMap.put(TAKECOURSERELATEDTASKS, new ShowCourseRelatedTasksCommand());
        commandMap.put(TAKEREVIEWSBYTASKID, new ShowReviewsByTaskId());
        commandMap.put(SENDREVIEW, new SendReviewCommand());
        commandMap.put(ADDTASK, new AddTaskCommand());

        commandMap.put(TAKEAVAILABLECOURSES, new ShowAvailableCoursesCommand());
        commandMap.put(JOINCOURSE, new JoinCourseCommand());
        commandMap.put(GETTAKENCOURSES, new ShowTakenCoursesCommand());
        commandMap.put(TAKERECEIVEDTASKS, new ShowReceivedTasksCommand());
        commandMap.put(SENDANSWER, new SendAnswerCommand());
    }

    private CommandMap() {
    }

    public static ActionCommand defineCommandType(String command) {
        ActionCommand definedCommand = null;
        for (Map.Entry<CommandType, ActionCommand> entry : commandMap.entrySet()) {
            CommandType currentCommandKey = entry.getKey();
            String currentKeyValue = currentCommandKey.toString();
            if (currentKeyValue.equals(command)) {
                definedCommand = entry.getValue();
                break;
            }
        }
        return definedCommand;
    }
}
