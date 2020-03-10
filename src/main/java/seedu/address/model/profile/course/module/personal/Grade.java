package seedu.address.model.profile.course.module.personal;

import java.util.NoSuchElementException;

/**
 * Stores the grade of a profile's Module object.
 */
public class Grade {

    private ModuleGrade myGrade;

    public String getGrade() {
        return this.myGrade.toString();
    }

    public void setGrade(String grade) {
        for (ModuleGrade g: ModuleGrade.values()) {
            if (g.toString().equals(grade)) {
                this.myGrade = g;
                return;
            }
        }
        throw new NoSuchElementException("Grade " + grade + " does not exist");
    }
}
