package by.epam.onlinetraining.dto;

import by.epam.onlinetraining.entity.Review;
import by.epam.onlinetraining.entity.Task;

import java.io.Serializable;

public class TaskDto implements Serializable {
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

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", courseId=" + courseId +
                ", review=" + review +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;

        if (courseId != taskDto.courseId) return false;
        if (id != null ? !id.equals(taskDto.id) : taskDto.id != null) return false;
        if (name != null ? !name.equals(taskDto.name) : taskDto.name != null) return false;
        if (description != null ? !description.equals(taskDto.description) : taskDto.description != null) return false;
        return review != null ? review.equals(taskDto.review) : taskDto.review == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + courseId;
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
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
