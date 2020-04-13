package fithelper.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.calorietable.CalorieDatum;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Entry;
import fithelper.model.entry.SortBy;
import fithelper.model.profile.Profile;
import fithelper.model.weight.Bmi;
import fithelper.model.weight.Date;
import fithelper.model.weight.Weight;
import fithelper.model.weight.WeightValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

/**
 * A default model stub that have all of the methods either failing or returning simple results.
 */
public class ModelStub implements Model {
    @Override
    public Predicate<Entry> someDatePredicate(String dateStr) {
        throw new AssertionError("This method should not be called.");
    }
    @Override
    public boolean canUndo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String undo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String redo() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearDiaryFitHelper() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDiary(Diary diary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDiaryDate(DiaryDate diaryDate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTimeClashes(Entry entry) {
        return false;
    }

    @Override
    public boolean hasTimeClashes(Entry entry, Entry editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commit(String commitMessage) {
        System.out.println("add command test");
    }

    @Override
    public void setVersionControl(Boolean isEnabled) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addEntry(Entry entry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFitHelper(ReadOnlyFitHelper newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEntry(Entry entry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteEntry(Entry target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addDiary(Diary diary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDiary(Diary target, Diary editedDiary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDiary(Diary target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Entry> getFilteredFoodEntryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Entry> getFilteredSportsEntryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Entry> getFilteredReminderEntryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredReminderEntryList(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Entry> getFilteredTodayFoodEntryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Entry> getFilteredTodaySportsEntryList() {
        throw new AssertionError("This method should not be called.");
    }

    public ObservableList<Entry> getTodayEntries(String todayDate, FilteredList<Entry> entries) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredDiaryList(Predicate<Diary> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredFoodEntryList(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredSportEntryList(Predicate<Entry> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    public Set<CalorieDatum> searchFoodCalorieTable(String keywords) {
        return new HashSet<>();
    }

    public Set<CalorieDatum> searchSportsCalorieTable(String keywords) {
        return new HashSet<>();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override
    public ObservableList<VEvent> getVEvents() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addVEvent(Entry entry) throws IllegalValueException {
        System.out.println("add command test");
    }

    @Override
    public void clearVevents() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setVevent(Entry target, Entry editedEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateVevents() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteVevent(Entry entry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCalendarDate(String date) {
        throw new AssertionError("This method should not be called.");
    }

    public LocalDate getCalendarDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCalendarMode(String mode) {
        throw new AssertionError("This method should not be called.");
    }

    public String getCalendarMode() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCalendarShow(String show) {
        throw new AssertionError("This method should not be called.");
    }

    public LocalDate getCalendarShow() {
        throw new AssertionError("This method should not be called.");
    }

    // Methods about user profile.

    @Override
    public void setUserProfile(ReadOnlyUserProfile newUserProfile) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserProfile(Profile newUserProfile) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserProfile getUserProfile() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isSameProfile(Profile newProfile) {
        throw new AssertionError("This method should not be called.");
    }


    // Methods about weight records.
    @Override
    public void setWeightRecords(ReadOnlyWeightRecords weightRecords) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyWeightRecords getWeightRecords() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteWeight(Weight weight) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Weight getWeightByDate(Date date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasWeight(Weight weight) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addWeight(Weight weight) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public void editWeight(Weight weight, WeightValue weightValue, Bmi bmi) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Weight> getFilteredWeightList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredWeightList(Predicate<Weight> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public LocalDate getLastWeightDate() {
        throw new AssertionError("This method should not be called.");
    }

    public void sortFilteredFoodEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        System.out.println("List sorted.");
    }

    public void sortFilteredSportsEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        System.out.println("List sorted.");
    }

    public void sortFilteredEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
        System.out.println("List sorted.");
    }
}
