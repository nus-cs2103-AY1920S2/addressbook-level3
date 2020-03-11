package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list and product list.
     * This list will not contain any duplicate persons or duplicate products.
     */
    ObservableList<Person> getPersonList();
    ObservableList<Product> getProductList();

}
