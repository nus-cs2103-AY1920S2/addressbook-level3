package seedu.nova.model;

import java.util.ArrayList;
import java.util.List;

/**
 * For use in redo and undo command for Address Book feature.
 * Save the state of AddressBook every time certain command is used.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currStatePointer;

    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currStatePointer = 0;
    }

    /**
     * Save and add current state of address book.
     */
    public void commit() {
        addressBookStateList.subList(currStatePointer + 1, addressBookStateList.size()).clear();
        addressBookStateList.add(new AddressBook(this));
        currStatePointer++;
    }

    public boolean canUndo() {
        return currStatePointer > 0;
    }

    public boolean canRedo() {
        return currStatePointer < addressBookStateList.size() - 1;
    }

    /**
     * Undo the command.
     */
    public void undo() {
        if (canUndo()) {
            currStatePointer--;
            resetData(addressBookStateList.get(currStatePointer));
        }
    }

    /**
     * Redo the command.
     */
    public void redo() {
        if (canRedo()) {
            currStatePointer++;
            resetData(addressBookStateList.get(currStatePointer));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                && currStatePointer == otherVersionedAddressBook.currStatePointer;
    }
}
