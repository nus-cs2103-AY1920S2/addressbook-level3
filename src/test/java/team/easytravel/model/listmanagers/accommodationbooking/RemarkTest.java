package team.easytravel.model.listmanagers.accommodationbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RemarkTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructorInvalidRemarkThrowsIllegalArgumentException() {
        String invalidRemark = "";
        assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // blank remark
        assertFalse(Remark.isValidRemark("")); // Empty string
        // assertFalse(Remark.isValidRemark("  ")); // Multiple Spaces
        // assertFalse(Remark.isValidRemark(" ")); // Single space

        // invalid remark
        assertFalse(Remark.isValidRemark("yoursystemrunsf1234anyhowjustpresswhymylifesobadnow"
                + "IwanttowithdrawfromNUSWhyYouwanttoputupto150characters"
                + "MakemylifesodifficultwhydidijoinnusSPwassomuchfun")); // Contains 154 characters.


        // Valid remark
        assertTrue(Remark.isValidRemark("Hello ")); //Normal case
        assertTrue(Remark.isValidRemark("^")); // Non-alphanumeric characters
        assertTrue(Remark.isValidRemark("Dog*")); // Contains non-alphanumeric characters.
        assertTrue(Remark.isValidRemark("Hello1234")); // Contains alphanumeric characters.
        assertTrue(Remark.isValidRemark("hellomynameisJ ohn123an dIamA"
                + "Dog")); //Contains 30 alphanumeric char with spaces.
        assertTrue(Remark.isValidRemark("yoursystemrunsf1234anyhow"
                + "justpress")); // Contains 34 characters.
    }

    @Test
    public void testToString() {
        assertEquals("1000", new Remark("1000").toString());
        assertEquals("HelloImaDog", new Remark("HelloImaDog").toString()); //Caps and non caps characters
        assertEquals("1000Money", new Remark("1000Money").toString()); //Alphanumeric

        //Wrong toString
        assertNotEquals("Wrong", new Remark("Hello").toString());
    }

    @Test
    public void testEquals() {
        //Equal Remark Object
        assertEquals(new Remark("Food"), new Remark("Food")); // When two remark are the same.

        //Non Equal Remark Object
        assertNotEquals(new Remark("Foody"), new Remark("Food")); // When two remark are different.

    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new Remark("Hashcode").hashCode(), new Remark("Hashcode").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Remark("hashcode").hashCode(), new Remark("HashCode").hashCode());

    }
}
