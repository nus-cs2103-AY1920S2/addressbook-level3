package seedu.address.model.day;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of days that corresponds to the user's generated schedule based on uncompeted assignments.
 */
public class ScheduleList {
    private final ObservableList<Day> scheduleList = FXCollections.observableArrayList();

    public ScheduleList() {}

    public ObservableList<Day> getScheduleList() {
        return scheduleList;
    }

    /**
     * Replaces the day at the specified index in the scheduleList with the new day.
     */
    public void setDay(int index, Day toSet) {
        scheduleList.set(index, toSet);
    }

    /**
     * Sets the size of the scheduleList to the number of days queried by the user.
     */
    public void setScheduleList(int numDays) {
        int currSize = scheduleList.size();

        if (numDays > currSize) {
            for (int i = 0; i < (numDays - currSize); i++) {
                scheduleList.add(new Day());
            }
        } else {
            for (int j = 0; j < (currSize - numDays); j++) {
                scheduleList.remove(scheduleList.size() - 1);
            }
        }

        for (int k = 0; k < scheduleList.size(); k++) {
            scheduleList.get(k).resetDueAssignments();
            scheduleList.get(k).resetAllocatedAssignments();
        }
    }
}
