package team.easytravel.model.listmanagers.packinglistitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;


class ItemNameTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ItemName(null));
    }

    @Test
    public void constructorInvalidNameThrowsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ItemName(invalidName));
    }


    @Test
    public void isValidName() {

        // null name
        Assert.assertThrows(NullPointerException.class, () -> ItemName.isValidName(null));

        // blank name
        assertFalse(ItemName.isValidName("")); // Empty string
        assertFalse(ItemName.isValidName(" ")); // Spaces only
        assertFalse(ItemName.isValidName("  ")); // Multiple spaces

        // invalid name
        assertFalse(ItemName.isValidName("^")); // Non-alphanumeric characters
        assertFalse(ItemName.isValidName("Dog*")); // Contains non-alphanumeric characters.
        assertFalse(ItemName.isValidName("yoursystemrunsf1234anyhowjustpresswhymylifesobadnow"
                + "IwanttowithdrawfromNUS")); // Contains 74 characters.
        assertFalse(ItemName.isValidName("hellomynameisJ ohn12"
                + "Doghahahahahhacsgohehelaaldota")); //Contains 50 alphanumeric char with spaces.

        // Valid name
        assertTrue(ItemName.isValidName("Hello ")); //Normal case
        assertTrue(ItemName.isValidName("Hello1234")); // Contains alphanumeric characters.
        assertTrue(ItemName.isValidName("yoursystemrunsf1234anyhow"
                + "justp")); // Contains 30 characters.
        assertTrue(ItemName.isValidName("ahaha56789j  " + "cjeudhwny7p")); // Contains 24 characters.


    }

    @Test
    public void testToString() {

        assertEquals("1000", new ItemName("1000").toString());
        assertEquals("HelloImaDog", new ItemName("HelloImaDog").toString()); //Caps and non caps characters
        assertEquals("1000Money", new ItemName("1000Money").toString()); //Alphanumeric


        //Wrong toString
        assertNotEquals("Wrong", new ItemName("Hello").toString());

    }

    @Test
    public void testEquals() {
        //Equal Description Object
        assertEquals(new ItemName("10Shirt"), new ItemName("10Shirt")); // When two name are the same.

        //Non Equal Description Object
        assertNotEquals(new ItemName("10Pants"), new ItemName("20Shirt")); // When two name are different.

    }

    @Test
    public void testHashCode() {

        // Equal Hashcode
        assertEquals(new ItemName("Hashcode").hashCode(), new ItemName("Hashcode").hashCode());

        //Equal Hashcode
        assertEquals(new ItemName("hashcode").hashCode(), new ItemName("Hashcode").hashCode());

        //Not Equal Hashcode
        assertNotEquals(new ItemName("hashcode").hashCode(), new ItemName("HashCode").hashCode());
    }
}
