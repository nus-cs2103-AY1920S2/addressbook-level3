package seedu.eylah.expensesplitter.model.item;

import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.eylah.expensesplitter.model.person.Amount;


/**
 * Represents an Item in the Expense Splitter of EYLAH.
 * Guarantees: details are present and not null.
 */
public class Item {

    // Identity fields
    private final ItemName name;

    // Data fields
    private final ItemPrice price;
    private final Amount amountPerPerson;

    /**
     * Every field must be present,
     * ItemName and ItemPrice fields must not be null.
     *
     * @param name,price represents the compulsory fields of an Item object.
     */
    public Item(ItemName name, ItemPrice price, Amount amountPerPerson) {
        requireAllNonNull(name, price);
        this.name = name;
        this.price = price;
        this.amountPerPerson = amountPerPerson;
    }

    /**
     * Returns the ItemName of an Item.
     *
     * @return the ItemName of an Item.
     */
    public ItemName getItemName() {
        return name;
    }

    /**
     * Returns the ItemPrice of an Item.
     *
     * @return the ItemPrice of an Item.
     */
    public ItemPrice getItemPrice() {
        return price;
    }

    public Amount getAmountPerPerson() {
        return amountPerPerson;
    }

    /**
     * Returns true if both Items of the same name have the same price.
     * This defines a weaker notion of equality between two food.
     *
     * @param otherItem the other Item we are comparing with.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }
        return otherItem != null
                && otherItem.getItemName().equals(getItemName())
                && otherItem.getItemPrice().equals(getItemPrice());
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getItemName().equals(getItemName())
                && otherItem.getItemPrice().equals(getItemPrice());
    }



    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String formattedItemName = getItemName().toString().substring(0, 1).toUpperCase()
                + getItemName().toString().substring(1).toLowerCase();
        builder.append(formattedItemName)
                .append(" | Price: ")
                .append(getItemPrice());
        return builder.toString();
    }
}
