package team.easytravel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;

/**
 * A utility class containing a list of {@code PackingListItem} objects to be used in tests.
 */
public class TypicalPackingListItem {

    public static final PackingListItem PACKING_LIST_SHIRT = new PackingListItem(
            new ItemName("Tshirt"), new Quantity(7), new ItemCategory("clothes"), false);

    public static final PackingListItem PACKING_LIST_JEANS = new PackingListItem(
            new ItemName("Jeans"), new Quantity(5), new ItemCategory("clothes"), false);

    public static final PackingListItem PACKING_LIST_UNDERWEAR = new PackingListItem(
            new ItemName("Underwear"), new Quantity(7), new ItemCategory("clothes"), false);

    public static final PackingListItem PACKING_LIST_PASSPORT = new PackingListItem(
            new ItemName("Passport"), new Quantity(1), new ItemCategory("international"), false);

    public static final PackingListItem PACKING_LIST_SHAMPOO = new PackingListItem(
            new ItemName("Shampoo"), new Quantity(1), new ItemCategory("toiletries"), false);

    public static final PackingListItem PACKING_LIST_CONDITIONER = new PackingListItem(
            new ItemName("Conditioner"), new Quantity(1), new ItemCategory("toiletries"), false);

    // Dont touch these packing list items below, used for storage testing
    public static final PackingListItem PACKING_LIST_SANDALS = new PackingListItem(
            new ItemName("Sandals"), new Quantity(1), new ItemCategory("clothes"), false);

    public static final PackingListItem PACKING_LIST_SHAVER = new PackingListItem(
            new ItemName("Shaver"), new Quantity(1), new ItemCategory("toiletries"), false);

    private TypicalPackingListItem() {
    }

    /**
     * Returns an {@code TypicalPackingListItem} with all the typical PackingListItem
     */

    public static PackingListManager getTypicalPackingListManager() {
        PackingListManager pm = new PackingListManager();
        for (PackingListItem packingListItem : getTypicalPackingListItems()) {
            pm.addPackingListItem(packingListItem);
        }
        return pm;
    }

    public static List<PackingListItem> getTypicalPackingListItems() {
        return new ArrayList<>(Arrays.asList(PACKING_LIST_UNDERWEAR, PACKING_LIST_SHIRT,
                PACKING_LIST_JEANS, PACKING_LIST_CONDITIONER, PACKING_LIST_PASSPORT, PACKING_LIST_SHAMPOO));
    }


}
