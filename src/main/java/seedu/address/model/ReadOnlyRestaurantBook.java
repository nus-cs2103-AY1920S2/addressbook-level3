package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Restaurant;

/**
 * Unmodifiable view of the address book.
 */
public interface ReadOnlyRestaurantBook {

    /**
     * Returns an unmodifiable view of the restaurant book.
     * This list will not contain any duplicate restaurants.
     */
    ObservableList<Restaurant> getRestaurantsList();
}
