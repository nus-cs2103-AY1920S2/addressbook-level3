package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.person.Remark;

/**
 * Jackson-friendly version of {@link Remark}.
 */
class JsonAdaptedRemark {

    private final String remarkName;

    /**
     * Constructs a {@code JsonAdaptedRemark} with the given {@code remarkName}.
     */
    @JsonCreator
    public JsonAdaptedRemark(String remarkName) {
        this.remarkName = remarkName;
    }

    /**
     * Converts a given {@code Remark} into this class for Jackson use.
     */
    public JsonAdaptedRemark(Remark source) {
        remarkName = source.value;
    }

    @JsonValue
    public String getRemarkName() {
        return remarkName;
    }

    /**
     * Converts this Jackson-friendly adapted remark object into the model's {@code Remark} object.
     *
     */
    public Remark toModelType() {
        return new Remark(remarkName);
    }

}
