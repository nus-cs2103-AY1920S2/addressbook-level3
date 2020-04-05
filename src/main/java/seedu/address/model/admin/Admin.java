package seedu.address.model.admin;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the TeaPet level
 * Duplicates are not allowed (by .isSameDate comparison).
 */
public class Admin implements ReadOnlyAdmin {

    private final UniqueDateList dates;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dates = new UniqueDateList();
    }

    public Admin() {}

    /**
     * Creates an admin list using the dates in the {@code toBeCopied}
     */
    public Admin(ReadOnlyAdmin toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the date list with {@code dates}.
     * {@code dates} must not contain duplicate dates.
     */
    public void setDates(List<Date> dates) {
        this.dates.setDates(dates);
    }

    /**
     * Resets the existing data of this {@code Admin} with {@code newData}.
     */
    public void resetData(ReadOnlyAdmin newData) {
        requireNonNull(newData);
        setDates(newData.getDateList());
    }

    //// admin-level operations

    /**
     * Returns true if a date with the same identity as {@code date} exists in the admin list.
     */
    public boolean hasDate(Date date) {
        requireNonNull(date);
        return dates.contains(date);
    }

    /**
     * Adds a date to the admin list.
     */
    public void addDate(Date p) {
        dates.add(p);
    }

    /**
     * Replaces the given date {@code target} in the list with {@code editedDate}.
     * {@code target} must exist in the admin list.
     * The date identity of {@code editedDate} must not be the same as another existing date in
     * the admin list.
     */
    public void setDate(Date target, Date editedDate) {
        requireNonNull(editedDate);
        dates.setDate(target, editedDate);
    }

    /**
     * Removes {@code key} from this {@code Admin}.
     * {@code key} must exist in the admin list.
     */
    public void removeDate(Date key) {
        dates.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return dates.asUnmodifiableObservableList().size() + " dates";
    }

    @Override
    public ObservableList<Date> getDateList() {
        return dates.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Admin // instanceof handles nulls
                && dates.equals(((Admin) other).dates));
    }

    @Override
    public int hashCode() {
        return dates.hashCode();
    }
}
