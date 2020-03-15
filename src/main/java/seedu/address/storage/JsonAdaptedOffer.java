package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.offer.Offer;

/**
 * Jackson-friendly version of {@link seedu.address.model.offer.Offer}.
 */
class JsonAdaptedOffer {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedOffer} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedOffer(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Offer} into this class for Jackson use.
     */
    public JsonAdaptedOffer(Offer source) {
        tagName = source.tagName;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Offer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Offer toModelType() throws IllegalValueException {
        if (!Offer.isValidTagName(tagName)) {
            throw new IllegalValueException(Offer.MESSAGE_CONSTRAINTS);
        }
        return new Offer(tagName);
    }

}
