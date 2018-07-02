package by.epam.onlinetraining.command.impl;

import by.epam.onlinetraining.bundles.ConfigurationManager;
import by.epam.onlinetraining.command.AbstractCommand;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.RequestResult;
import by.epam.onlinetraining.dto.ReviewDto;
import by.epam.onlinetraining.exceptions.CommandException;
import by.epam.onlinetraining.exceptions.ServiceException;
import by.epam.onlinetraining.service.ReviewService;
import by.epam.onlinetraining.service.Service;

import java.util.List;

public class ShowReviewsByTaskId extends AbstractCommand {
    private static final String TASK_REVIEWS_PAGE_PATH = ConfigurationManager.getProperty("path.page.taskreviews");
    private static final String TASK_ID_PARAM = "taskid";
    private static final String REVIEWS_AND_USERS_DTO_ATTR = "reviewsAndUsers";

    public ShowReviewsByTaskId(Service service) {
        super(service);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String taskIdLine = content.getSingleRequestParameter(TASK_ID_PARAM);
        int taskId = Integer.parseInt(taskIdLine);

        try{
            ReviewService reviewReceiver = (ReviewService) getService();
            List<ReviewDto> reviewDtoList = reviewReceiver.showReviewsByTaskId(taskId);
            content.setSessionAttributes(REVIEWS_AND_USERS_DTO_ATTR, reviewDtoList);

        } catch (ServiceException e){
            throw new CommandException("Fail to execute show reviews by task id command.",e);
        }
        return new RequestResult(TASK_REVIEWS_PAGE_PATH, NavigationType.FORWARD);
    }
}
