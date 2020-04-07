package seedu.address.model;

import seedu.address.model.supplier.Supplier;
import seedu.address.model.version.LinearHistory;
import seedu.address.model.version.StateNotFoundException;
import seedu.address.model.version.Version;

/**
 * An {@code AddressBook} that keeps track of its history. Snapshots of its state are done based on external commands.
 */
public class VersionedAddressBook extends AddressBook implements Version<AddressBook> {
    private LinearHistory<AddressBook> history;

    public VersionedAddressBook() {
        history = new LinearHistory<>(new AddressBook());
    }

    /**
     * Creates a VersionedAddressBook with an initial state containing the {@code Supplier}s in the {@code toBeCopied}.
     */
    public VersionedAddressBook(ReadOnlyList<Supplier> toBeCopied) {
        history = new LinearHistory<>(new AddressBook(toBeCopied));
        updateDisplayedSuppliers();
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Resets the existing data of the current state with {@code newData}.
     */
    public void resetData(ReadOnlyList<Supplier> newData) {
        getCurrentState().resetData(newData);
        updateDisplayedSuppliers();
    }

    //=========== Supplier-Level Operations =========================================================================

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the current state.
     */
    public boolean hasSupplier(Supplier supplier) {
        return getCurrentState().hasSupplier(supplier);
    }

    /**
     * Adds a supplier to the current state.
     * The supplier must not already exist in the current state.
     */
    public void addSupplier(Supplier p) {
        getCurrentState().addSupplier(p);
        updateDisplayedSuppliers();
    }

    /**
     * Replaces the given supplier {@code target} in the current state with {@code editedSupplier}.
     * {@code target} must exist in the current state.
     * The supplier identity of {@code editedSupplier} must not be the same as another existing supplier in the
     * current state.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        getCurrentState().setSupplier(target, editedSupplier);
        updateDisplayedSuppliers();
    }

    /**
     * Removes {@code key} from the current state.
     * {@code key} must exist in the current state.
     */
    public void removeSupplier(Supplier key) {
        getCurrentState().removeSupplier(key);
        updateDisplayedSuppliers();
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        history.commit();
    }

    @Override
    public void undo() throws StateNotFoundException {
        history.undo();
        updateDisplayedSuppliers();
    }

    @Override
    public void redo() throws StateNotFoundException {
        history.redo();
        updateDisplayedSuppliers();
    }

    @Override
    public AddressBook getCurrentState() {
        return history.getCurrentState();
    }

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedSuppliers() {
         super.resetData(getCurrentState());
    }
}
