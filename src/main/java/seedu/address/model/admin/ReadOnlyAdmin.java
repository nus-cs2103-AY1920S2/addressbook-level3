package seedu.address.model.admin;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an admin list
 */
public interface ReadOnlyAdmin {

    /**
     * Returns an unmodifiable view of the admin list.
     * This list will not contain any duplicate dates.
     */
    ObservableList<Date> getDateList();

}
