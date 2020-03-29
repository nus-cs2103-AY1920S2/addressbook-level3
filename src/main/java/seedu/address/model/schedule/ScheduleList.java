package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

/**
 *
 */
public class ScheduleList {

    private ArrayList<Schedule> scheduleList = new ArrayList<>();

    public ScheduleList() {
        this.scheduleList = new ArrayList<Schedule>();
    }

    /**
     * Replaces the contents of this list with {@code exercises}. {@code exercises} must
     * not contain duplicate exercises.
     */
    public void setSchedule(ArrayList<Schedule> newScheduleList) {
        requireAllNonNull(newScheduleList);
        scheduleList = newScheduleList;
    }

    public ScheduleList duplicate() {
        ScheduleList copiedList = new ScheduleList();
        copiedList.setSchedule();
        return new ScheduleList()
    }

    public void add(Schedule schedule) {
        scheduleList.add(schedule);
    }

    public ArrayList<Schedule> getArrayList() {
        return scheduleList;
    }

    @Override
    public int hashCode() {
        return scheduleList.hashCode();
    }

    @Override
    public String toString() {
        String toReturn = "";
        for (Schedule schedule : scheduleList) {
            toReturn += schedule.toString() + "\n";
        }
        return toReturn.trim();
    }
}
