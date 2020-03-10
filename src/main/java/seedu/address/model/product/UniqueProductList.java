package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A customer is considered unique by comparing using {@code Customer#isSamePerson(Customer)}. As such, adding
 * and updating of persons uses Customer#isSamePerson(Customer) for equality so as to ensure that the customer being
 * added or updated is unique in terms of identity in the UniqueCustomerList. However, the removal of a customer uses
 * Customer#equals(Object) so as to ensure that the customer with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Product#isSameProduct(Product)
 */
public class UniqueProductList implements Iterable<Product> {

    private final ObservableList<Product> internalList = FXCollections.observableArrayList();
    private final ObservableList<Product> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent customer as the given argument.
     */
    public boolean contains(Product toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProduct);
    }

    /**
     * Adds a customer to the list.
     * The customer must not already exist in the list.
     */
    public void add(Product toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProductException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the customer {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The customer identity of {@code editedPerson} must not be the same as another existing customer in the list.
     */
    public void setProduct(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProductNotFoundException();
        }

        if (!target.isSameProduct(editedProduct) && contains(editedProduct)) {
            throw new DuplicateProductException();
        }

        internalList.set(index, editedProduct);
    }

    public void setProduct(UniqueProductList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setProduct(List<Product> products) {
        requireAllNonNull(products);
        if (!productsAreUnique(products)) {
            throw new DuplicateProductException();
        }

        internalList.setAll(products);
    }

    /**
     * Removes the equivalent customer from the list.
     * The customer must exist in the list.
     */
    public void remove(Product toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProductNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Product> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Product> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProductList // instanceof handles nulls
                && internalList.equals(((UniqueProductList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean productsAreUnique(List<Product> products) {
        for (int i = 0; i < products.size() - 1; i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(i).isSameProduct(products.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
