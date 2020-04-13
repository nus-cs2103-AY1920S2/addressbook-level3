package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderIsDoneException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 * A reminder is considered unique by comparing using {@code Reminder#equals(Reminder)}. As such, adding, updating and
 * removal of reminders uses Reminder#equals(Reminder) for equality so as to ensure that the reminder being added,
 * updated or removed is unique in terms of identity in the UniqueReminderList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reminder#equals(Object)
 */
public class UniqueReminderList implements Iterable<Reminder> {

    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a reminder to the list.
     * The reminder must not already exist in the list.
     */
    public void add(Reminder toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(toAdd);
        FXCollections.sort(internalList);
    }

    /**
     * Replaces the reminder {@code target} in the list with {@code editedReminder}.
     * {@code target} must exist in the list.
     * The identity of {@code editedReminder} must not be the same as another existing reminder in the list.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (!target.equals(editedReminder) && contains(editedReminder)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, editedReminder);
        FXCollections.sort(internalList);
    }

    /**
     * Removes the equivalent reminder from the list.
     * The reminder must exist in the list.
     */
    public void remove(Reminder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReminderNotFoundException();
        }
    }

    /**
     * Marks the equivalent reminder from the list as done.
     * The reminder must exist in the list.
     */
    public Reminder markAsDone(Reminder toMark) {
        requireNonNull(toMark);

        int index = internalList.indexOf(toMark);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (toMark.getDone()) {
            throw new ReminderIsDoneException();
        }

        Reminder markedReminder = new Reminder(toMark.getDescription(), toMark.getDate(),
                toMark.getTime(), true);
        internalList.set(index, markedReminder);
        FXCollections.sort(internalList);
        return markedReminder;
    }

    public void setReminders(UniqueReminderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        requireAllNonNull(reminders);
        if (!remindersAreUnique(reminders)) {
            throw new DuplicateReminderException();
        }

        internalList.setAll(reminders);
    }

    /**
     * Returns the reminder at the {@code index}.
     * @param index Index of the reminder.
     * @return Reminder at the index.
     */
    public Reminder getReminder(int index) {
        return internalList.get(index);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reminder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReminderList // instanceof handles nulls
                && internalList.equals(((UniqueReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code reminders} contains only unique reminders.
     */
    private boolean remindersAreUnique(List<Reminder> reminders) {
        return CollectionUtil.isUnique(reminders);
    }

    public ObservableList<Reminder> getAllReminders() {
        return this.internalList;
    }
}
