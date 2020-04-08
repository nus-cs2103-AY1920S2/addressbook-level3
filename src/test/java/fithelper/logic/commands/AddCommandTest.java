package fithelper.logic.commands;

import static fithelper.testutil.AssertUtil.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.model.FitHelper;
import fithelper.model.Model;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.ReadOnlyWeightRecords;
import fithelper.model.calorietable.CalorieDatum;
import fithelper.model.diary.Diary;
import fithelper.model.diary.DiaryDate;
import fithelper.model.entry.Entry;
import fithelper.model.entry.SortBy;
import fithelper.model.profile.Profile;
import fithelper.model.weight.Weight;

import fithelper.testutil.EntryBuilder;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

public class AddCommandTest {

    @Test
    public void constructornullPersonthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void executeentryAcceptedByModeladdSuccessful() throws Exception {
        ModelStubAcceptingEntryAdded modelStub = new ModelStubAcceptingEntryAdded();
        Entry validEntry = new EntryBuilder().build();

        CommandResult commandResult = new AddCommand(validEntry).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEntry), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEntry), modelStub.entriesAdded);
    }

    @Test
    public void executeduplicateEntrythrowsCommandException() {
        Entry validEntry = new EntryBuilder().build();
        AddCommand addCommand = new AddCommand(validEntry);
        ModelStub modelStub = new ModelStubWithEntry(validEntry);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ENTRY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Entry food = new EntryBuilder().withName("Chicken Rice").build();
        Entry sports = new EntryBuilder().withName("Rock climbing").build();
        AddCommand addFoodCommand = new AddCommand(food);
        AddCommand addSportsCommand = new AddCommand(sports);

        // same object -> returns true
        assertTrue(addFoodCommand.equals(addFoodCommand));

        // same values -> returns true
        AddCommand addFoodCommandCopy = new AddCommand(food);
        assertTrue(addFoodCommand.equals(addFoodCommandCopy));

        // different types -> returns false
        assertFalse(addFoodCommand.equals(1));

        // null -> returns false
        assertFalse(addFoodCommand.equals(null));

        // different person -> returns false
        assertFalse(addFoodCommand.equals(addSportsCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
            throw new AssertionError("This method should not be called.");
        }

        public Set<CalorieDatum> searchSportsCalorieTable(String keywords) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean equals(Object obj) {
            throw new AssertionError("This method should not be called.");
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
        public boolean hasWeight(Weight weight) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWeight(Weight weight) {
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
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredSportsEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredEntryList(SortBy sortBy, boolean isAscendingSort) throws IllegalValueException {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single entry.
     */
    private class ModelStubWithEntry extends ModelStub {
        private final Entry entry;

        ModelStubWithEntry(Entry entry) {
            requireNonNull(entry);
            this.entry = entry;
        }

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return this.entry.isSameEntry(entry);
        }
    }

    /**
     * A Model stub that always accept the entry being added.
     */
    private class ModelStubAcceptingEntryAdded extends ModelStub {
        final ArrayList<Entry> entriesAdded = new ArrayList<Entry>();

        @Override
        public boolean hasEntry(Entry entry) {
            requireNonNull(entry);
            return entriesAdded.stream().anyMatch(entry::isSameEntry);
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            entriesAdded.add(entry);
        }

        @Override
        public ReadOnlyFitHelper getFitHelper() {
            return new FitHelper();
        }

    }

}
