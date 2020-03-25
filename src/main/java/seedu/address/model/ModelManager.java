package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.core.index.Index;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Quantity;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final InventorySystem addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Product> filteredProducts;
    private final FilteredList<Transaction> filteredTransactions;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyInventorySystem addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new InventorySystem(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(this.addressBook.getPersonList());
        filteredProducts = new FilteredList<>(this.addressBook.getProductList());
        filteredTransactions = new FilteredList<>(this.addressBook.getTransactionList());
    }

    public ModelManager() {
        this(new InventorySystem(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== InventorySystem ================================================================================

    @Override
    public void setAddressBook(ReadOnlyInventorySystem addressBook, String commandWord) {
        this.addressBook.resetData(addressBook, commandWord);
    }

    @Override
    public ReadOnlyInventorySystem getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Customer customer) {
        requireNonNull(customer);
        return addressBook.hasPerson(customer);
    }

    @Override
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return addressBook.hasProduct(product);
    }

    @Override
    public Product findProductById(UUID id) {
        requireNonNull(id);
        return addressBook.findProductById(id);
    }

    @Override
    public void deletePerson(Customer target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteProduct(Product target) {
        addressBook.removeProduct(target);
    }

    @Override
    public void addPerson(Customer customer) {
        addressBook.addPerson(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addProduct(Product product) {
        addressBook.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return addressBook.hasTransaction(transaction);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        addressBook.addTransaction(transaction);
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);
        addressBook.setTransaction(target, editedTransaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        addressBook.removeTransaction(transaction);
    }

    @Override
    public void setPerson(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);
        addressBook.setPerson(target, editedCustomer);
    }

    @Override
    public void setProduct(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);
        addressBook.setProduct(target, editedProduct);
    }

    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public ObservableList<Product> getFilteredProductList() {
        return filteredProducts;
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredCustomers.equals(other.filteredCustomers)
                && filteredProducts.equals(other.filteredProducts)
                && filteredTransactions.equals(other.filteredTransactions);
    }

}
