package fithelper.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Predicate;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.calorietable.CalorieDatum;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Entry;
import fithelper.model.entry.SortBy;
import fithelper.model.profile.Profile;
import fithelper.model.today.Today;
import fithelper.model.weight.Bmi;
import fithelper.model.weight.Date;
import fithelper.model.weight.Weight;
import fithelper.model.weight.WeightValue;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Diary> PREDICATE_SHOW_ALL_DIARIES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_NO_ENTRIES = unused -> false;
    Predicate<Weight> PREDICATE_SHOW_ALL_WEIGHTS = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_UNDONE_ENTRIES = entry -> entry.getStatus().value.equals("Undone");
    Predicate<Entry> PREDICATE_SHOW_TODAY_ENTRIES = entry ->
            entry.getTime().getDateStr().equals(new Today().getTodayDateStr());
    Predicate<Entry> someDatePredicate(String dateStr);

    /**
     * Returns true if the model has previous baking home states to restore.
     */
    boolean canUndo();

    /**
     * Returns true if the model has undone baking home states to restore.
     */
    boolean canRedo();

    /**
     * Restores fitHelper to its previous state.
     * @return the commit message of the current state.
     */
    String undo();

    /**
     * Restores fitHelper to its previously undone state.
     * @return the commit message of the previous state.
     */
    String redo();

    /**
     * Saves the current FitHelper state for undo/redo.
     *
     * @param commitMessage the message describing the details of the commit
     */
    void commit(String commitMessage);

    /**
     * Sets the status of version control.
     * If {@code isEnabled} is false, version control is disabled. As a result,
     * {@code commit()} will not save the current FitHelper state.
     */
    void setVersionControl(Boolean isEnabled);

    /**
     * Replaces FitHelper data with the data in {@code fitHelper}.
     */
    void setFitHelper(ReadOnlyFitHelper fitHelper);

    /** Returns the FitHelper */
    ReadOnlyFitHelper getFitHelper();

    /** clears the diary data of the FitHelper */
    void clearDiaryFitHelper();

    /**
     * Returns true if diary log with the same identity as {@code diary} exists in the FitHelper.
     */
    boolean hasDiary(Diary diary);

    /**
     * Returns true if diary log with the same date as {@code diary} exists in the FitHelper.
     */
    boolean hasDiaryDate(DiaryDate diaryDate);

    /**
     * Returns true if an Entry with the same identity as {@code entry} exists in the FitHelper.
     */
    boolean hasEntry(Entry entry);

    /**
     * Returns true if at least two Entries have time clashes as {@code entry} exists in the FitHelper.
     */
    boolean hasTimeClashes(Entry entry);
    boolean hasTimeClashes(Entry entry, Entry editedEntry);

    /**
     * Deletes the given diary.
     * The diary must exist in the log book.
     */
    void deleteDiary(Diary target);

    /**
     * Deletes the given entry.
     * The entry must exist in the log book.
     */
    void deleteEntry(Entry target);

    /**
     * Adds the given diary.
     * {@code diary} must not already exist in the log book.
     */
    void addDiary(Diary diary);

    /**
     * Adds the given Entry.
     * {@code entry} must not already exist in the log book.
     */
    void addEntry(Entry entry);

    void addVEvent(Entry entry) throws IllegalValueException;

    void setVevent(Entry entry, Entry newEntry);

    void deleteVevent(Entry entry);

    /**
     * Replaces the given diary {@code target} with {@code editedDiary}.
     * {@code target} must exist in the log book.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the log book.
     */
    void setDiary(Diary target, Diary editedDiary);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     */
    void setEntry(Entry target, Entry editedEntry);

    /** Returns an unmodifiable view of the filtered diary list */
    ObservableList<Diary> getFilteredDiaryList();

    /** Returns an unmodifiable view of the filtered food entry list */
    ObservableList<Entry> getFilteredFoodEntryList();

    /** Returns an unmodifiable view of the filtered sports entry list */
    ObservableList<Entry> getFilteredSportsEntryList();

    /** Returns an unmodifiable view of the filtered sports entry list */
    ObservableList<Entry> getFilteredReminderEntryList();

    /** Returns an unmodifiable view of the filtered food entry list */
    ObservableList<Entry> getFilteredTodayFoodEntryList();

    /** Returns an unmodifiable view of the filtered sports entry list */
    ObservableList<Entry> getFilteredTodaySportsEntryList();

    /**
     * Updates the filter of the filtered diary list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDiaryList(Predicate<Diary> predicate);

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<Entry> predicate);

    /**
     * Updates the filter of the filtered food entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodEntryList(Predicate<Entry> predicate);

    /**
     * Updates the filter of the filtered sports entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSportEntryList(Predicate<Entry> predicate);

    /**
     * Updates the filter of the filtered reminder entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderEntryList(Predicate<Entry> predicate);

    /**
     * Search the pre-defined FoodCalorie dataset for entries that match the given keyword.
     * @param keyword keyword for searching
     */
    Set<CalorieDatum> searchFoodCalorieTable(String keyword);

    /**
     * Search the pre-defined SportsCalorie dataset for entries that match the given keyword.
     * @param keyword keyword for searching
     */
    Set<CalorieDatum> searchSportsCalorieTable(String keyword);

    ObservableList<VEvent> getVEvents();

    void updateVevents();

    void setCalendarDate(String date);
    LocalDate getCalendarDate();

    void setCalendarMode(String mode);
    String getCalendarMode();

    void setCalendarShow(String show);

    LocalDate getCalendarShow();

    public void clearVevents();

    // Methods about user profile.

    /**
     * Replaces user profile data with the data in {@code newUserProfile}.
     */
    void setUserProfile(ReadOnlyUserProfile newUserProfile);

    void setUserProfile(Profile newUserProfile);

    /** Returns the User Profile */
    ReadOnlyUserProfile getUserProfile();

    /**
     * Returns true if new profile is the same as original one, without comparing weight data.
     */
    boolean isSameProfile(Profile newProfile);


    // Methods about weight records.

    /**
     * Replaces Weight Records data with the data in {@code weightRecords}.
     */
    void setWeightRecords(ReadOnlyWeightRecords weightRecords);

    /** Returns the WeightRecords */
    ReadOnlyWeightRecords getWeightRecords();

    /**
     * Returns true if weight record with the same date as {@code weight} exists in Weight Records.
     */
    boolean hasWeight(Weight weight);

    /**
     * Adds the given weight.
     * {@code weight} must not already exist in the log book.
     */
    void addWeight(Weight weight);

    void deleteWeight(Weight weight);

    void editWeight(Weight weight, WeightValue weightValue, Bmi bmi);

    Weight getWeightByDate(Date date);

    /** Returns an unmodifiable view of the filtered weight list */
    ObservableList<Weight> getFilteredWeightList();

    /**
     * Updates the filter of the filtered weight records list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWeightList(Predicate<Weight> predicate);

    LocalDate getLastWeightDate();

    void sortFilteredEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException;

    void sortFilteredFoodEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException;

    void sortFilteredSportsEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException;
}

