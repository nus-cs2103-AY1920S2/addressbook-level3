package fithelper.model;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.calendar.CalendarSettings;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Time;
import fithelper.model.entry.UniqueEntryList;
import fithelper.model.entry.VeventList;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

/**
 * Represents the in-memory model of the FitHelper data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FitHelper fitHelper;
    private final FilteredList<Entry> filteredFoodEntries;
    private final FilteredList<Entry> filteredSportsEntries;
    private final FilteredList<Entry> filteredReminderEntries;
    private final VeventList vEventList;
    private CalendarSettings calendarSettings;
    private final UserProfile userProfile;

    /**
     * Initializes a ModelManager with the given fitHelper and userPrefs.
     */
    public ModelManager(ReadOnlyFitHelper fitHelper, ReadOnlyUserProfile userProfile) {
        super();
        requireAllNonNull(fitHelper);

        logger.fine("Initializing with FitHelper: " + fitHelper);

        this.fitHelper = new FitHelper(fitHelper);
        filteredFoodEntries = new FilteredList<>(this.fitHelper.getFoodList());
        filteredSportsEntries = new FilteredList<>(this.fitHelper.getSportsList());
        filteredReminderEntries = new FilteredList<>(this.fitHelper.getReminderList());
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
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return fitHelper.hasEntry(entry);
    }

    /**
     * Deletes the given entry.
     * The entry must exist in the log book.
     * @param target
     */
    @Override
    public void deleteEntry(Entry target) {
        fitHelper.removeEntry(target);
    }

    @Override
    public void addEntry(Entry entry) {
        fitHelper.addEntry(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
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
        calendarSettings = new CalendarSettings(formattedDate);
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
    public ReadOnlyUserProfile getUserProfile() {
        return this.userProfile;
    }
}
