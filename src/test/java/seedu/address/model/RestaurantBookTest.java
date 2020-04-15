package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRestaurants.AMEENS;
import static seedu.address.testutil.TypicalRestaurants.getTypicalRestaurantBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.exceptions.DuplicateRestaurantException;
import seedu.address.testutil.RestaurantBuilder;

public class RestaurantBookTest {

    private final RestaurantBook restaurantBook = new RestaurantBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), restaurantBook.getRestaurantsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> restaurantBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyRestaurantBook_replacesData() {
        RestaurantBook newData = getTypicalRestaurantBook();
        restaurantBook.resetData(newData);
        assertEquals(newData, restaurantBook);
    }

    @Test
    public void resetData_withDuplicateRestaurants_throwsDuplicateRestaurantException() {
        // Two restaurants with the same identity fields
        Restaurant editedRestaurant = new RestaurantBuilder(AMEENS).withPrice("$$").build();
        List<Restaurant> newRestaurants = Arrays.asList(AMEENS, editedRestaurant);
        RestaurantBookStub newData = new RestaurantBookStub(newRestaurants);

        assertThrows(DuplicateRestaurantException.class, () -> restaurantBook.resetData(newData));
    }

    @Test
    public void hasRestaurant_nullRestaurant_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> restaurantBook.hasRestaurant(null));
    }

    @Test
    public void hasRestaurant_restaurantNotInRestaurantBook_returnsFalse() {
        assertFalse(restaurantBook.hasRestaurant(AMEENS));
    }

    @Test
    public void hasRestaurant_restaurantInRestaurantBook_returnsTrue() {
        restaurantBook.addRestaurant(AMEENS);
        assertTrue(restaurantBook.hasRestaurant(AMEENS));
    }

    @Test
    public void hasRestaurant_restaurantWithSameIdentityFieldsInRestaurantBook_returnsTrue() {
        restaurantBook.addRestaurant(AMEENS);
        Restaurant editedRestaurant = new RestaurantBuilder(AMEENS).withPrice("$$").build();
        assertTrue(restaurantBook.hasRestaurant(editedRestaurant));
    }

    @Test
    public void getRestaurantList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> restaurantBook.getRestaurantsList().remove(0));
    }

    /**
     * A stub ReadOnlyRestaurantBook whose restaurants list can violate interface constraints.
     */
    private static class RestaurantBookStub implements ReadOnlyRestaurantBook {
        private final ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();

        RestaurantBookStub(Collection<Restaurant> restaurants) {
            this.restaurants.setAll(restaurants);
        }

        @Override
        public ObservableList<Restaurant> getRestaurantsList() {
            return restaurants;
        }
    }

}
