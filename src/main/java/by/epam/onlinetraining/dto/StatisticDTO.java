package by.epam.onlinetraining.dto;

import java.io.Serializable;

public class StatisticDTO implements Serializable {

    private static final long serialVersionUID = -4297002979065529634L;
    private int usersCount;
    private int tasksCount;
    private int coursesCount;
    private int teachersCount;
    private int subjectsCount;
    private int englishLanguageCount;
    private int germanLanguageCount;
    private int frenchLanguageCount;
    private int italianLanguageCount;
    private int spanishLanguageCount;

    public StatisticDTO() {
    }

    public StatisticDTO(int usersCount, int tasksCount, int coursesCount, int teachersCount, int subjectsCount,
                        int englishLanguageCount, int germanLanguageCount, int frenchLanguageCount,
                        int italianLanguageCount, int spanishLanguageCount) {
        this.usersCount = usersCount;
        this.tasksCount = tasksCount;
        this.coursesCount = coursesCount;
        this.teachersCount = teachersCount;
        this.subjectsCount = subjectsCount;
        this.englishLanguageCount = englishLanguageCount;
        this.germanLanguageCount = germanLanguageCount;
        this.frenchLanguageCount = frenchLanguageCount;
        this.italianLanguageCount = italianLanguageCount;
        this.spanishLanguageCount = spanishLanguageCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getEnglishLanguageCount() {
        return englishLanguageCount;
    }

    public void setEnglishLanguageCount(int englishLanguageCount) {
        this.englishLanguageCount = englishLanguageCount;
    }

    public int getGermanLanguageCount() {
        return germanLanguageCount;
    }

    public void setGermanLanguageCount(int germanLanguageCount) {
        this.germanLanguageCount = germanLanguageCount;
    }

    public int getFrenchLanguageCount() {
        return frenchLanguageCount;
    }

    public void setFrenchLanguageCount(int frenchLanguageCount) {
        this.frenchLanguageCount = frenchLanguageCount;
    }

    public int getItalianLanguageCount() {
        return italianLanguageCount;
    }

    public void setItalianLanguageCount(int italianLanguageCount) {
        this.italianLanguageCount = italianLanguageCount;
    }

    public int getSpanishLanguageCount() {
        return spanishLanguageCount;
    }

    public void setSpanishLanguageCount(int spanishLanguageCount) {
        this.spanishLanguageCount = spanishLanguageCount;
    }
}
