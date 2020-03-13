package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSameTask comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueTaskList tasklist;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasklist = new UniqueTaskList();
    }

    public AddressBook() {}

    /** Creates an AddressBook using the Tasks in the {@code toBeCopied} */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not
     * contain duplicate persons.
     */
    public void setTasks(List<Task> tasklist) {
        this.tasklist.setTasks(tasklist);
    }

    /** Resets the existing data of this {@code AddressBook} with {@code newData}. */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setTasks(newData.getTaskList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasklist.contains(task);
    }

    /** Adds a person to the address book. The person must not already exist in the address book. */
    public void addTask(Task t) {
        tasklist.add(t);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedTask}. {@code target}
     * must exist in the address book. The person identity of {@code editedTask} must not be the
     * same as another existing person in the address book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasklist.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address
     * book.
     */
    public void removeTask(Task key) {
        tasklist.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return tasklist.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasklist.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                        && tasklist.equals(((AddressBook) other).tasklist));
    }

    @Override
    public int hashCode() {
        return tasklist.hashCode();
    }
}
