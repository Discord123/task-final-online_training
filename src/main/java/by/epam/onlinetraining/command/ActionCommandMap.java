package by.epam.onlinetraining.command;

import by.epam.onlinetraining.command.impl.*;
import java.util.EnumMap;
import java.util.Map;

import static by.epam.onlinetraining.command.ActionCommandType.*;


public class ActionCommandMap {
    private static Map<ActionCommandType, ActionCommand> commandMap = new EnumMap<>(ActionCommandType.class);

    static {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(SIGNUP, new StudentSignUpCommand());
        commandMap.put(LOCALE, new LocaleCommand());
        commandMap.put(GETPAGE, new GetPageCommand());
        commandMap.put(RECOVERPASSWORD, new RecoverPasswordCommand());

        commandMap.put(ADDTEACHER, new TeacherSignUpCommand());
        commandMap.put(GETSTATISTIC, new GetStatisticCommand());
        commandMap.put(TAKEALLTEACHERS, new TakeAllTeachersCommand());
        commandMap.put(DELETEUSER, new DeleteUserCommand());
        commandMap.put(TAKEALLCOURSES, new TakeAllCoursesCommand());
        commandMap.put(EDITCOURSE, new EditCourseCommand());
        commandMap.put(ADDCOURSE, new AddCourseCommand());

        commandMap.put(TAKETEACHERRELATEDCOURSES, new TakeTeacherRelatedCoursesCommand());
        commandMap.put(TAKECOURSERELATEDTASKS, new TakeCourseRelatedTasksCommand());
        commandMap.put(TAKEREVIEWSBYTASKID, new TakeReviewsByTaskId());
        commandMap.put(SENDREVIEW, new SendReviewCommand());
        commandMap.put(ADDTASK, new AddTaskCommand());

        commandMap.put(TAKEAVAILABLECOURSES, new TakeAvailableCoursesCommand());
        commandMap.put(JOINCOURSE, new JoinCourseCommand());
        commandMap.put(GETTAKENCOURSES, new GetTakenCoursesCommand());
        commandMap.put(TAKERECEIVEDTASKS, new TakeReceivedTasksCommand());
        commandMap.put(SENDANSWER, new SendAnswerCommand());
    }

    private ActionCommandMap() {
    }

    public static ActionCommand defineCommandType(String command) {
        ActionCommandType actionCommandType = ActionCommandType.valueOf(command);
        return commandMap.get(actionCommandType);
    }
}
