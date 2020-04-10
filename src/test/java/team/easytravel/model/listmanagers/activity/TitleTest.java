package team.easytravel.model.listmanagers.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.model.util.attributes.Title;
import team.easytravel.testutil.Assert;

class TitleTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructorInvalidTitleThrowsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }


    @Test
    public void isValidTitle() {

        // null title
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // blank title
        assertFalse(Title.isValidTitle("")); // Empty string
        assertFalse(Title.isValidTitle(" ")); // Spaces only
        assertFalse(Title.isValidTitle("  ")); // Multiple spaces

        // invalid title
        assertFalse(Title.isValidTitle("^")); // Non-alphanumeric characters
        assertFalse(Title.isValidTitle("Dog*")); // Contains non-alphanumeric characters.
        assertFalse(Title.isValidTitle("yoursystemrunsf1234anyhowjustpresswhymylifesobadnow"
                + "IwanttowithdrawfromNUSIstolethisfromCATHAHAHAHHAH")); // Contains 74 characters.

        // Valid title
        assertTrue(Title.isValidTitle("someTitle ")); //Normal case
        assertTrue(Title.isValidTitle("title1234")); // Contains alphanumeric characters.
        assertTrue(Title.isValidTitle("hellomynameisJ ohnCE"
                + "NA hahahahahhacsgohehelaaldota")); //Contains 50 alphanumeric char with spaces.
        assertTrue(Title.isValidTitle("yoursystemrunsf1234anyhow"
                + "justpress")); // Contains 34 characters.


    }

    @Test
    public void testToString() {

        assertEquals("1000", new Title("1000").toString());
        assertEquals("DogLand", new Title("DogLand").toString()); //Caps and non caps characters
        assertEquals("1000MoneyGimme", new Title("1000MoneyGimme").toString()); //Alphanumeric


        //Wrong toString
        assertNotEquals("Wrong", new Title("Stupid").toString());

    }

    @Test
    public void testEquals() {
        //Equal Description Object
        assertEquals(new Title("10Nights"), new Title("10Nights")); // When two titles are the same.

        //Non Equal Description Object
        assertNotEquals(new Title("10Nights"), new Title("20Nights")); // When two titles are different.

    }

    @Test
    public void testHashCode() {

        // Equal Hashcode
        assertEquals(new Title("Hashcode").hashCode(), new Title("Hashcode").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Title("hashcode").hashCode(), new Title("HashCode").hashCode());


    }
}
