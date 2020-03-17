package seedu.zerotoone.model;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.person.Person;

/**
 * Unmodifiable view of an exercise list
 */
public interface ReadOnlyExerciseList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
