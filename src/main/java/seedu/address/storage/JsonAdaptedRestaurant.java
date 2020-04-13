package seedu.address.storage;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Visit;

/**
 * Jackson-friendly version of {@link Restaurant}
 */
class JsonAdaptedRestaurant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's %s field is missing!";

    private final String name;
    private final String location;
    private final String hours;
    private final String price;
    private final String cuisine;
    private final String visit;
    private final ArrayList<JsonAdaptedNote> recommendedNote = new ArrayList<>();
    private final ArrayList<JsonAdaptedNote> goodNote = new ArrayList<>();
    private final ArrayList<JsonAdaptedNote> badNote = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRestaurant} with the given restaurant details.
     */
    @JsonCreator
    public JsonAdaptedRestaurant(@JsonProperty("name") String name, @JsonProperty("location") String location,
                                 @JsonProperty("hours") String hours, @JsonProperty("price") String price,
                                 @JsonProperty("cuisine") String cuisine,
                                 @JsonProperty("visit") String visit,
                                 @JsonProperty("recommendedNotes") ArrayList<JsonAdaptedNote> recommendedNotes,
                                 @JsonProperty("goodNotes") ArrayList<JsonAdaptedNote> goodNotes,
                                 @JsonProperty("badNotes") ArrayList<JsonAdaptedNote> badNotes) {
        this.name = name;
        this.location = location;
        this.hours = hours;
        this.price = price;
        this.cuisine = cuisine;

        this.visit = visit;
        if (recommendedNotes != null) {
            this.recommendedNote.addAll(recommendedNotes);
        }
        if (goodNotes != null) {
            this.goodNote.addAll(goodNotes);
        }
        if (badNotes != null) {
            this.badNote.addAll(badNotes);
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
        visit = source.getVisit().visit;
        recommendedNote.addAll(source.getRecommendedFood().stream()
                .map(JsonAdaptedNote::new)
                .collect(Collectors.toList()));
        goodNote.addAll(source.getGoodFood().stream()
                .map(JsonAdaptedNote::new)
                .collect(Collectors.toList()));
        badNote.addAll(source.getBadFood().stream()
                .map(JsonAdaptedNote::new)
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

        if (visit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Visit.class.getSimpleName()));
        }
        if (!Visit.isValidVisit(visit)) {
            throw new IllegalValueException(Visit.MESSAGE_CONSTRAINTS);
        }
        final Visit modelVisit = new Visit(visit);
        final ArrayList<Note> modelRecommendedNote = new ArrayList<>();
        for (JsonAdaptedNote rnote : recommendedNote) {
            modelRecommendedNote.add(rnote.toModelType());
        }
        final ArrayList<Note> modelGoodNote = new ArrayList<>();
        for (JsonAdaptedNote gnote : goodNote) {
            modelGoodNote.add(gnote.toModelType());
        }
        final ArrayList<Note> modelBadNote = new ArrayList<>();
        for (JsonAdaptedNote bnote : badNote) {
            modelBadNote.add(bnote.toModelType());
        }
        return new Restaurant(modelName, modelLocation, modelHours, modelPrice, modelCuisine, modelVisit,
                modelRecommendedNote, modelGoodNote, modelBadNote);
    }
}
