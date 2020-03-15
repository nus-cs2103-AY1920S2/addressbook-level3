package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Offer;

/**
 * Jackson-friendly version of {@link seedu.address.model.tag.Offer}.
 */
class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Offer} into this class for Jackson use.
     */
    public JsonAdaptedTag(Offer source) {
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
