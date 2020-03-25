package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;
import seedu.address.model.util.QuantityThreshold;

/**
 * Represents a Product in the product list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {

    // Identity fields
    private final Description description;

    // Data fields
    private final Price price;
    private final Quantity quantity;
    private final Sales sales;

    private QuantityThreshold threshold;

    /**
     * Every field must be present and not null.
     */
    public Product(Description description, Price price, Quantity quantity, Sales sales, QuantityThreshold threshold) {
        requireAllNonNull(description, price, quantity);
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sales = sales;
        this.threshold = threshold;
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

    public QuantityThreshold getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = new QuantityThreshold(threshold);
    }
    /**
     * Returns true if both products have the same identity and data fields.
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
     * Returns true if both products have the same identity and data fields.
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
        builder.append("Description: ")
                .append(getDescription())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Sales: ")
                .append(getSales())
                .append(" Threshold: ")
                .append(getThreshold());
        return builder.toString();
    }

}
