package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;

import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.item.ItemPrice;
import seedu.eylah.expensesplitter.model.person.Amount;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Chicken Rice";
    public static final BigDecimal DEFAULT_PRICE = new BigDecimal("3.50");
    public static final BigDecimal DEFAULT_AMOUNT_PER_PERSON = new BigDecimal("3.50");

    private ItemName name;
    private ItemPrice price;
    private Amount amountPerPerson;

    public ItemBuilder() {
        name = new ItemName(DEFAULT_NAME);
        price = new ItemPrice(DEFAULT_PRICE);
        amountPerPerson = new Amount(DEFAULT_AMOUNT_PER_PERSON);
    }

    /**
     * Initializes the ItemBuilder with the data of {@code ItemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getItemName();
        price = itemToCopy.getItemPrice();
        amountPerPerson = itemToCopy.getAmountPerPerson();
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
    public ItemBuilder withPrice(BigDecimal price) {
        this.price = new ItemPrice(price);
        return this;
    }

    /**
     * Sets the {@code amountPerPerson} of the {@code Item} that we are building.
     */
    public ItemBuilder withAmountPerPerson(BigDecimal amountPerPerson) {
        this.amountPerPerson = new Amount(amountPerPerson);
        return this;
    }

    public Item build() {
        return new Item(name, price, amountPerPerson);
    }


}
