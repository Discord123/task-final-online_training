package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.bundle.PagePathManager;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.dto.StatisticDTO;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.exception.ServiceException;
import by.epam.onlinetraining.service.ServiceManager;
import by.epam.onlinetraining.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GetStatisticCommand extends ActionCommand {

    private static final Logger Logger = LogManager.getLogger(GetStatisticCommand.class);
    private static final String STATISTIC_PATH = PagePathManager.getProperty("path.page.statistic");
    private static final String STATISTIC_PARAM = "getStatistic";

    public GetStatisticCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {

        try {
            UserService userService = (UserService) getService();
            StatisticDTO statisticDTO = userService.getStatistic();
            requestContent.setSessionAttributes(STATISTIC_PARAM, statisticDTO);
        } catch (ServiceException e) {
            Logger.log(Level.FATAL,"Exception during statistic command");
            throw new CommandException("Exception during statistic command", e);
        }

        return new ActionResult(STATISTIC_PATH, NavigationType.FORWARD);
    }
}
