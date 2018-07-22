package by.epam.onlinetraining.entity;

import java.io.Serializable;

public class Review extends OnlineTrainingEntity implements Serializable {

    private static final long serialVersionUID = -4389004392286672590L;
    private int userId;
    private int taskId;
    private String answer;
    private String review;
    private int mark;

    public Review() {
    }

    public Review(int userId, int taskId, String answer, String review, int mark) {
        this.userId = userId;
        this.taskId = taskId;
        this.answer = answer;
        this.review = review;
        this.mark = mark;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review1 = (Review) o;

        if (userId != review1.userId) return false;
        if (taskId != review1.taskId) return false;
        if (mark != review1.mark) return false;
        if (answer != null ? !answer.equals(review1.answer) : review1.answer != null) return false;
        return review != null ? review.equals(review1.review) : review1.review == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + taskId;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + mark;
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "userId=" + userId +
                ", taskId=" + taskId +
                ", answer='" + answer + '\'' +
                ", review='" + review + '\'' +
                ", mark=" + mark +
                '}';
    }
}
