package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.restaurant.Restaurant;

/**
 * An Immutable RestaurantBook that is serializable to JSON format.
 */
@JsonRootName(value = "restaurantbook")
class JsonSerializableRestaurantBook {
    public static final String MESSAGE_DUPLICATE_PERSON = "Restaurants list contains duplicate restaurant(s).";

    private final List<JsonAdaptedRestaurant> restaurants = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRestaurantBook} with the given restaurants.
     */
    @JsonCreator
    public JsonSerializableRestaurantBook(@JsonProperty("restaurants") List<JsonAdaptedRestaurant> restaurants) {
        this.restaurants.addAll(restaurants);
    }

    /**
     * Converts a given {@code ReadOnlyRestaurantBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestaurantBook}.
     */
    public JsonSerializableRestaurantBook(ReadOnlyRestaurantBook source) {
        restaurants.addAll(source
                .getRestaurantsList()
                .stream()
                .map(JsonAdaptedRestaurant::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this restaurant book into the model's {@code RestaurantBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RestaurantBook toModelType() throws IllegalValueException {
        RestaurantBook restaurantBook = new RestaurantBook();
        for (JsonAdaptedRestaurant jsonAdaptedRestaurant : restaurants) {
            Restaurant restaurant = jsonAdaptedRestaurant.toModelType();
            if (restaurantBook.hasRestaurant(restaurant)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            restaurantBook.addRestaurant(restaurant);
        }
        return restaurantBook;
    }

}
