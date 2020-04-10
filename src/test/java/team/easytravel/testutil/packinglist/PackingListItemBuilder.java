package team.easytravel.testutil.packinglist;

import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;

/**
 * The type Packing list item builder.
 */
public class PackingListItemBuilder {
    /**
     * The constant DEFAULT_ITEM_NAME.
     */
    public static final String DEFAULT_ITEM_NAME = "Shampoo";
    /**
     * The constant DEFAULT_QUANTITY.
     */
    public static final Integer DEFAULT_QUANTITY = 1;
    /**
     * The constant DEFAULT_ITEM_CATEGORY.
     */
    public static final String DEFAULT_ITEM_CATEGORY = "toiletries";

    private ItemName itemName;
    private Quantity quantity;
    private ItemCategory itemCategory;

    /**
     * Instantiates a new Packing list item builder.
     */
    public PackingListItemBuilder() {
        itemName = new ItemName(DEFAULT_ITEM_NAME);
        quantity = new Quantity(DEFAULT_QUANTITY);
        itemCategory = new ItemCategory(DEFAULT_ITEM_CATEGORY);
    }

    /**
     * Instantiates a new Packing list item builder.
     *
     * @param packingListItem the packing list item
     */
    public PackingListItemBuilder(PackingListItem packingListItem) {
        itemName = packingListItem.getItemName();
        quantity = packingListItem.getQuantity();
        itemCategory = packingListItem.getItemCategory();
    }

    /**
     * With item name packing list item builder.
     *
     * @param itemName the item name
     * @return the packing list item builder
     */
    public PackingListItemBuilder withItemName(String itemName) {
        this.itemName = new ItemName(itemName);
        return this;
    }

    /**
     * With quantity packing list item builder.
     *
     * @param quantity the quantity
     * @return the packing list item builder
     */
    public PackingListItemBuilder withQuantity(int quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * With item category packing list item builder.
     *
     * @param itemCategory the item category
     * @return the packing list item builder
     */
    public PackingListItemBuilder withItemCategory(String itemCategory) {
        this.itemCategory = new ItemCategory(itemCategory);
        return this;
    }

    /**
     * Build packing list item.
     *
     * @return the packing list item
     */
    public PackingListItem build() {
        return new PackingListItem(itemName, quantity, itemCategory, false);
    }
}
