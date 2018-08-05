package by.epam.onlinetraining.dto;

import java.io.Serializable;

public class StatisticDTO implements Serializable {

    private static final long serialVersionUID = -4297002979065529634L;
    private int usersCount;
    private int tasksCount;
    private int coursesCount;
    private int teachersCount;
    private int subjectsCount;

    public StatisticDTO() {
    }

    public StatisticDTO(int usersCount, int tasksCount, int coursesCount, int teachersCount, int subjectsCount) {
        this.usersCount = usersCount;
        this.tasksCount = tasksCount;
        this.coursesCount = coursesCount;
        this.teachersCount = teachersCount;
        this.subjectsCount = subjectsCount;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }

    public int getCoursesCount() {
        return coursesCount;
    }

    public void setCoursesCount(int coursesCount) {
        this.coursesCount = coursesCount;
    }

    public int getTeachersCount() {
        return teachersCount;
    }

    public void setTeachersCount(int teachersCount) {
        this.teachersCount = teachersCount;
    }

    public int getSubjectsCount() {
        return subjectsCount;
    }

    public void setSubjectsCount(int subjectsCount) {
        this.subjectsCount = subjectsCount;
    }
}
