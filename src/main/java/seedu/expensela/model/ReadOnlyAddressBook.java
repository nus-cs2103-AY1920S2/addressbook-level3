package seedu.expensela.model;

import javafx.collections.ObservableList;
import seedu.expensela.model.transaction.Transaction;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Transaction> getPersonList();

}
