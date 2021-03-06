package by.epam.onlinetraining.command;

import by.epam.onlinetraining.content.RequestContent;


public class CommandFactory {
    private static final String COMMAND_PARAMETER = "command";

    public ActionCommand initCommand(RequestContent content) {
        ActionCommand command = null;
        String commandName = null;

        String incomingCommandName = content.getSingleRequestParameter(COMMAND_PARAMETER);
        if (incomingCommandName != null && !incomingCommandName.isEmpty()) {
            commandName = incomingCommandName.toUpperCase();
            command = ActionCommandMap.defineCommandType(commandName);
        }

        return command;
    }
}
