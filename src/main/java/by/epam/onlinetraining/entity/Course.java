package by.epam.onlinetraining.entity;

import by.epam.onlinetraining.entity.enums.Status;

import java.io.Serializable;

public class Course extends OnlineTrainingEntity implements Serializable{
    private int id;
    private String title;
    private int subjectId;
    private Status status;
    private boolean isAvailable;
    private int teacherId;

    public Course() {
    }

    public Course(int id, String title, int subjectId, Status status, boolean isAvailable, int teacherId) {
        this.id = id;
        this.title = title;
        this.subjectId = subjectId;
        this.status = status;
        this.isAvailable = isAvailable;
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subjectId=" + subjectId +
                ", status=" + status +
                ", isAvailable=" + isAvailable +
                ", teacherId=" + teacherId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (subjectId != course.subjectId) return false;
        if (isAvailable != course.isAvailable) return false;
        if (teacherId != course.teacherId) return false;
        if (title != null ? !title.equals(course.title) : course.title != null) return false;
        return status == course.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + subjectId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isAvailable ? 1 : 0);
        result = 31 * result + teacherId;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
