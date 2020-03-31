package seedu.eylah.expensesplitter.testutil;

import java.util.ArrayList;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;

/**
 * A utility class to help with building Entry objects.
 */
public class EntryBuilder {

    public static final Item DEFAULT_ITEM = TypicalItem.BEERTOWER;
    public static final ArrayList<Person> DEFAULT_PERSONS = TypicalPersons.getTypicalPersonsArrayList();

    private Item item;
    private ArrayList<Person> persons;

    public EntryBuilder() {
        item = DEFAULT_ITEM;
        persons = DEFAULT_PERSONS;
    }

    public EntryBuilder(Entry entryToCopy) {
        item = entryToCopy.getItem();
        persons = entryToCopy.getPersonsList();
    }

    /**
     * Sets the {@code item} of the {@code Entry} that we are building.
     */
    public EntryBuilder withItem(Item item) {
        this.item = item;
        return this;
    }

    /**
     * Sets the {@code persons} of the {@code Entry} that we are building.
     */
    public EntryBuilder withPersons(ArrayList<Person> persons) {
        this.persons = persons;
        return this;
    }

    public Entry build() {
        return new Entry(item, persons);
    }
}
