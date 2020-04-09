package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.day.Day;

/**
 * Unmodifiable view of the calculated schedule.
 */
public interface ReadOnlySchedule {
    /**
     * Returns an unmodifiable view of the calculated schedule.
     */
    ObservableList<Day> getScheduleList();
}
