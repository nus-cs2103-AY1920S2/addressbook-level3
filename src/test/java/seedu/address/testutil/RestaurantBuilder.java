package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Visit;

/**
 * A utility class to help with building {@code Restaurant} objects.
 */
public class RestaurantBuilder {
    public static final String DEFAULT_NAME = "Ameens";
    public static final String DEFAULT_LOCATION = "Clementi";
    public static final String DEFAULT_HOURS = "0900:2300";
    public static final String DEFAULT_PRICE = "$";
    public static final String DEFAULT_CUISINE = "Indian";
    public static final String DEFAULT_VISIT = "Yes";
    public static final String DEFAULT_RECOMMENDED = "Butter chicken";
    public static final String DEFAULT_GOOD = "Cheese Fries";
    public static final String DEFAULT_BAD = "Fried rice";

    private Name name;
    private Location location;
    private Hours hours;
    private Price price;
    private Cuisine cuisine;
    private Visit visit;
    private ArrayList<Note> recommendedFood;
    private ArrayList<Note> goodFood;
    private ArrayList<Note> badFood;

    /**
     * Initialises RestaurantBuilder with the data of {@Restaurant toCopy}.
     */
    public RestaurantBuilder(Restaurant toCopy) {
        this.name = toCopy.getName();
        this.location = toCopy.getLocation();
        this.hours = toCopy.getHours();
        this.price = toCopy.getPrice();
        this.cuisine = toCopy.getCuisine();
        this.visit = toCopy.getVisit();
        this.recommendedFood = toCopy.getRecommendedFood();
        this.goodFood = toCopy.getGoodFood();
        this.badFood = toCopy.getBadFood();
    }

    public RestaurantBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.location = new Location(DEFAULT_LOCATION);
        this.hours = new Hours(DEFAULT_HOURS);
        this.price = new Price(DEFAULT_PRICE);
        this.cuisine = new Cuisine(DEFAULT_CUISINE);
        this.visit = new Visit(DEFAULT_VISIT);
        this.recommendedFood = new ArrayList<>();
        recommendedFood.add(new Note(DEFAULT_RECOMMENDED));
        this.goodFood = new ArrayList<>();
        goodFood.add(new Note(DEFAULT_GOOD));
        this.badFood = new ArrayList<>();
        badFood.add(new Note(DEFAULT_BAD));
    }

    /**
     * Sets the {@Name} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@Location} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@Hours} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withHours(String hours) {
        this.hours = new Hours(hours);
        return this;
    }

    /**
     * Sets the {@Price} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@Cuisine} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withCuisine(String cuisine) {
        this.cuisine = new Cuisine(cuisine);
        return this;
    }

    /**
     * Sets the {@Visit} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withVisit(String visit) {
        this.visit = new Visit(visit);
        return this;
    }

    /**
     * Sets the {@Notes} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withRecommended(String recommendedFood) {
        this.recommendedFood.add(new Note(recommendedFood));
        return this;
    }

    /**
     * Sets the {@Notes} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withGood(String goodFood) {
        this.goodFood.add(new Note(goodFood));
        return this;
    }

    /**
     * Sets the {@Notes} of the {@Restaurant} that we are building.
     */
    public RestaurantBuilder withBad(String badFood) {
        this.badFood.add(new Note(badFood));
        return this;
    }

    /**
     * Builds the restaurant
     * @return restaurant that is created
     */
    public Restaurant build() {
        return new Restaurant(name, location, hours, price, cuisine, visit,
                recommendedFood, goodFood, badFood);
    }
}
