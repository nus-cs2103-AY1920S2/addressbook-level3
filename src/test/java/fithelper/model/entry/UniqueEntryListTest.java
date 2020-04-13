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
    public void contains_entryInList_returnsTrue() {
        uniqueEntryList.add(FOOD);
        assertTrue(uniqueEntryList.contains(FOOD));
    }

    @Test
    public void contains_entryWithDifferentFieldsInList_returnsFalse() {
        uniqueEntryList.add(FOOD);
        Entry editedFood = new EntryBuilder(FOOD).withLocation(VALID_LOCATION_SPORTS).withType(VALID_TYPE_SPORTS)
                .build();
        assertFalse(uniqueEntryList.contains(editedFood));
    }

    @Test
    public void add_nullEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.add(null));
    }

    @Test
    public void add_duplicateEntry_throwsDuplicateEntryException() {
        uniqueEntryList.add(FOOD);
        assertThrows(DuplicateEntryException.class, () -> uniqueEntryList.add(FOOD));
    }

    @Test
    public void setEntry_nullTargetEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntry(null, FOOD));
    }

    @Test
    public void setEntry_nullEditedEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntry(FOOD, null));
    }

    @Test
    public void setEntry_targetEntryNotInList_throwsEntryNotFoundException() {
        assertThrows(EntryNotFoundException.class, () -> uniqueEntryList.setEntry(FOOD, FOOD));
    }

    @Test
    public void setEntry_editedEntryIsSameEntry_success() {
        uniqueEntryList.add(FOOD);
        uniqueEntryList.setEntry(FOOD, FOOD);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(FOOD);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntry_editedEntryHasNonUniqueIdentity_throwsDuplicateEntryException() {
        uniqueEntryList.add(SPORTS);
        uniqueEntryList.add(FOOD);
        assertThrows(DuplicateEntryException.class, () -> uniqueEntryList.setEntry(SPORTS, FOOD));
    }

    @Test
    public void remove_nullEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.remove(null));
    }

    @Test
    public void removeEntryDoesNotExistthrowsEntryNotFoundException() {
        assertThrows(EntryNotFoundException.class, () -> uniqueEntryList.remove(FOOD));
    }

    @Test
    public void remove_existingEntry_removesEntry() {
        uniqueEntryList.add(FOOD);
        uniqueEntryList.remove(FOOD);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntries_nullUniqueEntryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntries((UniqueEntryList) null));
    }

    @Test
    public void setEntries_uniqueEntryList_replacesOwnListWithProvidedUniqueEntryList() {
        uniqueEntryList.add(FOOD);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(SPORTS);
        uniqueEntryList.setEntries(expectedUniqueEntryList);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntryList.setEntries((List<Entry>) null));
    }

    @Test
    public void setEntry_list_replacesOwnListWithProvidedList() {
        uniqueEntryList.add(FOOD);
        List<Entry> entryList = Collections.singletonList(SPORTS);
        uniqueEntryList.setEntries(entryList);
        UniqueEntryList expectedUniqueEntryList = new UniqueEntryList();
        expectedUniqueEntryList.add(SPORTS);
        assertEquals(expectedUniqueEntryList, uniqueEntryList);
    }

    @Test
    public void setEntries_listWithDuplicateEntries_throwsDuplicateEntryException() {
        List<Entry> listWithDuplicateEntries = Arrays.asList(FOOD, FOOD);
        assertThrows(DuplicateEntryException.class, () -> uniqueEntryList.setEntries(listWithDuplicateEntries));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueEntryList.asUnmodifiableObservableList().remove(0));
    }
}
