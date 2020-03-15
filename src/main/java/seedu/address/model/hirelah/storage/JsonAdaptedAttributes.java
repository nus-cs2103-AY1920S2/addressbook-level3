package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.Attribute;

/**
 * Jackson-friendly version of {@link Attribute}.
 */
class JsonAdaptedAttributes {
    private final String attribute;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAttributes (Attribute source) {
        attribute = source.toString();
    }
    /**
     * Converts this Jackson-friendly adapted Attribute object into the model's {@code Attribute} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attribute.
     */
    public Attribute toModelType() throws IllegalValueException {
        if (attribute == null) {
            throw new IllegalValueException("Invalid Attribute.");
        }
        return new Attribute(attribute);
    }
}
