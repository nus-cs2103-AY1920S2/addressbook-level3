package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Customer> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Product> PREDICATE_SHOW_ALL_PRODUCTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getProductListFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setProductListFilePath(Path productListFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasPerson(Customer customer);

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasProduct(Product product);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deletePerson(Customer target);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deleteProduct(Product target);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addPerson(Customer customer);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addProduct(Product product);

    /**
     * Replaces the given customer {@code target} with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer
     * in the address book.
     */
    void setPerson(Customer target, Customer editedCustomer);

    /**
     * Replaces the given customer {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedPerson} must not be the same as another existing customer
     * in the address book.
     */
    void setProduct(Product target, Product editedProduct);

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<Customer> predicate);

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProductList(Predicate<Product> predicate);

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Product> getFilteredProductList();
}
