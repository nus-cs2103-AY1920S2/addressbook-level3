package team.easytravel.model.listmanagers.packinglistitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;

class PackingListItemTest {

    private final PackingListItem packingListItem = new PackingListItem(new ItemName("Shirt"), new Quantity(10),
            new ItemCategory("Clothing"), false);

    @Test
    public void constructorInvalidNameThrowsIllegalArgumentException() {
        String invalidName = " ";
        Integer invalidQuantity = 0;
        boolean isChecked = false;
        Assert.assertThrows(IllegalArgumentException.class, () -> new PackingListItem(new ItemName(invalidName),
                new Quantity(invalidQuantity), new ItemCategory(null), isChecked));
    }

    @Test
    public void getItemName() {

        // Correct case
        assertEquals(new ItemName("Shirt"), new PackingListItem(new ItemName("Shirt"),
                new Quantity(10), new ItemCategory("Clothing"), false).getItemName());

        //Different case
        assertNotEquals(new ItemName("Shirt"), new PackingListItem(new ItemName("Pants"),
                new Quantity(10), new ItemCategory("Clothing"), false).getItemName());

    }

    @Test
    public void getQuantity() {

        // Correct case
        assertEquals(new Quantity(10), new PackingListItem(new ItemName("Shirt"),
                new Quantity(10), new ItemCategory("Clothing"), false).getQuantity());

        //Different case
        assertNotEquals(new Quantity(5), new PackingListItem(new ItemName("Pants"),
                new Quantity(20), new ItemCategory("Clothing"), false).getQuantity());

    }

    @Test
    public void isChecked() {

        // Correct case
        assertTrue(new PackingListItem(new ItemName("Shirt"),
                new Quantity(10), new ItemCategory("Clothing"), true).getIsChecked());

        //Different case
        assertFalse(new PackingListItem(new ItemName("Shirt"),
                new Quantity(10), new ItemCategory("Clothing"), false).getIsChecked());
    }

    @Test
    public void isSameItem() {
        // same object -> returns true
        assertTrue(packingListItem.isSame(packingListItem));

        // null -> returns false
        assertFalse(packingListItem.isSame(null));

        // different name and quantity -> returns false
        PackingListItem editedPackingListItem = new PackingListItem(new ItemName("Raincoat"), new Quantity(30),
                new ItemCategory("Clothing"), false);
        assertFalse(editedPackingListItem.isSame(packingListItem));

        // different name -> returns false
        PackingListItem secondEditedPackingListItem = new PackingListItem(new ItemName("Pants"), new Quantity(10),
               new ItemCategory("Clothing"), false);
        assertFalse(editedPackingListItem.isSame(secondEditedPackingListItem));
    }

    @Test
    public void testEquals() {
        // Same case
        PackingListItem editedPackingListItem = new PackingListItem(new ItemName("Shirt"), new Quantity(10),
                new ItemCategory("Clothing"), false);
        assertEquals(packingListItem, editedPackingListItem);

        //Different case
        editedPackingListItem = new PackingListItem(new ItemName("Pants"), new Quantity(1),
                new ItemCategory("Clothing"), false);
        assertNotEquals(editedPackingListItem, packingListItem);
    }

    @Test
    public void testHashCode() {

        //Same case
        PackingListItem editedPackingListItem = new PackingListItem(new ItemName("Shirt"), new Quantity(10),
                new ItemCategory("Clothing"), false);
        assertEquals(editedPackingListItem.hashCode(), packingListItem.hashCode());

        //Different case
        editedPackingListItem = new PackingListItem(new ItemName("Pants"), new Quantity(1),
                new ItemCategory("Clothing"), false);
        assertNotEquals(editedPackingListItem.hashCode(), packingListItem.hashCode());
    }

    @Test
    public void testToString() {
        PackingListItem editedPackingListItem = new PackingListItem(new ItemName("Shirt"), new Quantity(10),
                new ItemCategory("Clothing"), false);
        assertEquals(packingListItem.toString(), editedPackingListItem.toString());

        editedPackingListItem = new PackingListItem(new ItemName("Pants"), new Quantity(1),
                new ItemCategory("Clothing"), false);
        assertNotEquals(editedPackingListItem.toString(), packingListItem.toString());

    }

    @Test
    public void getItemCategory() {
        PackingListItem editedPackingListItem = new PackingListItem(new ItemName("Shirt"), new Quantity(10),
                new ItemCategory("Clothing"), false);

        assertEquals(new ItemCategory("Clothing"), editedPackingListItem.getItemCategory());

        editedPackingListItem = new PackingListItem(new ItemName("Shirt"), new Quantity(10),
                new ItemCategory("Medicine"), false);

        assertNotEquals(packingListItem, editedPackingListItem);

    }
}
