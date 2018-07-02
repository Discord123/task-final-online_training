package by.epam.onlinetraining.command.factory;

import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.command.CommandMap;
import by.epam.onlinetraining.content.RequestContent;


public class CommandFactory {
    private static final String COMMAND_PARAMETER = "command";

    public AbstractCommand initCommand(RequestContent content) {
        AbstractCommand command = null;
        String commandName = null;

        String commandIncomingName = content.getSingleRequestParameter(COMMAND_PARAMETER);
        if (commandIncomingName != null && !commandIncomingName.isEmpty()) {
            commandName = commandIncomingName.toUpperCase();
            command = CommandMap.defineCommandType(commandName);
        }

        return command;
    }
}
