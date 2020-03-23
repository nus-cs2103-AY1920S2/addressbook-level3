package fithelper.model;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.calorietable.CalorieEntry;
import fithelper.model.diary.Diary;
import fithelper.model.entry.Entry;
import fithelper.model.profile.Profile;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;



/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Diary> PREDICATE_SHOW_ALL_DIARIES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;
    Predicate<Entry> PREDICATE_SHOW_UNDONE_ENTRIES = entry -> entry.getStatus().value.equals("Undone");
    Predicate<Entry> PREDICATE_SHOW_DONE_ENTRIES = entry -> entry.getStatus().value.equals("Done");

    /**
     * Replaces FitHelper data with the data in {@code fitHelper}.
     */
    void setFitHelper(ReadOnlyFitHelper fitHelper);

    /** Returns the FitHelper */
    ReadOnlyFitHelper getFitHelper();

    /**
     * Returns true if diary log with the same identity as {@code diary} exists in the FitHelper.
     */
    boolean hasDiary(Diary diary);

    /**
     * Returns true if an Entry with the same identity as {@code entry} exists in the FitHelper.
     */
    boolean hasEntry(Entry entry);

    /**
     * Returns true if at least two Entries have time clashes as {@code entry} exists in the FitHelper.
     */
    boolean hasTimeClashes(Entry entry);

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
    void setDiary(String target, Diary editedDiary);

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
    ObservableList<Entry> getFilteredTodayFoodEntryList(String dateStr);

    /** Returns an unmodifiable view of the filtered sports entry list */
    ObservableList<Entry> getFilteredTodaySportsEntryList(String dateStr);

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
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodEntryList(Predicate<Entry> predicate);

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSportEntryList(Predicate<Entry> predicate);

    /**
     * Search the pre-defined FoodCalorie dataset for entries that match the given keyword.
     * @param keyword keyword for searching
     */
    List<CalorieEntry> searchFoodCalorieTable(String keyword);

    /**
     * Search the pre-defined SportsCalorie dataset for entries that match the given keyword.
     * @param keyword keyword for searching
     */
    List<CalorieEntry> searchSportsCalorieTable(String keyword);

    ObservableList<VEvent> getVEvents();

    void setCalendarDate(String date);
    LocalDateTime getCalendarDate();


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

}

