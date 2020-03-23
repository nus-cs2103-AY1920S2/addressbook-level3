package fithelper.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.calendar.CalendarSettings;
import fithelper.model.calorietable.CalorieEntry;
import fithelper.model.diary.Diary;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Time;
import fithelper.model.entry.UniqueEntryList;
import fithelper.model.entry.VeventList;
import fithelper.model.profile.Profile;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;
import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the FitHelper data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FitHelper fitHelper;
    private final FilteredList<Diary> filteredDiaries;
    private final FilteredList<Entry> filteredFoodEntries;
    private final FilteredList<Entry> filteredSportsEntries;
    private final FilteredList<Entry> filteredReminderEntries;
    private final FilteredList<Entry> filteredTodayFoodEntries;
    private final FilteredList<Entry> filteredTodaySportsEntries;
    private final VeventList vEventList;
    private CalendarSettings calendarSettings = new CalendarSettings(LocalDateTime.now());
    private final UserProfile userProfile;

    /**
     * Initializes a ModelManager with the given fitHelper and userPrefs.
     */
    public ModelManager(ReadOnlyFitHelper fitHelper, ReadOnlyUserProfile userProfile) {
        super();
        requireAllNonNull(fitHelper);

        logger.fine("Initializing with FitHelper: " + fitHelper);

        this.fitHelper = new FitHelper(fitHelper);
        filteredDiaries = new FilteredList<Diary>(this.fitHelper.getDiaryList());
        filteredFoodEntries = new FilteredList<>(this.fitHelper.getFoodList());
        filteredSportsEntries = new FilteredList<>(this.fitHelper.getSportsList());
        filteredReminderEntries = new FilteredList<>(this.fitHelper.getReminderList());
        filteredTodayFoodEntries = new FilteredList<>(this.fitHelper.getFoodList());
        filteredTodaySportsEntries = new FilteredList<>(this.fitHelper.getSportsList());
        vEventList = new VeventList(filteredFoodEntries, filteredSportsEntries);
        this.userProfile = new UserProfile(userProfile);
    }

    public ModelManager() {
        this(new FitHelper(), new UserProfile());
    }

    //=========== FitHelper ================================================================================

    @Override
    public void setFitHelper(ReadOnlyFitHelper fitHelper) {
        this.fitHelper.resetData(fitHelper);
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        return this.fitHelper;
    }

    //=========== Basic Functions ===========================================================================
    @Override
    public boolean hasDiary(Diary diary) {
        requireNonNull(diary);
        return fitHelper.hasDiary(diary);
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return fitHelper.hasEntry(entry);
    }

    @Override
    public boolean hasTimeClashes(Entry entry) {
        requireNonNull(entry);
        return fitHelper.hasTimeClashes(entry);
    }

    /**
     * Deletes the given diary.
     * The diary must exist in the log book.
     *
     * @param target
     */
    @Override
    public void deleteDiary(Diary target) {
        fitHelper.removeDiary(target);
    }

    /**
     * Deletes the given entry.
     * The entry must exist in the log book.
     *
     * @param target
     */
    @Override
    public void deleteEntry(Entry target) {
        fitHelper.removeEntry(target);
    }

    @Override
    public void addDiary(Diary diary) {
        fitHelper.addDiary(diary);
        updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);
    }

    @Override
    public void addEntry(Entry entry) {
        fitHelper.addEntry(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    /**
     * Replaces the given diary {@code target} with {@code editedDiary}.
     * {@code target} must exist in the log book.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the log book.
     *
     * @param target
     * @param editedDiary
     */
    @Override
    public void setDiary(String target, Diary editedDiary) {
        requireAllNonNull(target, editedDiary);
        fitHelper.setDiary(target, editedDiary);
    }

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     *
     * @param target
     * @param editedEntry
     */
    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);
        fitHelper.setEntry(target, editedEntry);
    }

    //=========== Filtered Entry List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the diary list of {@code Diary} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        return filteredDiaries;
    }

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredFoodEntryList() {
        return filteredFoodEntries;
    }

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredSportsEntryList() {
        return filteredSportsEntries;
    }

    /**
     * Returns an unmodifiable view of the reminder list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredReminderEntryList() {
        return filteredReminderEntries;
    }

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredTodayFoodEntryList(String dateStr) {
        return this.fitHelper.getTodayFoodList(dateStr);
    }

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredTodaySportsEntryList(String dateStr) {
        return this.fitHelper.getTodaySportsList(dateStr);
    }

    public ObservableList<Entry> getTodayEntries(String todayDate, FilteredList<Entry> entries) {
        UniqueEntryList todayEntries = new UniqueEntryList();
        for (Entry entry : entries) {
            if (entry.getTime().getDateStr().equalsIgnoreCase(todayDate)) {
                todayEntries.add(entry);
            }
        }
        return todayEntries.asUnmodifiableObservableList();
    }

    /**
     * Updates the filter of the filtered diary list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredDiaryList(Predicate<Diary> predicate) {
        requireNonNull(predicate);
        filteredDiaries.setPredicate(predicate);
    }

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredFoodEntries.setPredicate(predicate);
        filteredSportsEntries.setPredicate(predicate);
    }

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredFoodEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredFoodEntries.setPredicate(predicate);
    }

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredSportEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredSportsEntries.setPredicate(predicate);
    }

    /**
     * Searches the {@code FoodCalorieTable} and add all entries whose name contains the keyword into a list
     * add returns the list.
     *
     * @param keyword keyword for searching
     * @return a list of {@code CalorieEntry} with matching keyword
     */
    public List<CalorieEntry> searchFoodCalorieTable(String keyword) {
        return fitHelper.addCalorieEntries("f", keyword);
    }

    /**
     * Searches the {@code SportsCalorieTable} and add all entries whose name contains the keyword into a list
     * add returns the list.
     *
     * @param keyword keyword for searching
     * @return a list of {@code CalorieEntry} with matching keyword
     */
    public List<CalorieEntry> searchSportsCalorieTable(String keyword) {
        return fitHelper.addCalorieEntries("s", keyword);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return fitHelper.equals(other.fitHelper)
                && filteredDiaries.equals(other.filteredDiaries)
                && filteredFoodEntries.equals(other.filteredFoodEntries)
                && filteredSportsEntries.equals(other.filteredSportsEntries);
    }

    // Methods about calendar.

    @Override
    public ObservableList<VEvent> getVEvents() {
        return vEventList.getVEvents();
    }

    @Override
    public void addVEvent(Entry entry) throws IllegalValueException {
        vEventList.addVEvent(entry);
    }

    @Override
    public void setVevent(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);
        vEventList.setVEvent(target, editedEntry);
    }

    @Override
    public void deleteVevent(Entry entry) {
        vEventList.deleteVEvent(entry);
    }

    @Override
    public void setCalendarDate(String date) {
        Time time = new Time(date);
        LocalDateTime formattedDate = time.dateTime;
        calendarSettings.setDate(formattedDate);
    }

    public LocalDateTime getCalendarDate() {
        return calendarSettings.getDate();
    }

    // Methods about user profile.

    @Override
    public void setUserProfile(ReadOnlyUserProfile newUserProfile) {
        this.userProfile.resetData(newUserProfile);
    }

    @Override
    public void setUserProfile(Profile newUserProfile) {
        this.userProfile.setUserProfile(newUserProfile);
    }

    @Override
    public ReadOnlyUserProfile getUserProfile() {
        return this.userProfile;
    }

    @Override
    public boolean isSameProfile(Profile newProfile) {
        return this.userProfile.getUserProfile().equals(newProfile);
    }
}
