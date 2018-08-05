package by.epam.onlinetraining.dto;

import by.epam.onlinetraining.entity.Review;
import by.epam.onlinetraining.entity.User;

import java.io.Serializable;

public class ReviewDto implements Serializable {

    private static final long serialVersionUID = 5281020140205201973L;
    private Integer userId;
    private Integer taskId;
    private String answer;
    private String review;
    private int mark;
    private String firstName;
    private String lastName;

    public ReviewDto() {
    }

    public ReviewDto(Review review, User user){
        this.userId = review.getUserId();
        this.taskId = review.getTaskId();
        this.answer = review.getAnswer();
        this.review = review.getReview();
        this.mark = review.getMark();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
