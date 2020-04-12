package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.transaction.Transaction;
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
    private final CostPrice costPrice;
    private final Price price;

    // Data fields
    private final Quantity quantity;
    private final Money money;
    private final UUID id;

    private QuantityThreshold threshold;
    private double progress;

    /**
     * Every field must be present and not null.
     */
    public Product(Description description, CostPrice costPrice, Price price, Quantity quantity,
                   Money money, QuantityThreshold threshold, double progress) {
        requireAllNonNull(description, costPrice, price, quantity);
        this.id = UUID.randomUUID();
        this.description = description;
        this.costPrice = costPrice;
        this.price = price;
        this.quantity = quantity;
        this.money = money;
        this.threshold = threshold;
        this.progress = progress;
    }

    /**
     * Every field must be present and not null.
     */
    public Product(UUID id, Description description, CostPrice costPrice, Price price, Quantity quantity,
                   Money money, QuantityThreshold threshold, double progress) {
        requireAllNonNull(id, description, costPrice, price, quantity);
        this.id = id;
        this.description = description;
        this.costPrice = costPrice;
        this.price = price;
        this.quantity = quantity;
        this.money = money;
        this.threshold = threshold;
        this.progress = progress;
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getQuantitySold(List<Transaction> transactions) {
        int count = 0;

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            if (transaction.getProduct().equals(this)) {
                int quantity = transaction.getQuantity().getValue();
                count += quantity;
            }
        }
        return count;
    }

    public int getProfit(List<Transaction> transactions) {
        int profit = 0;

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            if (transaction.getProduct().equals(this)) {
                int price = transaction.getMoney().value;
                int quantity = transaction.getQuantity().getValue();
                int costPrice = Integer.parseInt(transaction.getProduct().getCostPrice().value);
                profit += (price - costPrice * quantity);
            }
        }
        return profit;
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
     * Returns true if the product's quantity is below or equal its threshold.
     */
    public boolean reachesThreshold() {
        return this.quantity.getValue() <= this.threshold.value;
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
                && otherProduct.getPrice().equals(getPrice()))
                && otherProduct.getQuantity().equals(getQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, costPrice, price, quantity, money);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
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
