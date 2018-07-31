package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.command.util.PagePathManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.exception.CommandException;

public class GetPageCommand extends ActionCommand {
    private static final String PROPERTY_PREFIX = "path.page.";
    private static final String EXPECTED_PAGE_PARAMETER = "expectedPage";

    @Override
    public ActionResult execute(RequestContent requestContent) throws CommandException {
        String expectedPage = requestContent.getSingleRequestParameter(EXPECTED_PAGE_PARAMETER);
        String pageKey = PROPERTY_PREFIX + expectedPage;
        String page = PagePathManager.getProperty(pageKey);
        return new ActionResult(page, NavigationType.FORWARD);
    }
}
