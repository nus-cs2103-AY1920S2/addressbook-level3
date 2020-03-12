package seedu.eylah.expensesplitter.testutil;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Chicken Rice";
    public static final double DEFAULT_PRICE = 3.50;

    private ItemName name;
    private ItemPrice price;

    public ItemBuilder() {
        name = new ItemName(DEFAULT_NAME);
        price = new ItemPrice(DEFAULT_PRICE);
    }

    /**
     * Initializes the ItemBuilder with the data of {@code ItemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getItemName();
        price = itemToCopy.getItemPrice();

    }

    /**
     * Sets the {@code ItemName} of the {@code Item} that we are building.
     */
    public ItemBuilder withName(String name) {
        this.name = new ItemName(name);
        return this;
    }

    /**
     * Sets the {@code ItemPrice} of the {@code Item} that we are building.
     */
    public ItemBuilder withPrice(double price) {
        this.price = new ItemPrice(price);
        return this;
    }

    public Item build() {
        return new Item(name, price);
    }


}
