package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRestaurants.AMEENS;
import static seedu.address.testutil.TypicalRestaurants.KFC;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RestaurantBuilder;

public class RestaurantTest {

    @Test
    public void isSameRestaurant() {
        // same object -> returns true
        assertTrue(AMEENS.isSameRestaurant(AMEENS));

        // null -> returns false
        assertFalse(AMEENS.isSameRestaurant(null));

        // different name and location -> returns false
        Restaurant editedAmeens = new RestaurantBuilder(AMEENS).withLocation("Rubbish").build();
        assertFalse(AMEENS.isSameRestaurant(editedAmeens));

        // different name -> returns false
        editedAmeens = new RestaurantBuilder(AMEENS).withName("AAA").build();
        assertFalse(AMEENS.isSameRestaurant(editedAmeens));

        // same name, same location, different attributes -> returns true
        editedAmeens = new RestaurantBuilder(AMEENS).withName("Ameens").withLocation("Clementi").withVisit("No")
                .build();
        assertTrue(AMEENS.isSameRestaurant(editedAmeens));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Restaurant aliceCopy = new RestaurantBuilder(AMEENS).build();
        assertTrue(AMEENS.equals(aliceCopy));

        // same object -> returns true
        assertTrue(AMEENS.equals(AMEENS));

        // null -> returns false
        assertFalse(AMEENS.equals(null));

        // different type -> returns false
        assertFalse(AMEENS.equals(5));

        // different restaurant -> returns false
        assertFalse(AMEENS.equals(KFC));

        // different name -> returns false
        Restaurant editedAmeens = new RestaurantBuilder(AMEENS).withName("KFC").build();
        assertFalse(AMEENS.equals(editedAmeens));

        // different location -> returns false
        editedAmeens = new RestaurantBuilder(AMEENS).withLocation("Bedok").build();
        assertFalse(AMEENS.equals(editedAmeens));

        // different name -> returns false
        editedAmeens = new RestaurantBuilder(AMEENS).withName("Ameens2").build();
        assertFalse(AMEENS.equals(editedAmeens));

        // different price -> returns false
        editedAmeens = new RestaurantBuilder(AMEENS).withPrice("$$$").build();
        assertFalse(AMEENS.equals(editedAmeens));

        // different cuisine -> returns false
        editedAmeens = new RestaurantBuilder(AMEENS).withCuisine("Jap").build();
        assertFalse(AMEENS.equals(editedAmeens));

        // different hours -> returns false
        editedAmeens = new RestaurantBuilder(AMEENS).withHours("1234:2345").build();
        assertFalse(AMEENS.equals(editedAmeens));
    }
}
