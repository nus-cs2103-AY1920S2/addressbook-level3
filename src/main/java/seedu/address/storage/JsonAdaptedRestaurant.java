package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Remark;
import seedu.address.model.restaurant.Restaurant;

/**
 * Jackson-friendly version of {@link Restaurant}
 */
class JsonAdaptedRestaurant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's %s field is missing!";

    private final String name;
    private final String location;
    private final String hours;
    private final String price;
    private final ArrayList<JsonAdaptedRemarkR> remark = new ArrayList<>();
    private final String cuisine;

    /**
     * Constructs a {@code JsonAdaptedRestaurant} with the given restaurant details.
     */
    @JsonCreator
    public JsonAdaptedRestaurant(@JsonProperty("name") String name, @JsonProperty("location") String location,
                                 @JsonProperty("hours") String hours, @JsonProperty("price") String price,
                                 @JsonProperty("remark") ArrayList<JsonAdaptedRemarkR> remark,
                                 @JsonProperty("cuisine") String cuisine,
                                 @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.location = location;
        this.hours = hours;
        this.price = price;
        this.cuisine = cuisine;
        if (remark != null) {
            this.remark.addAll(remark);
        }
    }

    /**
     * Converts a given {@code Restaurant} into this class for Jackson use.
     */
    public JsonAdaptedRestaurant(Restaurant source) {
        name = source.getName().fullName;
        location = source.getLocation().fullLocation;
        hours = source.getHours().hours;
        price = source.getPrice().price;
        cuisine = source.getCuisine().cuisine;
        remark.addAll(source.getRemark().stream()
                .map(JsonAdaptedRemarkR::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted restaurant object into the model's {@code Restaurant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant.
     */
    public Restaurant toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (hours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Hours.class.getSimpleName()));
        }
        if (!Hours.isValidHours(hours)) {
            throw new IllegalValueException(Hours.MESSAGE_CONSTRAINTS);
        }
        final Hours modelHours = new Hours(hours);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (cuisine == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cuisine.class.getSimpleName()));
        }
        if (!Cuisine.isValidCuisine(cuisine)) {
            throw new IllegalValueException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        final Cuisine modelCuisine = new Cuisine(cuisine);
        final ArrayList<Remark> modelRemark = new ArrayList<>();
        for (JsonAdaptedRemarkR r : remark) {
            modelRemark.add(r.toModelType());
        }
        return new Restaurant(modelName, modelLocation, modelHours, modelPrice, modelCuisine, modelRemark);
    }
}
