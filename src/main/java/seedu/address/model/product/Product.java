package seedu.address.model.product;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {

    // Identity fields
    private final Description description;
    private final Price price;
    private final Quantity quantity;
    private final Sales sales;

    /**
     * Every field must be present and not null.
     */
    public Product(Description description, Price price, Quantity quantity, Sales sales) {
        requireAllNonNull(description, price, quantity);
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sales = sales;
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Sales getSales() {
        return sales;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }

        return otherProduct != null
                && otherProduct.getDescription().equals(getDescription())
                && otherProduct.getPrice().equals(getPrice())
                && otherProduct.getQuantity().equals(getQuantity())
                && otherProduct.getSales().equals(getSales());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return otherProduct.getDescription().equals(getDescription())
                && otherProduct.getPrice().equals(getPrice())
                && otherProduct.getQuantity().equals(getQuantity())
                && otherProduct.getSales().equals(getSales());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, price, quantity, sales);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Sales: ")
                .append(getSales());;
        return builder.toString();
    }

}