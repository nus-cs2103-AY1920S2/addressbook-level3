package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.day.Day;
import seedu.address.model.day.ScheduleList;

/**
 * Wraps all data at the schedule level.
 */
public class Schedule implements ReadOnlySchedule {
    private final ScheduleList scheduleList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        scheduleList = new ScheduleList();
    }

    public Schedule() {
    }

    /**
     * Sets the schedule size to the number of days queried by the user.
     */
    public void createSchedule(int numDays) {
        scheduleList.setScheduleList(numDays);
    }

    /**
     * Replaces the day at the specified index in the scheduleList with the new day.
     */
    public void setDay(int index, Day toSet) {
        scheduleList.setDay(index, toSet);
    }

    /**
     * Returns the user's generated schedule for the next n days (including today) based on stored assignments,
     * their deadlines and expected work hours per assignment.
     */
    public ObservableList<Day> getScheduleList() {
        return scheduleList.getScheduleList();
    }
}
