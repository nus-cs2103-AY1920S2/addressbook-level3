package team.easytravel.model.listmanagers.transportbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.model.util.attributes.Location;
import team.easytravel.testutil.Assert;


class LocationTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructorInvalidLocationThrowsIllegalArgumentException() {
        String invalidLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }



    @Test
    public void isValidLocation() {

        // null location
        Assert.assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // blank location
        assertFalse(Location.isValidLocation("")); // Empty string
        assertFalse(Location.isValidLocation("  ")); // Multiple Spaces
        assertFalse(Location.isValidLocation(" ")); // Single space

        // invalid location
        assertFalse(Location.isValidLocation("^")); // Non-alphanumeric characters
        assertFalse(Location.isValidLocation("Dog*")); // Contains non-alphanumeric characters.
        assertFalse(Location.isValidLocation("yoursystemrunsf1234anyhowjustpresswhymylifesobadnow"
                + "IwanttowithdrawfromNUS")); // Contains 74 characters.


        // Valid location
        assertTrue(Location.isValidLocation("Hello ")); //Normal case
        assertTrue(Location.isValidLocation("Hello1234")); // Contains alphanumeric characters.
        assertTrue(Location.isValidLocation("hellomynameisJ ohn123an dIamA"
                + "Dog")); //Contains 30 alphanumeric char with spaces.
        assertTrue(Location.isValidLocation("yoursystemrunsf1234anyhow"
                + "justpress")); // Contains 34 characters.
    }

    @Test
    public void testToString() {

        assertEquals("Jurong", new Location("Jurong").toString());
        assertEquals("Changi Airport", new Location("Changi Airport").toString()); //Caps and non caps characters
        assertEquals("GBTB", new Location("GBTB").toString()); //Alphanumeric

        //Wrong toString
        assertNotEquals("Wrong", new Location("Hello").toString());
    }

    @Test
    public void testEquals() {
        //Equal Location Object
        assertEquals(new Location("Airport"), new Location("Airport")); // When two locations are the same.

        //Non Equal Description Object
        assertNotEquals(new Location("NTU"), new Location("NUS")); // When two locations are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new Location("Hashcode").hashCode(), new Location("Hashcode").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Location("hashcode").hashCode(), new Location("HashCode").hashCode());

    }
}
