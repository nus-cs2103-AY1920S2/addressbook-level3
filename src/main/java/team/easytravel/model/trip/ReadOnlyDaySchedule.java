package team.easytravel.model.trip;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a DaySchedule
 */
public interface ReadOnlyDaySchedule {
    /**
     * Returns an unmodifiable view of the day schedule list.
     * This list will not contain any duplicate schedule entry.
     */
    ObservableList<DayScheduleEntry> getDayScheduleList();
}
