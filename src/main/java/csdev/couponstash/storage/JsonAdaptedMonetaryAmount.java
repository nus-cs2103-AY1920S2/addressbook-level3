package csdev.couponstash.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;

/**
 * Jackson-friendly version of {@link MonetaryAmount}.
 */
public class JsonAdaptedMonetaryAmount {
    private final Integer integer;
    private final Integer decimal;

    /**
     * Constructs a {@code JsonAdaptedMonetaryAmount} with the
     * given integer and decimal values.
     *
     * @param intValue Integer representing monetary amount
     *                 integer amount (full units of currency).
     * @param decValue Integer representing monetary amount
     *                 decimal amount (partial units of currency).
     */
    @JsonCreator
    public JsonAdaptedMonetaryAmount(@JsonProperty("integer") Integer intValue,
                                     @JsonProperty("decimal") Integer decValue) {

        assert (intValue != null && decValue != null) || (intValue == null && decValue == null)
                : "Monetary value missing integer or decimal field";
        this.integer = intValue;
        this.decimal = decValue;
    }

    /**
     * Constructs a {@code JsonAdaptedMonetaryAmount} from
     * the given model MonetaryAmount. Assumes that the given
     * MonetaryAmount is a valid MonetaryAmount.
     *
     * @param ma The MonetaryAmount to be saved in
     *           the JSON file.
     */
    public JsonAdaptedMonetaryAmount(MonetaryAmount ma) {
        this.integer = ma.getRawIntegerValue();
        this.decimal = ma.getRawDecimalValue();
    }

    /**
     * Converts this Jackson-friendly adapted MonetaryAmount
     * object into the Model's {@code MonetaryAmount} object.
     *
     * @throws IllegalValueException If there were any data
     *     constraints violated in the adapted MonetaryAmount.
     */
    public MonetaryAmount toModelType() throws IllegalValueException {
        if (!MonetaryAmount.isValidMonetaryAmount(this.integer, this.decimal)) {
            throw new IllegalValueException(MonetaryAmount.MESSAGE_CONSTRAINTS);
        }
        return new MonetaryAmount(this.integer, this.decimal);
    }
}
