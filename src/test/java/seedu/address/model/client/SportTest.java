package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SportTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sport(null));
    }

    @Test
    public void constructor_invalidSport_throwsIllegalArgumentException() {
        String invalidSport = " ";
        assertThrows(IllegalArgumentException.class, () -> new Sport(invalidSport));
    }

    @Test
    public void isValidSport() {
        // null Sport
        assertThrows(NullPointerException.class, () -> Sport.isValidSport(null));

        // invalid sport
        assertFalse(Sport.isValidSport("")); // empty string
        assertFalse(Sport.isValidSport(" ")); // spaces only

        // valid sport
        assertTrue(Sport.isValidSport("-")); // no sport
        assertTrue(Sport.isValidSport(".")); // no sport
        assertTrue(Sport.isValidSport("NIL")); // no sport
        assertTrue(Sport.isValidSport("no sport")); // no sport
        assertTrue(Sport.isValidSport("hockey")); // one sport
        assertTrue(Sport.isValidSport("jengabuildingfreestyle81mountainskydivingswimveryfast")); // long sport
        assertTrue(Sport.isValidSport("81xtwelveriiasdfmnklanl cccoccunut")); // long weird sport
        assertTrue(Sport.isValidSport("Cross-country mountain biking")); // sport with "-" and numbers
    }

    @Test
    public void equals_validHeight() {
        Sport h1 = new Sport("100000m race");

        assertTrue(h1.equals(h1));
        assertTrue(h1.equals(new Sport("100000m race")));

        assertFalse(h1.equals(new Sport("100000mrace")));
    }

    @Test
    public void hashCode_validHeight() {
        Sport h1 = new Sport("100000m race");

        assertTrue(h1.hashCode() == new Sport("100000m race").hashCode());

        assertFalse(h1.hashCode() == new Sport("100000mrace").hashCode());
    }
}
