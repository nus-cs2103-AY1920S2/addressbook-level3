package team.easytravel.storage.packinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalPackingListItem.PACKING_LIST_PASSPORT;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;

import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;


class JsonAdaptedPackingListItemTest {

    public static final String INVALID_ITEM_NAME = "T!T!@#E";
    public static final Integer INVALID_QUANTITY = -10;
    public static final String INVALID_ITEM_CATEGORY = "M)&SC&W!";


    public static final String VALID_ITEM_NAME = PACKING_LIST_PASSPORT.getItemName().value;
    public static final Integer VALID_QUANTITY = PACKING_LIST_PASSPORT.getQuantity().value;
    public static final String VALID_ITEM_CATEGORY = PACKING_LIST_PASSPORT.getItemCategory().value;


    @Test
    public void toModelType_validPackingListItemDetails_returnsPackingListItem() throws Exception {
        JsonAdaptedPackingListItem packingListItem = new JsonAdaptedPackingListItem(PACKING_LIST_PASSPORT);
        assertEquals(PACKING_LIST_PASSPORT, packingListItem.toModelType());
    }

    @Test
    public void toModelType_invalidItemName_throwsIllegalValueException() {
        JsonAdaptedPackingListItem item =
                new JsonAdaptedPackingListItem(INVALID_ITEM_NAME, VALID_QUANTITY, VALID_ITEM_CATEGORY, true);
        String exceptedMessage = ItemName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, exceptedMessage, item::toModelType);
    }


    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedPackingListItem item =
                new JsonAdaptedPackingListItem(VALID_ITEM_NAME, INVALID_QUANTITY, VALID_ITEM_CATEGORY, true);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }


    @Test
    public void toModelType_invalidItemCategory_throwsIllegalValueException() {
        JsonAdaptedPackingListItem item =
                new JsonAdaptedPackingListItem(VALID_ITEM_NAME, VALID_QUANTITY, INVALID_ITEM_CATEGORY, true);
        String expectedMessage = ItemCategory.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

}
