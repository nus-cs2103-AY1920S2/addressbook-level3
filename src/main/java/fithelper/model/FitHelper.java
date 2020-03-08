package fithelper.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import fithelper.model.entry.Entry;
import fithelper.model.entry.UniqueEntryList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class FitHelper implements ReadOnlyFitHelper {

    private final UniqueEntryList foodEntries;
    private final UniqueEntryList sportsEntries;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foodEntries = new UniqueEntryList();
        sportsEntries = new UniqueEntryList();
    }

    public FitHelper() {}

    /**
     * Creates an FitHelper using the Entries in the {@code toBeCopied}
     */
    public FitHelper(ReadOnlyFitHelper toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the entry list with {@code entries}.
     * {@code entries} must not contain duplicate entries.
     */
    public void setEntries(List<Entry> entries) {
        List<Entry> foodList = new ArrayList<>();
        List<Entry> sportsList = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.isFood()) {
                foodList.add(entry);
            } else {
                sportsList.add(entry);
            }
        }
        this.foodEntries.setEntries(foodList);
        this.sportsEntries.setEntries(sportsList);
    }

    public void setEntries(List<Entry> foods, List<Entry> sports) {
        this.foodEntries.setEntries(foods);
        this.sportsEntries.setEntries(sports);
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        if (target.isFood()) {
            foodEntries.setEntry(target, editedEntry);
        } else {
            sportsEntries.setEntry(target, editedEntry);
        }
    }

    /**
     * Resets the existing data of this {@code FitHelper} with {@code newData}.
     */
    public void resetData(ReadOnlyFitHelper newData) {
        requireNonNull(newData);
        setEntries(newData.getFoodList(), newData.getSportsList());
    }

    //// entry-level operations

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in FitHelper.
     */
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        if (entry.isFood()) {
            return foodEntries.contains(entry);
        } else {
            return sportsEntries.contains(entry);
        }
    }

    /**
     * Adds an entry to FitHelper.
     * The entry must not already exist in FitHelper.
     */
    public void addEntry(Entry entry) {
        if (entry.isFood()) {
            foodEntries.add(entry);
        } else {
            sportsEntries.add(entry);
        }
    }

    /**
     * Removes {@code key} from this {@code FitHelper}.
     * {@code key} must exist in FitHelper.
     */
    public void removeEntry(Entry key) {
        if (key.isFood()) {
            foodEntries.remove(key);
        } else {
            sportsEntries.remove(key);
        }
    }

    //// util methods

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Food: ")
                .append(foodEntries.asUnmodifiableObservableList().size())
                .append("\n")
                .append("Sports ")
                .append(sportsEntries.asUnmodifiableObservableList().size())
                .append("\n");
        return builder.toString();
    }

    /**
     * Returns an unmodifiable view of the food entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Entry> getFoodList() {
        return foodEntries.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Entry> getSportsList() {
        return sportsEntries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FitHelper // instanceof handles nulls
                && foodEntries.equals(((FitHelper) other).foodEntries)
                && sportsEntries.equals(((FitHelper) other).sportsEntries));
    }

    @Override
    public int hashCode() {
        List<UniqueEntryList> allList = new ArrayList<>();
        allList.add(foodEntries);
        allList.add(sportsEntries);
        return allList.hashCode();
    }
}
