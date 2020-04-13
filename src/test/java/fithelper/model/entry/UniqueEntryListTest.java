package fithelper.model.entry;

import static fithelper.logic.commands.CommandTestUtil.VALID_LOCATION_SPORTS;
import static fithelper.logic.commands.CommandTestUtil.VALID_TYPE_SPORTS;
import static fithelper.testutil.AssertUtil.assertThrows;
import static fithelper.testutil.TypicalEntriesUtil.FOOD;
import static fithelper.testutil.TypicalEntriesUtil.SPORTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import fithelper.model.entry.exceptions.DuplicateEntryException;
import fithelper.model.entry.exceptions.EntryNotFoundException;
import fithelper.testutil.EntryBuilder;

public class UniqueEntryListTest {

    private final UniqueEntryList uniqueEntryList = new UniqueEntryList();

    @Test
    public void contains_nullEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.contains(null));
    }

    @Test
    public void contains_entryNotInList_returnsFalse() {
        assertFalse(uniqueEntryList.contains(FOOD));
    }

    @Test
    public void containsentryInListreturnsTrue() {
        uniqueEntryList.add(FOOD);
        assertTrue(uniqueEntryList.contains(FOOD));
    }

    @Test
    public void containsentryWithDifferentFieldsInListreturnsFalse() {
        uniqueEntryList.add(FOOD);
        Entry editedFood = new EntryBuilder(FOOD).withLocation(VALID_LOCATION_SPORTS).withType(VALID_TYPE_SPORTS)
                .build();
        assertFalse(uniqueEntryList.contains(editedFood));
    }

    @Test
    public void addnullEntrythrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.add(null));
    }

    @Test
    public void addduplicateEntrythrowsDuplicateEntryException() {
        uniqueEntryList.add(FOOD);
        assertThrows(DuplicateEntryException.class, () -> uniqueEntryList.add(FOOD));
    }

    @Test
    public void setEntrynullTargetEntrythrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntry(null, FOOD));
    }

    @Test
    public void setEntrynullEditedEntrythrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntry(FOOD, null));
    }

    @Test
    public void setEntrytargetEntryNotInListthrowsEntryNotFoundException() {
        assertThrows(EntryNotFoundException.class, () -> uniqueEntryList.setEntry(FOOD, FOOD));
    }

    @Test
    public void setEntryeditedEntryIsSameEntrysuccess() {
        uniqueEntryList.add(FOOD);
        uniqueEntryList.setEntry(FOOD, FOOD);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(FOOD);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntryeditedEntryHasNonUniqueIdentitythrowsDuplicateEntryException() {
        uniqueEntryList.add(SPORTS);
        uniqueEntryList.add(FOOD);
        assertThrows(DuplicateEntryException.class, () -> uniqueEntryList.setEntry(SPORTS, FOOD));
    }

    @Test
    public void removenullEntrythrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.remove(null));
    }

    @Test
    public void removeEntryDoesNotExistthrowsEntryNotFoundException() {
        assertThrows(EntryNotFoundException.class, () -> uniqueEntryList.remove(FOOD));
    }

    @Test
    public void removeexistingEntryremovesEntry() {
        uniqueEntryList.add(FOOD);
        uniqueEntryList.remove(FOOD);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntriesnullUniqueEntryListthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntries((UniqueEntryList) null));
    }

    @Test
    public void setEntriesuniqueEntryListreplacesOwnListWithProvidedUniqueEntryList() {
        uniqueEntryList.add(FOOD);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(SPORTS);
        uniqueEntryList.setEntries(expectedUniqueEntryList);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntriesnullListthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntries((List<Entry>) null));
    }

    @Test
    public void setEntrylistreplacesOwnListWithProvidedList() {
        uniqueEntryList.add(FOOD);
        List<Entry> entryList = Collections.singletonList(SPORTS);
        uniqueEntryList.setEntries(entryList);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(SPORTS);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntrieslistWithDuplicateEntriesthrowsDuplicateEntryException() {
        List<Entry> listWithDuplicateEntries = Arrays.asList(FOOD, FOOD);
        assertThrows(DuplicateEntryException.class, () -> uniqueEntryList.setEntries(listWithDuplicateEntries));
    }

    @Test
    public void asUnmodifiableObservableListmodifyListthrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueEntryList.asUnmodifiableObservableList().remove(0));
    }
}
