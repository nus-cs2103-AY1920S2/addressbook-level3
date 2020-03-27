package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@code AddressBook} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedAddressBook extends AddressBook implements Version<AddressBook> {
    private AddressBook currentState;
    int statePointer;
    List<AddressBook> stateList;

    public VersionedAddressBook() {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new AddressBook();
        commit();
    }

    /**
     * Creates a VersionedAddressBook using the {@code Person}s in the {@code toBeCopied}.
     */
    public VersionedAddressBook(ReadOnlyList<Person> toBeCopied) {
        statePointer = -1;
        stateList = new ArrayList<>();
        currentState = new AddressBook(toBeCopied);
        commit();
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the current state with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        currentState.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code VersionedAddressBook} with {@code newData}.
     * Resets the history to an empty state as well.
     */
    public void resetData(ReadOnlyList<Person> newData) {
        setPersons(newData.getReadOnlyList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the current state.
     */
    public boolean hasPerson(Person person) {
        return currentState.hasPerson(person);
    }

    /**
     * Adds a person to the current state.
     * The person must not already exist in the current state.
     */
    public void addPerson(Person p) {
        currentState.addPerson(p);
    }

    /**
     * Replaces the given person {@code target} in the current state with {@code editedPerson}.
     * {@code target} must exist in the current state.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the current state.
     */
    public void setPerson(Person target, Person editedPerson) {
        currentState.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from the current state.
     * {@code key} must exist in the current state.
     */
    public void removePerson(Person key) {
        currentState.removePerson(key);
    }

    //// versioning methods

    @Override
    public void commit() {
        AddressBook i = new AddressBook(getCurrentState());
        stateList = stateList.subList(0, statePointer + 1);
        stateList.add(i);
        statePointer++;
    }

    @Override
    public void undo() throws StateNotFoundException {
        if (statePointer == 0) {
            throw new StateNotFoundException();
        }

        statePointer--;
        currentState = new AddressBook(stateList.get(statePointer));
    }

    @Override
    public AddressBook getCurrentState() {
        return currentState;
    }

    //// util methods

    @Override
    public String toString() {
        return currentState.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getReadOnlyList() {
        return currentState.getReadOnlyList();
    }

    @Override
    protected UniquePersonList getPersons() {
        return currentState.getPersons();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedAddressBook // instanceof handles nulls
                && currentState.equals(((VersionedAddressBook) other).currentState))
                || (other instanceof AddressBook
                && currentState.equals((AddressBook) other));
    }

    @Override
    public int hashCode() {
        return currentState.hashCode();
    }
}
