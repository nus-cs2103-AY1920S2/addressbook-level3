package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all reminder data at the Reminder TAble level
 * Duplicates are not allowed
 */
public class ReminderTAble implements ReadOnlyReminder {

    private final UniqueReminderList reminders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        reminders = new UniqueReminderList();
    }

    public ReminderTAble() {}

    /**
     * Creates a ReminderTAble using the reminders in the {@code toBeCopied}
     */
    public ReminderTAble(ReadOnlyReminder toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Creates a ReminderTAble using the reminders in the {@code reminderList}
     */
    public ReminderTAble(List<Reminder> reminderList) {
        this.reminders.setReminders(reminderList);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the reminder list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Resets the existing data of this {@code ReminderTAble} with {@code newData}.
     */
    public void resetData(ReadOnlyReminder newData) {
        requireNonNull(newData);

        setReminders(newData.getAllReminders());
    }

    //// Reminder-level operations

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in TAble.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a reminder to TAble.
     * The reminder must not already exist in TAble.
     */
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReminder}.
     * {@code target} must exist in the ReminderTAble.
     * The reminder identity of {@code editedReminder} must not be the same
     * as another existing reminder in TAble.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireNonNull(editedReminder);

        reminders.setReminder(target, editedReminder);
    }

    /**
     * Removes {@code key} from this {@code ReminderTAble}.
     * {@code key} must exist in the ReminderTAble.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    /**
     * Marks {@code key} from this {@code ReminderTAble} as done.
     * {@code key} must exist in the ReminderTAble.
     */
    public Reminder markReminderAsDone(Reminder key) {
        return reminders.markAsDone(key);
    }

    /**
     * Returns {@code reminder} from {@code ReminderTAble}.
     * {@code reminder} must exist in ReminderTAble.
     */
    public Reminder getReminder(int index) {
        return reminders.getReminder(index);
    }

    //// util methods

    @Override
    public String toString() {
        return reminders.asUnmodifiableObservableList().size() + " reminders";
        // TODO: refine later
    }

    @Override
    public ObservableList<Reminder> getAllReminders() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderTAble // instanceof handles nulls
                && reminders.equals(((ReminderTAble) other).reminders));
    }

    @Override
    public int hashCode() {
        return reminders.hashCode();
    }
}
