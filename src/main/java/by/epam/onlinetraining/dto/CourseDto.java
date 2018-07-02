package by.epam.onlinetraining.dto;

import by.epam.onlinetraining.entity.Course;
import by.epam.onlinetraining.entity.Subject;
import by.epam.onlinetraining.entity.User;
import by.epam.onlinetraining.entity.enums.Status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDto courseDto = (CourseDto) o;

        if (isAvailable != courseDto.isAvailable) return false;
        if (id != null ? !id.equals(courseDto.id) : courseDto.id != null) return false;
        if (title != null ? !title.equals(courseDto.title) : courseDto.title != null) return false;
        if (status != courseDto.status) return false;
        if (teacher != null ? !teacher.equals(courseDto.teacher) : courseDto.teacher != null) return false;
        return subject != null ? subject.equals(courseDto.subject) : courseDto.subject == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", isAvailable=" + isAvailable +
                ", teacher=" + teacher +
                ", subject=" + subject +
                '}';
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
