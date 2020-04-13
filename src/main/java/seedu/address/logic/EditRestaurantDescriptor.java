package seedu.address.logic;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Visit;

/**
 * Stores the details to edit the restaurant with. Each non-empty field value will replace the
 * corresponding field value of the restaurant.
 */
public class EditRestaurantDescriptor {
    private Name name;
    private Location location;
    private Visit visit;
    private Hours hours;
    private Cuisine cuisine;
    private Price price;

    public EditRestaurantDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditRestaurantDescriptor(EditRestaurantDescriptor toCopy) {
        setName(toCopy.name);
        setLocation(toCopy.location);
        setVisit(toCopy.visit);
        setHours(toCopy.hours);
        setCuisine(toCopy.cuisine);
        setPrice(toCopy.price);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, location, visit, hours, cuisine, price);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Optional<Visit> getVisit() {
        return Optional.ofNullable(visit);
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public Optional<Hours> getHours() {
        return Optional.ofNullable(hours);
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public Optional<Cuisine> getCuisine() {
        return Optional.ofNullable(cuisine);
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Optional<Price> getPrice() {
        return Optional.ofNullable(price);
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    public Restaurant createEditedRestaurant(Restaurant restaurantToEdit) {
        assert restaurantToEdit != null;

        Name updatedName = getName().orElse(restaurantToEdit.getName());
        Location updatedLocation = getLocation().orElse(restaurantToEdit.getLocation());
        Visit updatedVisit = getVisit().orElse(restaurantToEdit.getVisit());
        Hours updatedHours = getHours().orElse(restaurantToEdit.getHours());
        Cuisine updatedCuisine = getCuisine().orElse(restaurantToEdit.getCuisine());
        Price updatedPrice = getPrice().orElse(restaurantToEdit.getPrice());
        ArrayList<Note> updatedRecFood = restaurantToEdit.getRecommendedFood();
        ArrayList<Note> updatedGoodFood = restaurantToEdit.getGoodFood();
        ArrayList<Note> updatedBadFood = restaurantToEdit.getBadFood();

        return new Restaurant(updatedName, updatedLocation, updatedHours, updatedPrice, updatedCuisine,
                updatedVisit, updatedRecFood, updatedGoodFood, updatedBadFood);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRestaurantDescriptor)) {
            return false;
        }

        // state check
        EditRestaurantDescriptor e = (EditRestaurantDescriptor) other;

        return getName().equals(e.getName())
                && getLocation().equals(e.getLocation())
                && getVisit().equals(e.getVisit())
                && getHours().equals(e.getHours())
                && getCuisine().equals(e.getCuisine())
                && getPrice().equals(e.getPrice());
    }
}
