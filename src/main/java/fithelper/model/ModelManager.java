package fithelper.model;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static fithelper.model.entry.Type.FOOD;
import static fithelper.model.entry.Type.SPORTS;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.commons.util.DateUtil;
import fithelper.commons.util.ModeUtil;
import fithelper.model.calendar.CalendarSettings;
import fithelper.model.calorietable.CalorieDatum;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Entry;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.UniqueEntryList;
import fithelper.model.entry.VeventList;
import fithelper.model.profile.Profile;
import fithelper.model.weight.Bmi;
import fithelper.model.weight.Date;
import fithelper.model.weight.Weight;
import fithelper.model.weight.WeightValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

/**
 * Represents the in-memory model of the FitHelper data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFitHelper fitHelper;
    private final FilteredList<Diary> filteredDiaries;
    private final FilteredList<Entry> filteredFoodEntries;
    private final FilteredList<Entry> filteredSportsEntries;
    private final FilteredList<Entry> filteredReminderEntries;
    private final FilteredList<Entry> filteredTodayFoodEntries;
    private final FilteredList<Entry> filteredTodaySportsEntries;
    private final VeventList vEventList;
    private CalendarSettings calendarSettings = new CalendarSettings(LocalDate.now(), "tb");
    private final UserProfile userProfile;
    private final WeightRecords weightRecords;
    private final FilteredList<Weight> filteredWeights;


    /**
     * Initializes a ModelManager with the given fitHelper, userProfile and weightRecords.
     */
    public ModelManager(ReadOnlyFitHelper fitHelper, ReadOnlyUserProfile userProfile,
                        ReadOnlyWeightRecords weightRecords) {
        super();
        requireAllNonNull(fitHelper, userProfile, weightRecords);

        logger.fine("Initializing with FitHelper: " + fitHelper);


        this.fitHelper = new VersionedFitHelper(fitHelper);
        filteredDiaries = new FilteredList<>(this.fitHelper.getDiaryList());
        filteredFoodEntries = new FilteredList<>(this.fitHelper.getFoodList());
        filteredSportsEntries = new FilteredList<>(this.fitHelper.getSportsList());
        filteredReminderEntries = new FilteredList<>(this.fitHelper.getReminderList());
        filteredTodayFoodEntries = new FilteredList<>(this.fitHelper.getTodayFoodList());
        filteredTodaySportsEntries = new FilteredList<>(this.fitHelper.getTodaySportsList());
        vEventList = this.fitHelper.getVeventList();

        logger.fine("Initializing with UserProfile: " + userProfile);
        this.userProfile = new UserProfile(userProfile);

        logger.fine("Initializing with Weight Records: " + weightRecords);
        this.weightRecords = new WeightRecords(weightRecords);
        filteredWeights = new FilteredList<>(this.weightRecords.getWeightList());
    }

    public ModelManager() {
        this(new FitHelper(), new UserProfile(), new WeightRecords());
    }

    @Override
    public boolean canUndo() {
        return fitHelper.canUndo();
    }

    @Override
    public boolean canRedo() {
        return fitHelper.canRedo();
    }

    @Override
    public String undo() {
        return fitHelper.undo();
    }

    @Override
    public String redo() {
        return fitHelper.redo();
    }

    @Override
    public void commit(String commitMessage) {
        fitHelper.commit(commitMessage);
    }

    @Override
    public void setVersionControl(Boolean isEnabled) {
        fitHelper.setVersionControl(isEnabled);
    }

    //=========== FitHelper ================================================================================

    @Override
    public void setFitHelper(ReadOnlyFitHelper fitHelper) {
        this.fitHelper.resetData(fitHelper);
    }

    @Override
    public void clearDiaryFitHelper() {
        this.fitHelper.clearDiary();
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        return this.fitHelper;
    }

    //=========== Basic Functions ===========================================================================

    /**
     * Creates a predicate in showing entries of a specific date.
     *
     * @param dateStr
     * @return
     */
    public Predicate<Entry> someDatePredicate(String dateStr) {
        Predicate<Entry> showSomedayEntriesPredicate = entry ->
                entry.getTime().getDateStr().equalsIgnoreCase(dateStr);
        return showSomedayEntriesPredicate;
    }

    @Override
    public boolean hasDiary(Diary diary) {
        requireNonNull(diary);
        return fitHelper.hasDiary(diary);
    }

    @Override
    public boolean hasDiaryDate(DiaryDate diaryDate) {
        requireNonNull(diaryDate);
        return fitHelper.hasDiaryDate(diaryDate);
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

    @Override
    public boolean hasTimeClashes(Entry entry, Entry editedEntry) {
        requireNonNull(entry);
        return fitHelper.hasTimeClashes(entry, editedEntry);
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
    public void setDiary(Diary target, Diary editedDiary) {
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
    public ObservableList<Entry> getFilteredTodayFoodEntryList() {
        return filteredTodayFoodEntries;
    }

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredTodaySportsEntryList() {
        return filteredTodaySportsEntries;
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
     * Updates the filter of the filtered reminder entry list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredReminderEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredReminderEntries.setPredicate(predicate);
    }

    /**
     * Searches the {@code FoodCalorieTable} and add at most 3 entries whose name matches the keyword into a set
     * add returns the set.
     *
     * @param keywords keyword for searching
     * @return a set of {@code CalorieDatum} with matching keyword
     */
    public Set<CalorieDatum> searchFoodCalorieTable(String keywords) {
        return fitHelper.addCalorieData(FOOD, keywords);
    }

    /**
     * Searches the {@code SportsCalorieTable} and add all entries whose name contains the keywords into a list
     * add returns the list.
     *
     * @param keywords keywords for searching
     * @return a list of {@code CalorieDatum} with matching keywords
     */
    public Set<CalorieDatum> searchSportsCalorieTable(String keywords) {
        return fitHelper.addCalorieData(SPORTS, keywords);
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
    public void updateVevents() {
        System.out.println(filteredFoodEntries);
        vEventList.refreshedList(filteredFoodEntries, filteredSportsEntries);

    }

    @Override
    public void setCalendarDate(String date) {
        LocalDate tmp = DateUtil.dateParsed(date);
        calendarSettings.setDate(tmp);
    }

    public LocalDate getCalendarDate() {
        return calendarSettings.getDate();
    }

    @Override
    public void setCalendarMode(String mode) {
        ModeUtil.checkMode(mode);
        calendarSettings.setMode(mode);
    }

    public String getCalendarMode() {
        return calendarSettings.getMode();
    }

    @Override
    public void setCalendarShow(String show) {
        if (show == null) {
            calendarSettings.setShow(null);
        } else {
            LocalDate date = DateUtil.dateParsed(show);
            calendarSettings.setShow(date);
        }
    }

    public LocalDate getCalendarShow() {
        return calendarSettings.getShow();
    }

    public void clearVevents() {
        vEventList.clearList();
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


    // Methods about weight records.
    @Override
    public void setWeightRecords(ReadOnlyWeightRecords weightRecords) {
        this.weightRecords.resetData(weightRecords);
    }

    @Override
    public ReadOnlyWeightRecords getWeightRecords() {
        return this.weightRecords;
    }

    @Override
    public boolean hasWeight(Weight weight) {
        requireNonNull(weight);
        return weightRecords.hasWeight(weight);
    }

    @Override
    public void addWeight(Weight weight) {
        weightRecords.addWeight(weight);
        updateFilteredWeightList(PREDICATE_SHOW_ALL_WEIGHTS);
    }

    @Override
    public void deleteWeight(Weight weight) {
        weightRecords.removeWeight(weight);
    }

    @Override
    public Weight getWeightByDate(Date date) {
        return weightRecords.getWeightByDate(date);
    }

    @Override
    public void editWeight(Weight weight, WeightValue weightValue, Bmi bmi) {
        weightRecords.editWeight(weight, weightValue, bmi);
    }

    /**
     * Returns an unmodifiable view of the weight list of {@code Weight} backed by the internal list of
     * {@code WeightRecords}
     */
    @Override
    public ObservableList<Weight> getFilteredWeightList() {
        return filteredWeights;
    }

    /**
     * Updates the filter of the filtered weight list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredWeightList(Predicate<Weight> predicate) {
        requireNonNull(predicate);
        filteredWeights.setPredicate(predicate);
    }

    @Override
    public LocalDate getLastWeightDate() {
        ObservableList<Weight> weights = this.weightRecords.getWeightList();
        if (weights.size() == 0) {
            return null;
        } else {
            LocalDate last = weights.get(0).getDate().value;
            for (int i = 1; i < weights.size(); i++) {
                if (weights.get(i).getDate().value.isAfter(last)) {
                    last = weights.get(i).getDate().value;
                }
            }
            return last;
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
        fitHelper.sortFilteredFoodEntryList(sortBy, isAscendingSort);
    }

    /**
     * Sorts sports entry list by a given criterion (calorie value or time) in either ascending or descending order.
     *
     * @param sortBy sort criterion
     * @param isAscendingSort boolean indicating whether sorting in ascending order (otherwise in descending order)
     * @throws IllegalValueException if given {@code sortBy} is invalid
     */
    public void sortFilteredSportsEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        fitHelper.sortFilteredSportsEntryList(sortBy, isAscendingSort);
    }

    /**
     * Sorts both food and sports entry list by a given criterion (calorie value or time)
     * in either ascending or descending order.
     *
     * @param sortBy sort criterion
     * @param isAscendingSort boolean indicating whether sorting in ascending order (otherwise in descending order)
     * @throws IllegalValueException if given {@code sortBy} is invalid
     */
    public void sortFilteredEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        fitHelper.sortFilteredFoodEntryList(sortBy, isAscendingSort);
        fitHelper.sortFilteredSportsEntryList(sortBy, isAscendingSort);
    }
}
