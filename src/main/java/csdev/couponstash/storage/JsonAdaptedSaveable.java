package csdev.couponstash.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.coupon.savings.Saveable;

/**
 * Jackson-friendly version of {@link Saveable}.
 */
public class JsonAdaptedSaveable {
    private final String saveableDesc;
    private final int count;

    /**
     * Constructs a {@code JsonAdaptedSaveable} with the
     * given {@code saveableDesc}.
     *
     * <p>Note that String saveableDesc should be
     * non-null and non-empty, even though this
     * is not actually checked by the constructor.
     * Int count should also be greater than zero.
     *
     * <p>This will be checked when trying to convert
     * JsonAdaptedSaveables to a Model Saveables,
     * however.
     *
     * @param saveableDesc The String to be used.
     * @param count Int representing count of the item.
     */
    @JsonCreator
    public JsonAdaptedSaveable(@JsonProperty("saveableDesc") String saveableDesc,
                               @JsonProperty("count") int count) {
        this.saveableDesc = saveableDesc;
        this.count = count;
    }

    /**
     * Constructor for a JsonAdaptedSaveable.
     * Note that Saveable sva is assumed to be
     * a valid Saveable (with non-empty String
     * and positive non-zero count)
     * @param sva The Saveable to be used.
     */
    public JsonAdaptedSaveable(Saveable sva) {
        this.saveableDesc = sva.getValue();
        this.count = sva.getCount();
    }

    /**
     * Converts this Jackson-friendly adapted Saveable object into
     * the Model's {@code Saveable} object.
     *
     * @throws IllegalValueException If there were any data
     *     constraints violated in the adapted Saveable. The
     *     most likely error would result from an empty String
     *     as Saveables should not be empty Strings.
     */
    public Saveable toModelType() throws IllegalValueException {
        if (!Saveable.isValidSaveableValue(this.saveableDesc, this.count)) {
            throw new IllegalValueException(Saveable.MESSAGE_CONSTRAINTS);
        }
        return new Saveable(this.saveableDesc, this.count);
    }
}
