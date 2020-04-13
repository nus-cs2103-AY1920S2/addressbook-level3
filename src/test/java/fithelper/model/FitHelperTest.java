package fithelper.model;

import static fithelper.testutil.AssertUtil.assertThrows;
import static fithelper.testutil.TypicalEntriesUtil.FOOD;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import fithelper.model.diary.Diary;
import fithelper.model.diary.UniqueDiaryList;
import fithelper.model.entry.Entry;
import fithelper.model.entry.UniqueEntryList;
import fithelper.model.entry.VeventList;

import javafx.collections.ObservableList;

public class FitHelperTest {

    private final FitHelper fitHelper = new FitHelper();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), fitHelper.getFoodList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fitHelper.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyFitHelper_replacesData() {
        FitHelper newData = getTypicalFitHelper();
        fitHelper.resetData(newData);
        assertEquals(newData, fitHelper);
    }

    @Test
    public void hasEntrynullEntrythrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> fitHelper.hasEntry(null));
    }

    @Test
    public void hasEntry_entryNotInFitHelper_returnsFalse() {
        assertFalse(fitHelper.hasEntry(FOOD));
    }

    @Test
    public void hasEntry_entryInFitHelper_returnsTrue() {
        fitHelper.addEntry(FOOD);
        assertTrue(fitHelper.hasEntry(FOOD));
    }

    @Test
    public void getEntryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> fitHelper.getFoodList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose entries list can violate interface constraints.
     */
    private static class FitHelperStub implements ReadOnlyFitHelper {
        private final UniqueEntryList foodEntries = new UniqueEntryList();
        private final UniqueEntryList sportsEntries = new UniqueEntryList();
        private final UniqueEntryList reminderEntries = new UniqueEntryList();
        private final UniqueDiaryList diaries = new UniqueDiaryList();
        private final VeventList veventlist = new VeventList();

        FitHelperStub(List<Entry> entries) {
            this.setEntries(entries);
        }

        @Override
        public ObservableList<Entry> getFoodList() {
            return foodEntries.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Entry> getSportsList() {
            return sportsEntries.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Entry> getReminderList() {
            return reminderEntries.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Diary> getDiaryList() {
            return diaries.asUnmodifiableObservableList();
        }

        @Override
        public VeventList getVeventList() {
            return veventlist;
        }

        /**
         * Replaces the contents of the entry list with {@code entries}.
         * {@code entries} must not contain duplicate entries.
         */
        public void setEntries(List<Entry> entries) {
            List<Entry> foodList = new ArrayList<>();
            List<Entry> sportsList = new ArrayList<>();
            List<Entry> reminderList = new ArrayList<>();
            for (Entry entry : entries) {
                if (entry.isFood()) {
                    foodList.add(entry);
                } else {
                    sportsList.add(entry);
                }
                if (entry.getStatus().value.equalsIgnoreCase("Undone")) {
                    reminderList.add(entry);
                }
            }
            this.foodEntries.setEntries(foodList);
            this.sportsEntries.setEntries(sportsList);
            this.reminderEntries.setEntries(reminderList);
        }
    }
}
