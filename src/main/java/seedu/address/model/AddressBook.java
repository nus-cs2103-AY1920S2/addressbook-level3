package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.UniqueCustomerList;
import seedu.address.model.product.Product;
import seedu.address.model.product.UniqueProductList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCustomerList persons;
    private final UniqueProductList products;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueCustomerList();
        products = new UniqueProductList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the customer list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setPersons(List<Customer> customers) {
        this.persons.setPersons(customers);
    }

    /**
     * Replaces the contents of the customer list with {@code persons}.
     * {@code products} must not contain duplicate persons.
     */
    public void setProducts(List<Product> products) {
        this.products.setProduct(products);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setProducts(newData.getProductList());
    }

    //// customer-level operations

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    public boolean hasPerson(Customer customer) {
        requireNonNull(customer);
        return persons.contains(customer);
    }

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return products.contains(product);
    }

    /**
     * Adds a customer to the address book.
     * The customer must not already exist in the address book.
     */
    public void addPerson(Customer p) {
        persons.add(p);
    }

    /**
     * Adds a customer to the address book.
     * The customer must not already exist in the address book.
     */
    public void addProduct(Product p) {
        products.add(p);
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the address book.
     */
    public void setPerson(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);

        persons.setPerson(target, editedCustomer);
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedPerson} must not be the same as another existing customer in the address book.
     */
    public void setProduct(Product target, Product editedProduct) {
        requireNonNull(editedProduct);

        products.setProduct(target, editedProduct);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Customer key) {
        persons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProduct(Product key) {
        products.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Customer> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Product> getProductList() {
        return products.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
