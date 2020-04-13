package seedu.eylah.expensesplitter.model.receipt;

import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Represents an Entry class, which is to be added to into a Receipt class.
 * Guarantees: details are present and not nul.
 */
public class Entry {
    private Item item;
    private ArrayList<Person> persons;

    /**
     * Every field must be present.
     * Item and array of String must not be null.
     *
     * @param item,persons Represents the compulsory fields of an Entry object.
     */
    public Entry(Item item, ArrayList<Person> persons) {
        requireAllNonNull(item, persons);
        this.item = item;
        this.persons = persons;
    }

    /**
     * Returns the item in the Entry.
     *
     * @return the item.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns the ArrayList of Persons associated with the Item.
     *
     * @return the ArrayList of Persons associated with the Item.
     */
    public ArrayList<Person> getPersonsList() {
        return this.persons;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherItem = (Entry) other;

        return otherItem.getItem().equals(getItem())
                && otherItem.getPersonsList().equals(getPersonsList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(item, persons);
    }

    @Override
    public String toString() {
        final StringBuilder personsFormatted = new StringBuilder();
        for (int i = 0; i < persons.size(); i++) {
            if (i != persons.size() - 1) {
                personsFormatted.append(persons.get(i))
                        .append(", ");
            } else { //last person in list
                personsFormatted.append(persons.get(i));
            }
        }
        return "Item: " + item + " | " + personsFormatted.toString();
    }
}
