//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.reminder.exceptions.DuplicateReminderException;
import seedu.jelphabot.model.reminder.exceptions.ReminderNotFoundException;

/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Reminder#isSameReminder(Reminder)}.
 * As such, adding and updating of tasks uses Task#isSameTask(Task)
 * for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueReminderList implements Iterable<Reminder> {

    private static final Logger logger = LogsCenter.getLogger(UniqueReminderList.class);

    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReminder);
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
    }

    public ObservableList<Reminder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
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

    public void setReminder(Reminder target, Reminder newReminder) {
        requireAllNonNull(target, newReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (!target.isSameReminder(target) && contains(newReminder)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, newReminder);
    }

    public void setReminders(UniqueReminderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setReminders(List<Reminder> reminders) {
        requireAllNonNull(reminders);
        if (!remindersAreUnique(reminders)) {
            throw new DuplicateReminderException();
        }

        internalList.setAll(reminders);
    }

    /**
     * Sets the new index after a reminder before has been deleted.
     */
    public void updateReminderIndexes(int index) {
        for (Reminder reminder : internalList) {
            if (reminder.getIndex().getOneBased() > index) {
                int currentIndex = reminder.getIndex().getOneBased();
                currentIndex -= 1;
                reminder.setIndex(currentIndex);
            }
        }
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

    /**
     * Returns true if {@code reminder} contains only unique tasks.
     */
    public boolean remindersAreUnique(List<Reminder> reminders) {
        HashSet<Reminder> seen = new HashSet<>();
        for (Reminder reminder : reminders) {
            if (seen.contains(reminder)) {
                return false;
            }
            seen.add(reminder);
        }
        return true;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}
