package by.epam.onlinetraining.command;

import by.epam.onlinetraining.command.impl.*;
import by.epam.onlinetraining.service.impl.CoursesServiceImpl;
import by.epam.onlinetraining.service.impl.ReviewServiceImpl;
import by.epam.onlinetraining.service.impl.TasksServiceImpl;
import by.epam.onlinetraining.service.impl.UserServiceImpl;

import java.util.EnumMap;
import java.util.Map;

import static by.epam.onlinetraining.command.CommandType.*;


public class CommandMap {
    private static Map<CommandType, AbstractCommand> commandMap = new EnumMap<>(CommandType.class);

    static{
        commandMap.put(LOGIN, new LoginCommand(new UserServiceImpl()));
        commandMap.put(LOGOUT, new LogoutCommand(new UserServiceImpl()));
        commandMap.put(SIGNUP, new SignUpCommand(new UserServiceImpl()));
        commandMap.put(LOCALE, new LocaleCommand());
        commandMap.put(GETPAGE, new GetPageCommand());
        commandMap.put(RECOVERPASSWORD, new RecoverPasswordCommand(new UserServiceImpl()));

        commandMap.put(SHOWALLTEACHERS, new ShowAllTeachersCommand(new UserServiceImpl()));
        commandMap.put(DELETEUSER, new DeleteUserCommand(new UserServiceImpl()));
        commandMap.put(SHOWALLCOURSES, new ShowAllCoursesCommand(new CoursesServiceImpl()));
        commandMap.put(EDITCOURSE, new EditCourseCommand(new CoursesServiceImpl()));
        commandMap.put(ADDCOURSE, new AddCourseCommand(new CoursesServiceImpl()));

        commandMap.put(SHOWTEACHERRELATEDCOURSES, new ShowTeacherRelatedCoursesCommand(new CoursesServiceImpl()));
        commandMap.put(SHOWCOURSERELATEDTASKS, new ShowCourseRelatedTasksCommand(new TasksServiceImpl()));
        commandMap.put(SHOWREVIEWSBYTASKID, new ShowReviewsByTaskId(new ReviewServiceImpl()));
        commandMap.put(SENDREVIEW, new SendReviewCommand(new ReviewServiceImpl()));
        commandMap.put(ADDTASK, new AddTaskCommand(new TasksServiceImpl()));

        commandMap.put(SHOWAVAILABLECOURSES, new ShowAvailableCoursesCommand(new CoursesServiceImpl()));
        commandMap.put(JOINCOURSE, new JoinCourseCommand(new UserServiceImpl()));
        commandMap.put(SHOWTAKENCOURSES, new ShowTakenCoursesCommand(new CoursesServiceImpl()));
        commandMap.put(SHOWRECEIVEDTASKS, new ShowReceivedTasksCommand(new TasksServiceImpl()));
        commandMap.put(SENDANSWER, new SendAnswerCommand(new ReviewServiceImpl()));
    }

    private CommandMap() {
    }

    public static AbstractCommand defineCommandType(String command) {
        AbstractCommand definedCommand = null;
        for (Map.Entry<CommandType, AbstractCommand> entry : commandMap.entrySet()){
            CommandType currentKey = entry.getKey();
            String currentKeyValue = currentKey.toString();
            if(currentKeyValue.equals(command)){
                definedCommand = entry.getValue();
                break;
            }
        }
        return definedCommand;
    }
}
