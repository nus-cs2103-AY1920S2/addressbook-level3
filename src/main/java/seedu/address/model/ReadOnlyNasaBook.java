package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;

/**
 * Unmodifiable view of an Nasa book
 */
public interface ReadOnlyNasaBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Activity> getActivityList();
}
