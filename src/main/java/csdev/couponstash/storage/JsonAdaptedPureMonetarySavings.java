package csdev.couponstash.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;

/**
 * Jackson-friendly version of {@link PureMonetarySavings}.
 * PureMonetarySavings is used to represent total savings
 * earned from using a certain Coupon, in contrast to
 * Savings which represents the savings for each use.
 * Hence, this class has to be saved to the file
 * separately from Savings.
 */
public class JsonAdaptedPureMonetarySavings {
    private final JsonAdaptedMonetaryAmount monetaryAmount;
    private final List<JsonAdaptedSaveable> saveables;

    /**
     * Constructs a {@code JsonAdaptedPureMonetarySavings} with the given savings details.
     * At least one field should be non-null.
     *
     * @param jsma JsonAdaptedMonetaryAmount representing
     *             MonetaryAmount (could be null).
     * @param ma Double representing MonetaryAmount. Warning: this property
     *           is depreciated, but still exists mainly to support old save
     *           files for Coupon Stash v1.3 and before.
     * @param sva List of JsonAdaptedSaveables (could be null).
     */
    @JsonCreator
    public JsonAdaptedPureMonetarySavings(@JsonProperty("monetaryAmount") JsonAdaptedMonetaryAmount jsma,
                                          @JsonProperty("saveables") List<JsonAdaptedSaveable> sva) {

        this.monetaryAmount = jsma;
        this.saveables = sva;
    }

    /**
     * Constructor for a JsonAdaptedPureMonetarySavings.
     * Note that PureMonetarySavings pms is assumed to be
     * a valid PureMonetarySavings.
     * @param pms The PureMonetarySavings to be used.
     */
    public JsonAdaptedPureMonetarySavings(PureMonetarySavings pms) {
        this.monetaryAmount = pms.getMonetaryAmount().map(JsonAdaptedMonetaryAmount::new).orElse(null);
        Function<List<Saveable>, List<JsonAdaptedSaveable>> mapToJson =
            svaList -> svaList.stream()
                    .map(JsonAdaptedSaveable::new)
                    .collect(Collectors.toList());
        this.saveables = pms.getSaveables().map(mapToJson).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted PureMonetarySavings
     * object into the Model's {@code PureMonetarySavings} object.
     *
     * @throws IllegalValueException If there were any data
     *     constraints violated in the adapted PureMonetarySavings.
     */
    public PureMonetarySavings toModelType() throws IllegalValueException {
        if (this.monetaryAmount == null) {
            throw new IllegalValueException(PureMonetarySavings.MESSAGE_CONSTRAINTS);
        }

        List<Saveable> modelSaveables = new ArrayList<Saveable>();
        if (saveables != null) {
            for (JsonAdaptedSaveable jsv : saveables) {
                modelSaveables.add(jsv.toModelType());
            }
        }

        if (modelSaveables.isEmpty()) {
            return new PureMonetarySavings(monetaryAmount.toModelType());
        } else {
            return new PureMonetarySavings(monetaryAmount.toModelType(), modelSaveables);
        }
    }
}
