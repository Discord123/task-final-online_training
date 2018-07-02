package by.epam.onlinetraining.dto;

import by.epam.onlinetraining.entity.Review;
import by.epam.onlinetraining.entity.User;

import java.io.Serializable;

public class ReviewDto implements Serializable {
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

    @Override
    public String toString() {
        return "ReviewDto{" +
                "userId=" + userId +
                ", taskId=" + taskId +
                ", answer='" + answer + '\'' +
                ", review='" + review + '\'' +
                ", mark=" + mark +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewDto that = (ReviewDto) o;

        if (mark != that.mark) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (review != null ? !review.equals(that.review) : that.review != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
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
