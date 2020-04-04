package seedu.address.model;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;

import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Customer> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Product> PREDICATE_SHOW_ALL_PRODUCTS = unused -> true;
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;

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
    Path getInventorySystemFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setInventorySystemFilePath(Path inventorySystemFilePath);

    /**
     * Replaces address book data with the data in {@code inventorySystem}.
     */
    void setInventorySystem(ReadOnlyInventorySystem inventorySystem, String commandWord);

    /** Returns the InventorySystem */
    ReadOnlyInventorySystem getInventorySystem();

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasPerson(Customer customer);

    /**
     * Returns true if a product with the same identity as {@code product} exists in the product list.
     */
    boolean hasProduct(Product product);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deletePerson(Customer target);

    /**
     * Deletes the given product.
     * The product must exist in the product list.
     */
    void deleteProduct(Product target);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the product list.
     */
    void deleteTransaction(Transaction target);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addPerson(Customer customer);

    /**
     * Adds the given product.
     * {@code product} must not already exist in the product list.
     */
    void addProduct(Product product);

    /**
     * Find product by id.
     * {@code id} the unique id.
     */
    Product findProductById(UUID id);

    /**
     * Replaces the given customer {@code target} with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer
     * in the address book.
     */
    void setPerson(Customer target, Customer editedCustomer);

    /**
     * Replaces the given product {@code target} with {@code editedProduct}.
     * {@code target} must exist in the product list.
     * The product identity of {@code editedProduct} must not be the same as
     * another existing product in the address book.
     */
    void setProduct(Product target, Product editedProduct);

    /**
     * Replaces the given customer {@code target} with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer
     * in the address book.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the address book.
     */
    boolean hasTransaction(Transaction transaction);

    /**
     * Adds the given transaction.
     * {@code transaction} must not already exist in the address book.
     */
    void addTransaction(Transaction transaction);

    /**
     * Return a filtered observable transactions list.
     * @param predicate specifies the matching condition.
     */
    ObservableList<Transaction> filterTransaction(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<Customer> predicate);

    /**
     * Updates the filter of the filtered customer list to filter by the current {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList();

    /**
     * Updates the filter of the filtered product list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProductList(Predicate<Product> predicate);


    /**
     * Updates the filter of the filtered product list to filter by the current {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProductList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Updates the filter of the filtered transaction list to filter by the current {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList();


    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredProductList();

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Transaction> getFilteredTransactionList();
}
