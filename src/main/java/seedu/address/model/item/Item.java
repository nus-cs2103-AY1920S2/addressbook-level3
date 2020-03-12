package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Item in the Expense Splitter of EYLAH.
 * Guarantees: details are present and not null.
 */
public class Item {

    // Identity fields
    private final ItemName name;

    // Data fields
    private final ItemPrice price;

    /**
     * Every field must be present,
     * ItemName and ItemPrice fields must not be null.
     *
     * @param name,price represents the compulsory fields of an Item object.
     */
    public Item(ItemName name, ItemPrice price) {
        requireAllNonNull(name, price);
        this.name = name;
        this.price = price;
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


    /**
     * Returns true if both Items of the same name have the same price.
     * This defines a weaker notion of equality between two food.
     * @param
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getItemName().equals(getItemName())
                && otherItem.getItemPrice().equals(getItemPrice());
    }

    /* Commented out this portion regarding stronger notion of equality for now.
    Will add back if needed in the future.

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return otherFood.getName().equals(getName())
                && otherFood.getCalories().equals(getCalories())
                && otherFood.getCarb().equals(getCarb())
                && otherFood.getFat().equals(getFat())
                && otherFood.getProtein().equals(getProtein())
                && otherFood.getSugar().equals(getSugar());
    }

     */

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getItemName())
                .append(" Item Price: $")
                .append(getItemPrice());

        return builder.toString();
    }
}
