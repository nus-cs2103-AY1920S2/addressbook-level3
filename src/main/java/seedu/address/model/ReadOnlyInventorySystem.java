package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyInventorySystem {

    /**
     * Returns an unmodifiable view of the persons list and product list.
     * This list will not contain any duplicate persons or duplicate products.
     */
    ObservableList<Customer> getPersonList();
    ObservableList<Product> getProductList();
    ObservableList<Transaction> getTransactionList();

}
