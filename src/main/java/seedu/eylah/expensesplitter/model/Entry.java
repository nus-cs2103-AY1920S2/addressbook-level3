package seedu.eylah.expensesplitter.model;

import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Represents an Entry class, which is to be added to into a Receipt class.
 * Guarantees: details are present and not nul.
 */
public class Entry {
    private Item item;
    private String[] persons;
    private ArrayList<Person> personsList;

    /**
     * Every field must be present.
     * Item and array of String must not be null.
     *
     * @param item,persons Represents the compulsory fields of an Entry object.
     */
    public Entry(Item item, String ... persons) {
        requireAllNonNull(item, persons);
        this.item = item;
        this.persons = persons;
        this.personsList = createPersonsList(persons);
    }

    /**
     * Creates a Person object.
     *
     * @param name Name of the person.
     * @return a Person object initialised with the name;
     */
    private Person createPerson(String name) {
        return new Person(new Name(name), null);
    }

    /**
     * This method essentially converts a String array of person names
     * to an ArrayList of Person objects.
     *
     * @param persons String array of person names.
     * @return An ArrayList of Person objects associated with the Item.
     */
    private ArrayList<Person> createPersonsList(String ... persons) {
        ArrayList<Person> personsList = new ArrayList<>();
        for (String name : persons) {
            Person person = createPerson(name);
            personsList.add(person);
        }
        return personsList;
    }

    /**
     * Returns the item in the Entry.
     *
     * @return the item.
     */
    private Item getItem() {
        return this.item;
    }

    /**
     * Returns the ArrayList of Persons associated with the Item.
     *
     * @return the ArrayList of Persons associated with the Item.
     */
    private ArrayList<Person> getPersonsList() {
        return this.personsList;
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
        for (Person person : personsList) {
            personsFormatted.append(person);
        }
        return item + "   " + personsFormatted.toString();
    }
}
