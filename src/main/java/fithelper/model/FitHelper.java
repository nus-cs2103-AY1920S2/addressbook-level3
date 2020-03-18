package fithelper.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import fithelper.model.entry.Entry;
import fithelper.model.entry.UniqueEntryList;
import fithelper.model.today.Today;
import javafx.collections.ObservableList;

/**
 * Wraps all entry-related data at the FitHelper level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class FitHelper implements ReadOnlyFitHelper {

    private final UniqueEntryList foodEntries = new UniqueEntryList();
    private final UniqueEntryList sportsEntries = new UniqueEntryList();
    private final UniqueEntryList reminderEntries = new UniqueEntryList();
    private final UniqueEntryList todayFoodEntries = new UniqueEntryList();
    private final UniqueEntryList todaySportsEntries = new UniqueEntryList();

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
        String todayStr = new Today().getTodayDateStr();
        List<Entry> foodList = new ArrayList<>();
        List<Entry> sportsList = new ArrayList<>();
        List<Entry> reminderList = new ArrayList<>();
        List<Entry> todayFoodList = new ArrayList<>();
        List<Entry> todaySportsList = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.isFood()) {
                foodList.add(entry);
                if (entry.getTime().getDateStr().equalsIgnoreCase(todayStr)) {
                    todayFoodList.add(entry);
                }
            } else {
                sportsList.add(entry);
                if (entry.getTime().getDateStr().equalsIgnoreCase(todayStr)) {
                    todaySportsList.add(entry);
                }
            }
            if (entry.getStatus().value.equalsIgnoreCase("Undone")) {
                reminderList.add(entry);
            }
        }
        this.foodEntries.setEntries(foodList);
        this.sportsEntries.setEntries(sportsList);
        this.reminderEntries.setEntries(reminderList);
        this.todayFoodEntries.setEntries(todayFoodList);
        this.todaySportsEntries.setEntries(todaySportsList);
    }

    public void setEntries(List<Entry> foods, List<Entry> sports, List<Entry> reminders) {
        String todayStr = new Today().getTodayDateStr();
        List<Entry> todayFoodList = new ArrayList<>();
        List<Entry> todaySportsList = new ArrayList<>();
        for (Entry entry : foods) {
            if (entry.getTime().getDateStr().equalsIgnoreCase(todayStr)) {
                todayFoodList.add(entry);
            }
        }
        for (Entry entry : sports) {
            if (entry.getTime().getDateStr().equalsIgnoreCase(todayStr)) {
                todaySportsList.add(entry);
            }
        }
        this.foodEntries.setEntries(foods);
        this.sportsEntries.setEntries(sports);
        this.reminderEntries.setEntries(reminders);
        this.todayFoodEntries.setEntries(todayFoodList);
        this.todaySportsEntries.setEntries(todaySportsList);
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireNonNull(editedEntry);
        String todayStr = new Today().getTodayDateStr();
        if (target.isFood()) {
            foodEntries.setEntry(target, editedEntry);
            if (target.isToday(todayStr)) {
                todayFoodEntries.remove(target);
            }
            if (editedEntry.isToday(todayStr)) {
                todayFoodEntries.add(editedEntry);
            }
        } else {
            sportsEntries.setEntry(target, editedEntry);
            if (target.isToday(todayStr)) {
                todaySportsEntries.remove(target);
            }
            if (editedEntry.isToday(todayStr)) {
                todaySportsEntries.add(editedEntry);
            }
        }
        reminderEntries.remove(target);
        if (!editedEntry.isDone()) {
            reminderEntries.add(editedEntry);
        }
    }

    /**
     * Resets the existing data of this {@code FitHelper} with {@code newData}.
     */
    public void resetData(ReadOnlyFitHelper newData) {
        requireNonNull(newData);
        setEntries(newData.getFoodList(), newData.getSportsList(), newData.getReminderList());
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
        String todayStr = new Today().getTodayDateStr();
        if (entry.isFood()) {
            foodEntries.add(entry);
            if (entry.isToday(todayStr)) {
                todayFoodEntries.add(entry);
            }
        } else {
            sportsEntries.add(entry);
            if (entry.isToday(todayStr)) {
                todaySportsEntries.add(entry);
            }
        }
        if (!entry.isDone()) {
            reminderEntries.add(entry);
        }
    }

    /**
     * Removes {@code key} from this {@code FitHelper}.
     * {@code key} must exist in FitHelper.
     */
    public void removeEntry(Entry key) {
        String todayStr = new Today().getTodayDateStr();
        if (key.isFood()) {
            foodEntries.remove(key);
            if (key.isToday(todayStr)) {
                todayFoodEntries.remove(key);
            }
        } else {
            sportsEntries.remove(key);
            if (key.isToday(todayStr)) {
                todaySportsEntries.remove(key);
            }
        }
        if (!key.isDone()) {
            reminderEntries.remove(key);
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
                .append("\n")
                .append("Reminders ")
                .append(reminderEntries.asUnmodifiableObservableList().size())
                .append("\n")
                .append("Today Food: ")
                .append(todayFoodEntries.asUnmodifiableObservableList().size())
                .append("\n")
                .append("Today Sports ")
                .append(todaySportsEntries.asUnmodifiableObservableList().size())
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

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Entry> getReminderList() {
        return reminderEntries.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the food entry list.
     * This list will not contain any duplicate entries.
     */
    public ObservableList<Entry> getTodayFoodList(String dateStr) {
        return todayFoodEntries.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    public ObservableList<Entry> getTodaySportsList(String dateStr) {
        return todaySportsEntries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FitHelper // instanceof handles nulls
                && foodEntries.equals(((FitHelper) other).foodEntries)
                && sportsEntries.equals(((FitHelper) other).sportsEntries)
                && reminderEntries.equals(((FitHelper) other).reminderEntries)
                && todayFoodEntries.equals(((FitHelper) other).todayFoodEntries)
                && todaySportsEntries.equals(((FitHelper) other).todaySportsEntries));
    }

    @Override
    public int hashCode() {
        List<UniqueEntryList> allList = new ArrayList<>();
        allList.add(foodEntries);
        allList.add(sportsEntries);
        allList.add(reminderEntries);
        allList.add(todayFoodEntries);
        allList.add(todaySportsEntries);
        return allList.hashCode();
    }
}
