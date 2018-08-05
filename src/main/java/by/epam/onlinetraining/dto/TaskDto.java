package by.epam.onlinetraining.dto;

import by.epam.onlinetraining.entity.Review;
import by.epam.onlinetraining.entity.Task;

import java.io.Serializable;

public class TaskDto implements Serializable {

    private static final long serialVersionUID = -2646509672244042786L;
    private Integer id;
    private String name;
    private String description;
    private int courseId;
    private Review review;

    public TaskDto() {
    }

    public TaskDto(Task task, Review review) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.courseId = task.getCourseId();
        this.review = review;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
