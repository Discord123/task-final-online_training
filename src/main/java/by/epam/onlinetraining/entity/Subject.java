package by.epam.onlinetraining.entity;

import by.epam.onlinetraining.entity.enums.Language;
import by.epam.onlinetraining.entity.enums.LanguageLevel;

import java.io.Serializable;

public class Subject extends OnlineTrainingEntity implements Serializable{
    private int id;
    private Language language;
    private LanguageLevel level;

    public Subject() {
    }

    public Subject(int id, Language language, LanguageLevel level) {
        this.id = id;
        this.language = language;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", language=" + language +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != subject.id) return false;
        if (language != subject.language) return false;
        return level == subject.level;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageLevel getLevel() {
        return level;
    }

    public void setLevel(LanguageLevel level) {
        this.level = level;
    }
}
