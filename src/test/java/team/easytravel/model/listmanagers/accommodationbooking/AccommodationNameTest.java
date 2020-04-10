package team.easytravel.model.listmanagers.accommodationbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;

class AccommodationNameTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AccommodationName(null));
    }

    @Test
    public void constructorInvalidAccommodationNameThrowsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AccommodationName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> AccommodationName.isValidName(null));

        // blank name
        assertFalse(AccommodationName.isValidName("")); // Empty string
        assertFalse(AccommodationName.isValidName("   ")); // Multiple Spaces
        assertFalse(AccommodationName.isValidName(" ")); // Single space

        // invalid name
        assertFalse(AccommodationName.isValidName("^")); // Non-alphanumeric characters
        assertFalse(AccommodationName.isValidName("Dog*")); // Contains non-alphanumeric characters.
        assertFalse(AccommodationName.isValidName("yoursystemrunsf1234anyhowjustpresswhymylifesobadnow"
                + "IwanttowithdrawfromNUS")); // Contains 74 characters.


        // Valid name
        assertTrue(AccommodationName.isValidName("Jeffry ")); //Normal case
        assertTrue(AccommodationName.isValidName("Hello1234")); // Contains alphanumeric characters.
        assertTrue(AccommodationName.isValidName("hellomynameisJ ohn123an dIamA"
                + "Dog")); //Contains 30 alphanumeric char with spaces.
        assertTrue(AccommodationName.isValidName("yoursystemrunsf1234anyhow"
                + "justpress")); // Contains 34 characters.
    }

    @Test
    public void testToString() {
        assertEquals("Joshua", new AccommodationName("Joshua").toString());
        assertEquals("Kenny", new AccommodationName("Kenny").toString()); //Caps and non caps characters
        assertEquals("Lum123", new AccommodationName("Lum123").toString()); //Alphanumeric


        //Wrong toString
        assertNotEquals("Big Boss", new AccommodationName("Boss").toString());

    }

    @Test
    public void testEquals() {
        //Equal Name Object
        assertEquals(new AccommodationName("Henry"), new AccommodationName("Henry")); // When two names are the same.

        //Non Equal Name Object
        assertNotEquals(new AccommodationName("Wolfy"), new AccommodationName("Harry")); // When two names are diff
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new AccommodationName("Hashcode").hashCode(), new AccommodationName("Hashcode").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new AccommodationName("hashcode").hashCode(), new AccommodationName("HashCode").hashCode());

    }


}
