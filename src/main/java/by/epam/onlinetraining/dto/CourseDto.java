package by.epam.onlinetraining.dto;

import by.epam.onlinetraining.entity.Course;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.entity.User;

import java.io.Serializable;

public class CourseDto implements Serializable {
    private Integer id;
    private String title;
    private Status status;
    private boolean isAvailable;
    private User teacher;
    private Subject subject;

    public CourseDto() {
    }

    public CourseDto(Course course, Subject subject, User teacher){
        this.id = course.getId();
        this.title = course.getTitle();
        this.status = course.getStatus();
        this.isAvailable = course.isAvailable();
        this.teacher = teacher;
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
