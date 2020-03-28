package seedu.address.testutil.product;

import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.model.util.QuantityThreshold;

/**
 * A utility class to help with building Customer objects.
 */
public class ProductBuilder {

    public static final String DEFAULT_DESCRIPTION = "Black watch";
    public static final String DEFAULT_COSTPRICE = "10";
    public static final String DEFAULT_PRICE = "22";
    public static final String DEFAULT_QUANTITY = "12";
    public static final String DEFAULT_SALES = "44";
    public static final String DEFAULT_THRESHOLD = "5";

    private Description description;
    private CostPrice costPrice;
    private Price price;
    private Money money;
    private Quantity quantity;
    private QuantityThreshold threshold;

    public ProductBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        costPrice = new CostPrice(DEFAULT_COSTPRICE);
        price = new Price(DEFAULT_PRICE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        money = new Money(DEFAULT_SALES);
        threshold = new QuantityThreshold(DEFAULT_THRESHOLD);
    }

    /**
     * Initializes the ProductBuilder with the data of {@code productToCopy}.
     */
    public ProductBuilder(Product productToCopy) {
        description = productToCopy.getDescription();
        costPrice = productToCopy.getCostPrice();
        price = productToCopy.getPrice();
        quantity = productToCopy.getQuantity();
        money = productToCopy.getMoney();
        threshold = productToCopy.getThreshold();
    }

    /**
     * Sets the {@code Description} of the {@code Product} that we are building.
     */
    public ProductBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code CostPrice} of the {@code Product} that we are building.
     */
    public ProductBuilder withCostPrice(String costPrice) {
        this.costPrice = new CostPrice(costPrice);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Product} that we are building.
     */
    public ProductBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Product} that we are building.
     */
    public ProductBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Money} of the {@code Product} that we are building.
     */
    public ProductBuilder withMoney(String money) {
        this.money = new Money(money);
        return this;
    }

    /**
     * Sets te {@code QuantityThreshold} of the {@code Product} that we are building.
     */
    public ProductBuilder withThreshold(String threshold) {
        this.threshold = new QuantityThreshold(threshold);
        return this;
    }

    public Product build() {
        return new Product(description, costPrice, price, quantity, money, threshold);
    }

}

