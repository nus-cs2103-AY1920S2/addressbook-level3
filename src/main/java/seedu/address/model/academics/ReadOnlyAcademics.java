package seedu.address.model.academics;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAcademics {

    /**
     * Returns an unmodifiable view of the assessment list.
     * This list will not contain any duplicate assessment.
     */
    ObservableList<Assessment> getAcademicsList();

    /**
     * Returns a list of homework assessments.
     * This list will not contain any duplicate assessment.
     */
    ObservableList<Assessment> getHomeworkList();

    /**
     * Returns a list of exam assessments.
     * This list will not contain any duplicate assessment.
     */
    ObservableList<Assessment> getExamList();

}
