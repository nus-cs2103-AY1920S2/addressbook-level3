package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Remark;

/**
 * Jackson-friendly version of {@link Remark}.
 */
class JsonAdaptedRemarkR {

    private final String remarkName;

    /**
     * Constructs a {@code JsonAdaptedRemark} with the given {@code remarkName}.
     */
    @JsonCreator
    public JsonAdaptedRemarkR(String remarkName) {
        this.remarkName = remarkName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedRemarkR(Remark source) {
        remarkName = source.value;
    }

    @JsonValue
    public String getRemarkName() {
        return remarkName;
    }

    /**
     * Converts this Jackson-friendly adapted remark object into the model's {@code Remark} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark.
     */
    public Remark toModelType() {
        return new Remark(remarkName);
    }

}
