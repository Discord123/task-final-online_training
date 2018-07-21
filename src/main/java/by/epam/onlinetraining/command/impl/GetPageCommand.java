package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.exception.CommandException;

public class GetPageCommand extends ActionCommand {
    private static final String PROPERTY_PREFIX = "path.page.";
    private static final String EXPECTED_PAGE_PARAMETER = "expectedPage";

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        String expectedPage = requestContent.getSingleRequestParameter(EXPECTED_PAGE_PARAMETER);
        String pageKey = PROPERTY_PREFIX + expectedPage;
        String page = ConfigurationManager.getProperty(pageKey);
        return new RequestResult(page, NavigationType.FORWARD);
    }
}
