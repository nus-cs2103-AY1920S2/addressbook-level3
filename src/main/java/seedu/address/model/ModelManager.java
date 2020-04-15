package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final InventorySystem inventorySystem;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Product> filteredProducts;
    private final FilteredList<Transaction> filteredTransactions;

    private Predicate<Customer> customerPredicate;
    private Predicate<Product> productPredicate;
    private Predicate<Transaction> transactionPredicate;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyInventorySystem addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.inventorySystem = new InventorySystem(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(this.inventorySystem.getPersonList());
        filteredProducts = new FilteredList<>(this.inventorySystem.getProductList());
        filteredTransactions = new FilteredList<>(this.inventorySystem.getTransactionList());
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
    public Path getInventorySystemFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setInventorySystemFilePath(Path inventorySystemFilePath) {
        requireNonNull(inventorySystemFilePath);
        userPrefs.setAddressBookFilePath(inventorySystemFilePath);
    }

    //=========== InventorySystem ================================================================================

    @Override
    public void setInventorySystem(ReadOnlyInventorySystem inventorySystem, String commandWord) {
        this.inventorySystem.resetData(inventorySystem, commandWord);
    }

    @Override
    public ReadOnlyInventorySystem getInventorySystem() {
        return inventorySystem;
    }

    @Override
    public boolean hasPerson(Customer customer) {
        requireNonNull(customer);
        return inventorySystem.hasPerson(customer);
    }

    @Override
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return inventorySystem.hasProduct(product);
    }

    @Override
    public Product findProductById(UUID id) {
        requireNonNull(id);
        return inventorySystem.findProductById(id);
    }

    @Override
    public void deletePerson(Customer target) {
        inventorySystem.removePerson(target);
    }

    @Override
    public void deleteProduct(Product target) {
        inventorySystem.removeProduct(target);
    }

    @Override
    public void addPerson(Customer customer) {
        inventorySystem.addPerson(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addProduct(Product product) {
        inventorySystem.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return inventorySystem.hasTransaction(transaction);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        inventorySystem.addTransaction(transaction);
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);
        inventorySystem.setTransaction(target, editedTransaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        inventorySystem.removeTransaction(transaction);
    }

    @Override
    public ObservableList<Transaction> filterTransaction(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        FilteredList<Transaction> newFilteredTransactions =
                new FilteredList<>(inventorySystem.getTransactionList());
        newFilteredTransactions.setPredicate(predicate);
        return newFilteredTransactions;
    }

    @Override
    public void setPerson(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);
        inventorySystem.setPerson(target, editedCustomer);
    }

    @Override
    public void setProduct(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);
        inventorySystem.setProduct(target, editedProduct);
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
        customerPredicate = predicate;
    }

    @Override
    public void updateFilteredCustomerList() {
        filteredCustomers.setPredicate(customerPredicate);
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(PREDICATE_SHOW_ALL_PRODUCTS);
        int fullProductListSize = getFilteredProductList().size();

        filteredProducts.setPredicate(predicate);
        productPredicate = predicate;

        SortedList<Product> sortedProduct = new SortedList<>(filteredProducts);
        sortedProduct.comparatorProperty().set((o1, o2) -> {
            if (o1.getProgress() - o2.getProgress() > 0) {
                return 1;
            } else if (o1.getProgress() == o2.getProgress()) {
                return 0;
            } else {
                return -1;
            }
        });
        if (sortedProduct.size() == fullProductListSize) {
            inventorySystem.setProducts(sortedProduct);
        }
    }

    @Override
    public void updateFilteredProductList() {
        filteredProducts.setPredicate(productPredicate);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
        transactionPredicate = predicate;
    }

    @Override
    public void updateFilteredTransactionList() {
        filteredTransactions.setPredicate(transactionPredicate);
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
        return inventorySystem.equals(other.inventorySystem)
                && userPrefs.equals(other.userPrefs)
                && filteredCustomers.equals(other.filteredCustomers)
                && filteredProducts.equals(other.filteredProducts)
                && filteredTransactions.equals(other.filteredTransactions);
    }

}
