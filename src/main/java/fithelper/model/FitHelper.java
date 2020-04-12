package fithelper.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.calorietable.CalorieDatum;
import fithelper.model.calorietable.FoodCalorieTable;
import fithelper.model.calorietable.SportsCalorieTable;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;
import fithelper.model.diary.UniqueDiaryList;
import fithelper.model.entry.Entry;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.UniqueEntryList;
import fithelper.model.entry.VeventList;
import fithelper.model.today.Today;
import javafx.collections.ObservableList;

/**
 * Wraps all entry-related data at the FitHelper level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class FitHelper implements ReadOnlyFitHelper {

    private final UniqueDiaryList diaries = new UniqueDiaryList();
    private final UniqueEntryList foodEntries = new UniqueEntryList();
    private final UniqueEntryList sportsEntries = new UniqueEntryList();
    private final UniqueEntryList reminderEntries = new UniqueEntryList();
    private final UniqueEntryList todayFoodEntries = new UniqueEntryList();
    private final UniqueEntryList todaySportsEntries = new UniqueEntryList();
    private final FoodCalorieTable foodCalorieTable = new FoodCalorieTable();
    private final SportsCalorieTable sportsCalorieTable = new SportsCalorieTable();
    private final VeventList veventList = new VeventList();

    public FitHelper() {
    }

    /**
     * Creates an FitHelper using the Entries in the {@code toBeCopied}
     */
    public FitHelper(ReadOnlyFitHelper toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the diary list with {@code diaries}.
     * {@code diaries} must not contain duplicate diaries.
     */
    public void setDiaries(List<Diary> diaries) {
        List<Diary> diaryList = new ArrayList<>(diaries);
        this.diaries.setDiaries(diaryList);
    }

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
     * Replaces the given diary {@code target} in the list with {@code editedDiary}.
     * {@code target} must exist in the log book.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the log book.
     */
    public void setDiary(Diary target, Diary editedDiary) {
        requireNonNull(editedDiary);
        diaries.setDiary(target, editedDiary);
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
        if (!target.isDone()) {
            reminderEntries.remove(target);
        }
        if (!editedEntry.isDone()) {
            reminderEntries.add(editedEntry);
        }
    }

    /**
     * Resets the existing data of this {@code FitHelper} with {@code newData}.
     */
    public void resetData(ReadOnlyFitHelper newData) {
        requireNonNull(newData);
        setDiaries(newData.getDiaryList());
        setEntries(newData.getFoodList(), newData.getSportsList(), newData.getReminderList());
        setVevents(newData.getFoodList(), newData.getSportsList());
    }

    public void setVevents(ObservableList<Entry> foodList, ObservableList<Entry> sportsList) {
        veventList.refreshedList(foodList, sportsList);
    }

    /**
     * Clears the diary data of this {@code FitHelper}.
     */
    public void clearDiary() {
        setDiaries(new ArrayList<>());
    }

    //// diary-level operations

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in FitHelper.
     */
    public boolean hasDiary(Diary diary) {
        requireNonNull(diary);
        return diaries.contains(diary);
    }

    /**
     * Returns true if a entry with the same identity as {@code entry} exists in FitHelper.
     */
    public boolean hasDiaryDate(DiaryDate diaryDate) {
        requireNonNull(diaryDate);
        return diaries.containsDate(diaryDate.getValue());
    }

    /**
     * Adds a diary to FitHelper.
     * The diary must not already exist in FitHelper.
     */
    public void addDiary(Diary diary) {
        diaries.add(diary);
    }

    /**
     * Removes {@code key} from this {@code FitHelper}.
     * {@code key} must exist in FitHelper.
     */
    public void removeDiary(Diary key) {
        diaries.remove(key);
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
     * Returns true if a entry has time clashes with at least two other entries{@code entry}.
     */
    public boolean hasTimeClashes(Entry entry) {
        requireNonNull(entry);
        boolean hasTimeClashes;
        long hasTimeClashesWithFood = foodEntries.countClashes(entry);
        long hasTimeClashesWithSports = sportsEntries.countClashes(entry);
        long count = hasTimeClashesWithFood + hasTimeClashesWithSports;
        hasTimeClashes = count >= 1;
        return hasTimeClashes;
    }

    /**
     * Returns true if a entry has time clashes with at least one other entries{@code entry}.
     */
    public boolean hasTimeClashes(Entry entry, Entry editedEntry) {
        requireNonNull(entry);
        boolean hasTimeClashes;
        long hasTimeClashesWithFood = foodEntries.countClashes(editedEntry);
        long hasTimeClashesWithSports = sportsEntries.countClashes(editedEntry);
        long count = hasTimeClashesWithFood + hasTimeClashesWithSports;
        if (hasEntry(entry)) {
            count--;
        }
        hasTimeClashes = count >= 1;
        return hasTimeClashes;
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
                .append("\n")
                .append("User Diaries ")
                .append(diaries.asUnmodifiableObservableList().size())
                .append("\n");
        return builder.toString();
    }

    /**
     * Returns an unmodifiable view of the food entry list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public ObservableList<Diary> getDiaryList() {
        return diaries.asUnmodifiableObservableList();
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
     * Returns an unmodifiable view of the vevents list.
     * This list will not contain any duplicate entries.
     */
    @Override
    public VeventList getVeventList() {
        return veventList;
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
    public ObservableList<Entry> getTodayFoodList() {
        return todayFoodEntries.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    public ObservableList<Entry> getTodaySportsList() {
        return todaySportsEntries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FitHelper // instanceof handles nulls
                && diaries.equals(((FitHelper) other).diaries)
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

    /**
     * Searches one of the tables and add all entries whose name contains the keywords into a set
     * add returns the set.
     *
     * @param type type of entries to check (either f/food OR s/sports)
     * @param words keywords to match
     * @return a set of {@code CalorieDatum} with matching keywords.
     */
    public Set<CalorieDatum> addCalorieData(String type, String words) {
        assert "f".equals(type) || "s".equals(type) : "check type can only be f(food) or s(sports)";
        Set<CalorieDatum> result = new LinkedHashSet<>();
        Set<? extends CalorieDatum> data;
        String keywords = words.toLowerCase();
        String[] keywordsByWord = keywords.split(" ");
        if ("f".equals(type)) {
            data = foodCalorieTable.getData();
        } else {
            data = sportsCalorieTable.getData();
        }
        int count = 0;
        count = searchEachWordCompleteMatch(keywordsByWord, result, data, count);
        if (count < 3) {
            count = searchWholeWordPartialMatch(keywords, result, data, count);
        }
        if (count < 3 && keywordsByWord.length > 1) {
            searchEachWordPartialMatch(keywordsByWord, result, data, count);
        }
        return result;
    }

    /**
     * Checks if any {@code CalorieDatum} in the entry set whose name is the same of one of the keywordsByWord.
     * If so, add the entry in a set. Stop when all entries all checked or the set already contains 3 entries.
     *
     * @param keywordsByWord array of keywordsByWord
     * @param result set to contain matching entries
     * @param entries entry set
     * @param count number of entries in the set so far
     * @return number of {@code CalorieDatum} added at the end of the method.
     */
    private int searchEachWordCompleteMatch(String[] keywordsByWord, Set<CalorieDatum> result,
                                            Set<? extends CalorieDatum> entries, int count) {
        int currentCount = count;
        for (CalorieDatum entry : entries) {
            String name = entry.getName();
            if (currentCount == 3) {
                break;
            }
            for (String keyword : keywordsByWord) {
                if (name.equalsIgnoreCase(keyword)) {
                    result.add(entry);
                    currentCount++;
                    break;
                }
            }
        }
        return currentCount;
    }

    /**
     * Checks if any {@code CalorieDatum} in the entry set whose name contains the keywords as a whole.
     * If so, add the entry in a set. Stop when all entries all checked or the set already contains 3 entries.
     *
     * @param keywords keywords as a whole
     * @param result set to contain matching entries
     * @param entries entry set
     * @param count number of entries in the set so far
     * @return number of {@code CalorieDatum} added at the end of the method.
     */
    private int searchWholeWordPartialMatch(String keywords, Set<CalorieDatum> result,
                                            Set<? extends CalorieDatum> entries, int count) {
        int currentCount = count;
        for (CalorieDatum entry : entries) {
            String name = entry.getName();
            if (currentCount == 3) {
                break;
            }
            if (name.toLowerCase().contains(keywords)) {
                result.add(entry);
                currentCount++;
            }
        }
        return currentCount;
    }

    /**
     * Checks if any {@code CalorieDatum} in the entry set whose name contains one of the keywordsByWord.
     * If so, add the entry in a set. Stop when all entries all checked or the set already contains 3 entries.
     *
     * @param keywordsByWord array of keywordsByWord
     * @param result set to contain matching entries
     * @param entries entry set
     * @param count number of entries in the set so far
     */
    private void searchEachWordPartialMatch(String[] keywordsByWord, Set<CalorieDatum> result,
                                            Set<? extends CalorieDatum> entries, int count) {
        int currentCount = count;
        for (CalorieDatum entry : entries) {
            String name = entry.getName();
            if (currentCount == 3) {
                break;
            }
            for (String keyword : keywordsByWord) {
                if (name.toLowerCase().contains(keyword)) {
                    result.add(entry);
                    currentCount++;
                    break;
                }
            }
        }
    }

    /**
     * Sorts food entry list by a given criterion (calorie value or time) in either ascending or descending order.
     *
     * @param sortBy sort criterion
     * @param isAscendingSort boolean indicating whether sorting in ascending order (otherwise in descending order)
     * @throws IllegalValueException if given {@code sortBy} is invalid
     */
    public void sortFilteredFoodEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        if (isAscendingSort) {
            foodEntries.sortAscending(sortBy);
            reminderEntries.sortAscending(sortBy);
        } else {
            foodEntries.sortDescending(sortBy);
            reminderEntries.sortDescending(sortBy);
        }
    }

    /**
     * Sorts sports entry list by a given criterion (calorie value or time) in either ascending or descending order.
     *
     * @param sortBy sort criterion
     * @param isAscendingSort boolean indicating whether sorting in ascending order (otherwise in descending order)
     * @throws IllegalValueException if given {@code sortBy} is invalid
     */
    public void sortFilteredSportsEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        if (isAscendingSort) {
            sportsEntries.sortAscending(sortBy);
            reminderEntries.sortAscending(sortBy);
        } else {
            sportsEntries.sortDescending(sortBy);
            reminderEntries.sortDescending(sortBy);
        }
    }
}
