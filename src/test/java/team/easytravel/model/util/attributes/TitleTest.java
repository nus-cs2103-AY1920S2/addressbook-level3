package team.easytravel.model.util.attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TitleTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructorInvalidTitleThrowsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }


    @Test
    public void isValidTitle() {

        // null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // blank title
        assertFalse(Title.isValidTitle("")); // Empty string
        assertFalse(Title.isValidTitle("  ")); // Multiple Spaces
        assertFalse(Title.isValidTitle(" ")); // Single space

        // invalid title
        assertFalse(Title.isValidTitle("^")); // Non-alphanumeric characters
        assertFalse(Title.isValidTitle("Dog*")); // Contains non-alphanumeric characters.
        assertFalse(Title.isValidTitle("yoursystemrunsf1234anyhowjustpresswhymylifesobadnow"
                + "IwanttowithdrawfromNUS")); // Contains 74 characters, returns false as title only accepts 50 char.

        // Valid title
        assertTrue(Title.isValidTitle("Hello ")); //Normal case
        assertTrue(Title.isValidTitle("Hello1234")); // Contains alphanumeric characters.
        assertTrue(Title.isValidTitle("hellomynameisJ ohn123an dIamA"
                + "Dog")); //Contains 30 alphanumeric char with spaces.
        assertTrue(Title.isValidTitle("yoursystemrunsf1234anyhow"
                + "justpress")); // Contains 34 characters.
    }

    @Test
    public void testToString() {

        assertEquals("1000", new Title("1000").toString());
        assertEquals("JurongWest", new Title("JurongWest").toString()); //Caps and non caps characters
        assertEquals("BishanPark", new Title("BishanPark").toString()); //Alphanumeric


        //Wrong toString
        assertNotEquals("Changi", new Title("Hong Kong").toString());

    }

    @Test
    public void testEquals() {
        //Equal Title Object
        assertEquals(new Title("Tampines"), new Title("Tampines")); // When two Titles are the same.

        //Non Equal Title Object
        assertNotEquals(new Title("AMK"), new Title("Dover")); // When two titles are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new Title("Hashcode").hashCode(), new Title("Hashcode").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Title("hashcode").hashCode(), new Title("HashCode").hashCode());
    }
}
