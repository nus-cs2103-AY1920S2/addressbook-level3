package seedu.address.model.modelGeneric;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.modelObjectTags.ID;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AddressBookGeneric<K extends ModelObject> implements ReadOnlyAddressBookGeneric<K> {

    protected final UniqueList<K> objects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        objects = new UniqueList<K>();
    }

    public AddressBookGeneric() {
    }

    /**
     * Creates a generic AddressBook using the objects in the {@code toBeCopied}
     */
    public AddressBookGeneric(ReadOnlyAddressBookGeneric<K> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the object list with {@code objects}. {@code objects} must not contain
     * duplicate objects.
     */
    public void set(List<K> objects) {
        this.objects.set(objects);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBookGeneric<K> newData) {
        requireNonNull(newData);

        set(newData.getList());
    }

    /**
     * Returns true if an object with the same identity as {@code object} exists in the address book.
     */
    public boolean has(K object) {
        requireNonNull(object);
        return objects.contains(object);
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list.
     * The element should already be unique in unique list.
     */
    public Index getIndex(K object) {
        requireNonNull(object);
        return objects.getIndex(object);
    }


    /**
     * Adds an object to the address book. The object must not already exist in the address book.
     */
    public void add(K object) {
        objects.add(object);
    }

    /**
     * Adds an object to the address book at exact position index.
     */
    public void addAtIndex(K object, Integer idx) {
        objects.addAtIndex(object, idx);
    }

    /**
     * Replaces the given object {@code target} in the list with {@code edited}. {@code target}
     * must exist in the address book. The course identity of {@code edited} must not be the
     * same as another existing object in the address book.
     */
    public void set(K target, K edited) {
        requireNonNull(edited);

        objects.set(target, edited);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void remove(K key) {
        objects.remove(key);
    }

    //  ============== ID Operations ===========

    /**
     * Indicate if the address book has this objectID.
     */
    public boolean has(ID objID) {
        requireNonNull(objID);
        return objects.contains(objID);
    }

    /**
     * Gets an object to the address book by IDs.
     */
    public K get(ID objID) {
        requireNonNull(objID);
        return objects.get(objID);
    }

    /**
     * Removes an object from the address book by IDs.
     */
    public void remove(ID id) {
        objects.remove(id);
    }

    // ==========================================
    //// util methods
    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " objects";
        // TODO: refine later
    }

    @Override
    public ObservableList<K> getList() {
        return objects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBookGeneric
                // instanceof handles nulls
                && objects.equals(((AddressBookGeneric<K>) other).objects));
    }

    @Override
    public int hashCode() {
        return objects.hashCode();
    }
}
