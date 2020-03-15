package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.good.Good;
import seedu.address.model.offer.Offer;
import seedu.address.model.offer.Price;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of {@link seedu.address.model.offer.Offer}.
 */
class JsonAdaptedOffer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Offer's %s field is missing!";

    private final String good;
    private final String price;

    /**
     * Constructs a {@code JsonAdaptedOffer} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedOffer(@JsonProperty("good") String good, @JsonProperty("price") String price) {
        this.good = good;
        this.price = price;
    }

    /**
     * Converts a given {@code Offer} into this class for Jackson use.
     */
    public JsonAdaptedOffer(Offer source) {
        good = source.getGood().toString();
        price = source.getPrice().getValue();
    }

    @JsonValue
    public String getGood() {
        return good;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Offer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Offer toModelType() throws IllegalValueException {
        if (good == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Good.class.getSimpleName()));
        }
        if (!Good.isValidGoodName(good)) {
            throw new IllegalValueException(Good.MESSAGE_CONSTRAINTS);
        }
        final Good modelGood = new Good(good);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        return new Offer(modelGood, modelPrice);
    }

}
