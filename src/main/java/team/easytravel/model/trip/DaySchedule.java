package team.easytravel.model.trip;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import team.easytravel.model.util.uniquelist.UniqueList;

/**
 * Represents a DaySchedule in the Trip. DaySchedule contains list of schedule entries.
 * Duplicates are not allowed (by .isSame comparison)
 */
public class DaySchedule implements ReadOnlyDaySchedule {
    private final UniqueList<DayScheduleEntry> scheduleEntries;

    /**
     * Instantiates a new DaySchedule.
     */
    public DaySchedule() {
        scheduleEntries = new UniqueList<>();
    }

    /**
     * Creates an DaySchedule using the schedule entries in the {@code} toBeCopied}
     */
    public DaySchedule(ReadOnlyDaySchedule toBeCopied) {
        scheduleEntries = new UniqueList<>();
        resetData(toBeCopied);
    }

    // List overwrite operations

    /**
     * Replaces the contents of the day schedule with {@code scheduleEntries}.
     * {@code scheduleEntries} must not contain duplicate schedule entries.
     */
    public void setScheduleEntries(List<DayScheduleEntry> scheduleEntries) {
        this.scheduleEntries.setElements(scheduleEntries);
    }

    /**
     * Resets the existing data of this {@code DaySchedule} with {@code newData}
     */
    public void resetData(ReadOnlyDaySchedule newData) {
        requireNonNull(newData);
        setScheduleEntries(newData.getDayScheduleList());
    }

    // DayScheduleEntry-level operations

    /**
     * Returns true if a schedule entry with the same identity as {@code scheduleEntry} exists in the DaySchedule.
     */
    public boolean hasScheduleEntry(DayScheduleEntry scheduleEntry) {
        requireNonNull(scheduleEntry);
        return scheduleEntries.contains(scheduleEntry);
    }

    /**
     * Adds a schedule entry to the DaySchedule.
     * The schedule must not already exist in the DaySchedule.
     */
    public void addScheduleEntry(DayScheduleEntry scheduleEntry) {
        scheduleEntries.add(scheduleEntry);
        scheduleEntries.sort((x, y) -> x.getStartDateTime().compareTo(y.getStartDateTime()));
    }

    /**
     * Replaces the given schedule entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the DaySchedule.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the
     * DaySchedule.
     */
    public void setScheduleEntry(DayScheduleEntry target, DayScheduleEntry editedEntry) {
        requireNonNull(editedEntry);

        scheduleEntries.setElement(target, editedEntry);
    }

    /**
     * Removes {@code key} from this {@code DaySchedule}.
     * {@code key} must exist in the DaySchedule.
     */
    public void removeScheduleEntry(DayScheduleEntry key) {
        scheduleEntries.remove(key);
    }

    // Util methods

    @Override
    public String toString() {
        return scheduleEntries.asUnmodifiableObservableList().size() + "  schedule entries";
        // TODO: refine later
    }

    @Override
    public ObservableList<DayScheduleEntry> getDayScheduleList() {
        return scheduleEntries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DaySchedule // instanceof handles nulls
                && scheduleEntries.equals(((DaySchedule) other).scheduleEntries));
    }

    @Override
    public int hashCode() {
        return scheduleEntries.hashCode();
    }
}
