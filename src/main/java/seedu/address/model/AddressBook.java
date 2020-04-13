package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueGroupList groups;
    private final UniqueEventList events;

    {
        persons = new UniquePersonList();
        groups = new UniqueGroupList();
        events = new UniqueEventList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons & Groups in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain
     * duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the group list with {@code groups}. {@code groups} must not contain
     * duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setGroups(newData.getGroupList());
        setEvents(newData.getEventList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same identity as {@code personList} exists in the address
     * book.
     */
    public boolean hasPersons(List<Person> personList) {
        requireNonNull(personList);
        return persons.containsPersons(personList);
    }

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    /**
     * Returns true if a groups with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroups(List<Group> groupList) {
        requireNonNull(groupList);
        return groups.containsGroups(groupList);
    }

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Returns true if a groups with the same identity as {@code group} exists in the address book.
     */
    public boolean hasEvents(List<Event> eventList) {
        requireNonNull(eventList);
        return events.containsEvents(eventList);
    }

    /**
     * Adds given group to the groups list.
     *
     * @param group given group
     */
    public void addGroup(Group group) {
        requireNonNull(group);
        groups.addGroup(group);
    }


    public void addGroups(List<Group> g) {
        groups.addAll(g);
    }


    /**
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    public void addPersons(List<Person> p) {
        persons.addAll(p);
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void addEvents(List<Event> e) {
        events.addAll(e);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target}
     * must exist in the address book. The person identity of {@code editedPerson} must not be the
     * same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);
        groups.setGroup(target, editedGroup);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public void removeGroup(Group key) {
        groups.removeGroup(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
