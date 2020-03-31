package seedu.eylah.expensesplitter.model.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.expensesplitter.testutil.TypicalEntries.ENTRY_ONE;
import static seedu.eylah.expensesplitter.testutil.TypicalItem.BEERTOWER;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class EntryTest {
    private final Entry entry = ENTRY_ONE;
    private final Item item = BEERTOWER;
    private final ArrayList<Person> persons = TypicalPersons.getTypicalPersonsArrayList();

    @Test
    public void constructor_nullItemNullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Entry(null, null));
    }

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Entry(null, persons));
    }

    @Test
    public void constructor_nullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Entry(item, null));
    }

    @Test
    public void isValidItem() {
        Entry entryTest = new Entry(item, persons);
        assertEquals(entry, entryTest);
    }

    @Test
    public void getValidItem() {
        Item itemTest = entry.getItem();
        assertEquals(item, itemTest);
    }

    @Test
    public void getValidPersonsList() {
        ArrayList<Person> personsTest = entry.getPersonsList();
        assertEquals(persons, personsTest);
    }
}
