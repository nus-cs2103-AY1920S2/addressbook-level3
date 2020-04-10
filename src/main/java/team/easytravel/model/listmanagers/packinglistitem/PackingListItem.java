package team.easytravel.model.listmanagers.packinglistitem;

import java.util.Objects;

import team.easytravel.commons.util.CollectionUtil;
import team.easytravel.model.util.uniquelist.UniqueListElement;

/**
 * Represents a PackingListItem in the PackingListManager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PackingListItem implements UniqueListElement {
    // Identity fields
    private final ItemName itemName;

    // Data fields
    private final boolean isChecked;
    private final Quantity quantity;
    private final ItemCategory itemCategory;

    /**
     * Every field must be present and not null.
     *
     * @param itemName     the item name
     * @param quantity     the quantity
     * @param itemCategory the item category
     * @param isChecked    the is checked
     */
    public PackingListItem(ItemName itemName, Quantity quantity, ItemCategory itemCategory, boolean isChecked) {
        CollectionUtil.requireAllNonNull(itemName, quantity, itemCategory);
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemCategory = itemCategory;
        this.isChecked = isChecked;
    }

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public ItemName getItemName() {
        return itemName;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Gets item category.
     *
     * @return the item category
     */
    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    /**
     * Returns true of the packing list item is checked.
     *
     * @return the boolean
     */
    public boolean getIsChecked() {
        return isChecked;
    }

    /**
     * Returns true if both packing list items has the same identity fields.
     * This defines a weaker notion of equality between two packing list items.
     */
    @Override
    public boolean isSame(UniqueListElement other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PackingListItem)) {
            return false;
        }

        return itemName.equals(((PackingListItem) other).itemName);
    }

    /**
     * Returns true if both packing list items have the same identity and data fields.
     * This defines a stronger notion of equality between two packing list items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PackingListItem)) {
            return false;
        }

        PackingListItem otherItem = (PackingListItem) other;
        return itemName.equals(otherItem.itemName)
                && quantity.equals(otherItem.quantity)
                && itemCategory.equals(otherItem.itemCategory)
                && isChecked == otherItem.isChecked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, quantity, itemCategory, isChecked);
    }

    @Override
    public String toString() {
        return "Packing list item - Item Name: " + itemName
                + " Quantity: " + quantity
                + " Category: " + itemCategory
                + " Is Checked: " + isChecked;
    }
}
