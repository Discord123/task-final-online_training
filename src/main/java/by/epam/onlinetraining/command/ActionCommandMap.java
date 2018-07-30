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
        commandMap.put(SAVESTATISTIC, new SaveStatisticCommand());
        commandMap.put(SHOWALLTEACHERS, new ShowAllTeachersCommand());
        commandMap.put(DELETEUSER, new DeleteUserCommand());
        commandMap.put(SHOWALLCOURSES, new ShowAllCoursesCommand());
        commandMap.put(EDITCOURSE, new EditCourseCommand());
        commandMap.put(ADDCOURSE, new AddCourseCommand());

        commandMap.put(SHOWTEACHERRELATEDCOURSES, new TakeTeacherRelatedCoursesCommand());
        commandMap.put(SHOWCOURSERELATEDTASKS, new TakeCourseRelatedTasksCommand());
        commandMap.put(SHOWREVIEWSBYTASKID, new TakeReviewsByTaskId());
        commandMap.put(SENDREVIEW, new SendReviewCommand());
        commandMap.put(ADDTASK, new AddTaskCommand());

        commandMap.put(SHOWAVAILABLECOURSES, new TakeAvailableCoursesCommand());
        commandMap.put(JOINCOURSE, new JoinCourseCommand());
        commandMap.put(SHOWTAKENCOURSES, new GetTakenCoursesCommand());
        commandMap.put(SHOWRECEIVEDTASKS, new TakeReceivedTasksCommand());
        commandMap.put(SENDANSWER, new SendAnswerCommand());
    }

    private ActionCommandMap() {
    }

    public static ActionCommand defineCommandType(String command) {
        ActionCommandType actionCommandType = ActionCommandType.valueOf(command);
        return commandMap.get(actionCommandType);
    }
}
