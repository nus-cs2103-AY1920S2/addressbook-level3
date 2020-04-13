package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * Wraps all data at the schedule list level
 * Duplicates are not allowed (by .isSameSchedule comparison)
 */
public class ScheduleList {

    private final UniqueScheduleList schedules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        schedules = new UniqueScheduleList();
    }

    public ScheduleList() {}

    /**
     * Creates an ScheduleList using the Schedules in the {@code toBeCopied}
     */
    public ScheduleList(ScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the schedule list with {@code schedules}.
     * {@code schedules} must not contain duplicate schedules.
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules.setSchedules(schedules);
    }

    /**
     * Resets the existing data of this {@code ScheduleList} with {@code newData}.
     */
    public void resetData(ScheduleList newData) {
        requireNonNull(newData);

        setSchedules(newData.getScheduleList());
    }

    //// schedule-level operations

    /**
     * Returns true if a schedule with the same identity as {@code schedule} exists in the schedule list.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return schedules.contains(schedule);
    }

    /**
     * Adds a schedule to the schedule list.
     * The schedule must not already exist in the schedule list.
     */
    public void addSchedule(Schedule p) {
        schedules.add(p);
    }

    /**
     * Replaces the given schedule {@code target} in the list with {@code editedSchedule}.
     * {@code target} must exist in the schedule list.
     * The schedule identity of {@code editedSchedule} must not be the same as another existing
     * schedule in the schedule list.
     */
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireNonNull(editedSchedule);

        schedules.setSchedule(target, editedSchedule);
    }

    /**
     * Removes {@code key} from this {@code ScheduleList}.
     * {@code key} must exist in the schedule list.
     */
    public void removeSchedule(Schedule key) {
        schedules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return getScheduleList().size() + " schedules";
    }

    public List<Schedule> getScheduleList() {
        return schedules.asUnmodifiableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleList // instanceof handles nulls
                && schedules.equals(((ScheduleList) other).schedules));
    }

    @Override
    public int hashCode() {
        return schedules.hashCode();
    }
}
