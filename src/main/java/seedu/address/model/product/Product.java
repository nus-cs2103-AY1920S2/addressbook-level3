package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.UUID;

import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
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
    private final CostPrice costPrice;
    private final Price price;
    private final Quantity quantity;
    private final Money money;
    private final UUID id;

    private QuantityThreshold threshold;

    /**
     * Every field must be present and not null.
     */
    public Product(Description description, CostPrice costPrice, Price price, Quantity quantity,
                   Money money, QuantityThreshold threshold) {
        requireAllNonNull(description, costPrice, price, quantity);
        this.id = UUID.randomUUID();
        this.description = description;
        this.costPrice = costPrice;
        this.price = price;
        this.quantity = quantity;
        this.money = money;
        this.threshold = threshold;
    }

    /**
     * Every field must be present and not null.
     */
    public Product(UUID id, Description description, CostPrice costPrice, Price price, Quantity quantity,
                   Money money, QuantityThreshold threshold) {
        requireAllNonNull(id, description, costPrice, price, quantity);
        this.id = id;
        this.description = description;
        this.costPrice = costPrice;
        this.price = price;
        this.quantity = quantity;
        this.money = money;
        this.threshold = threshold;
    }

    public UUID getId() {
        return id;
    }

    public Description getDescription() {
        return description;
    }

    public CostPrice getCostPrice() {
        return costPrice;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getMoney() {
        return money;
    }

    public QuantityThreshold getThreshold() {
        return threshold;
    }

    public void setThreshold(String quantityThreshold) {
        this.threshold = new QuantityThreshold(quantityThreshold);
    }

    /**
     * Returns true if both products have the same identity and data fields.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }

        return otherProduct != null
                && otherProduct.getId().equals(getId())
                || (otherProduct.getDescription().equals(getDescription())
                && otherProduct.getCostPrice().equals(getCostPrice())
                && otherProduct.getPrice().equals(getPrice()));
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
        return otherProduct.getId().equals(getId())
                || (otherProduct.getDescription().equals(getDescription())
                && otherProduct.getCostPrice().equals(getCostPrice())
                && otherProduct.getPrice().equals(getPrice()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, costPrice, price, quantity, money);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription() + " (" + getId() + ")")
                .append("\nCost Price: $")
                .append(getCostPrice())
                .append(" Price: $")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Sales: $")
                .append(getMoney())
                .append(" Threshold: ")
                .append(getThreshold());
        return builder.toString();
    }

}
