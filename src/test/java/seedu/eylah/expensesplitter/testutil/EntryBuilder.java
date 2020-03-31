package seedu.eylah.expensesplitter.testutil;

import java.util.ArrayList;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Entry;


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

    public EntryBuilder withItem(Item item) {
        this.item = item;
        return this;
    }

    public EntryBuilder withPersons(ArrayList<Person> persons) {
        this.persons = persons;
        return this;
    }

    public Entry build() {
        return new Entry(item, persons);
    }
}
