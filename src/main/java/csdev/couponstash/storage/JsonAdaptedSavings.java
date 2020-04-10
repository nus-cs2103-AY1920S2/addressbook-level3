package csdev.couponstash.storage;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

/**
 * Jackson-friendly version of {@link Savings}.
 */
public class JsonAdaptedSavings {
    private final JsonAdaptedMonetaryAmount monetaryAmounte;
    private final Double percentageAmount;
    private final List<JsonAdaptedSaveable> saveables;

    /**
     * Constructs a {@code JsonAdaptedSavings} with the given savings details.
     * Monetary amount integer and monetary amount decimal should both be
     * present or both be null. If either one is present but the other is
     * not, the Savings will not be loaded properly.
     *
     * @param jsma JsonAdaptedMonetaryAmount representing
     *             MonetaryAmount (could be null).
     * @param mai Integer representing monetary amount
     *            integer value (could be null);
     * @param mad Integer representing monetary amount
     *            decimal value (could be null);
     * @param ma Double representing MonetaryAmount. Warning: this property
     *           is depreciated, but still exists mainly to support old save
     *           files for Coupon Stash v1.3 and before.
     * @param pc Double representing percentage amount (could be null).
     * @param sva List of JsonAdaptedSaveables (could be null).
     */
    @JsonCreator
    public JsonAdaptedSavings(@JsonProperty("monetaryAmounte") JsonAdaptedMonetaryAmount jsma,
                              @Deprecated
                              @JsonProperty("monetaryAmountInteger") Integer mai,
                              @Deprecated
                              @JsonProperty("monetaryAmountDecimal") Integer mad,
                              @Deprecated
                              @JsonProperty("monetaryAmount") Double ma,
                              @JsonProperty("percentageAmount") Double pc,
                              @JsonProperty("saveables") List<JsonAdaptedSaveable> sva) {

        if (mai != null & mad != null) {
            this.monetaryAmounte = new JsonAdaptedMonetaryAmount(mai, mad);
        } else if (ma != null) {
            int intAmount = (int) Math.floor(ma); // get rid of decimal
            int decAmount = ((int) Math.round(ma * 100)) % 100;
            checkArgument(MonetaryAmount.isValidMonetaryAmount(intAmount, decAmount),
                    MonetaryAmount.MESSAGE_CONSTRAINTS);
            this.monetaryAmounte = new JsonAdaptedMonetaryAmount(intAmount, decAmount);
        } else {
            this.monetaryAmounte = jsma;
        }
        this.percentageAmount = pc;
        this.saveables = sva;
    }

    /**
     * Constructor for a JsonAdaptedSavings.
     * Note that Savings sv is assumed to be
     * a valid Savings that does not violate
     * rules like not having both monetary amount
     * and percentage amount, or having at least
     * one non-empty field.
     * @param sv The Savings to be used.
     */
    public JsonAdaptedSavings(Savings sv) {
        this.monetaryAmounte = sv.getMonetaryAmount().map(JsonAdaptedMonetaryAmount::new).orElse(null);
        this.percentageAmount = sv.getPercentageAmount().map(PercentageAmount::getValue).orElse(null);
        Function<List<Saveable>, List<JsonAdaptedSaveable>> mapToJson =
            svaList -> svaList.stream()
                    .map(JsonAdaptedSaveable::new)
                    .collect(Collectors.toList());
        this.saveables = sv.getSaveables().map(mapToJson).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted Savings object into
     * the Model's {@code Savings} object.
     *
     * @throws IllegalValueException If there were any data
     *     constraints violated in the adapted Savings (e.g.
     *     the JsonAdaptedSavings contains both a monetary
     *     amount and a percentage amount, or does not
     *     contain any fields at all).
     */
    public Savings toModelType() throws IllegalValueException {
        if (this.checkIfValuesInvalid()) {
            throw new IllegalValueException(Savings.MESSAGE_CONSTRAINTS);

        } else if (saveables != null) {

            List<Saveable> modelSaveables = new ArrayList<Saveable>();
            for (JsonAdaptedSaveable jsv : saveables) {
                modelSaveables.add(jsv.toModelType());
            }
            if (monetaryAmounte != null) {
                return new Savings(monetaryAmounte.toModelType(), modelSaveables);
            } else if (percentageAmount != null) {
                return new Savings(new PercentageAmount(percentageAmount), modelSaveables);
            } else {
                return new Savings(modelSaveables);
            }

        } else if (monetaryAmounte != null) {
            return new Savings(monetaryAmounte.toModelType());
        } else {
            return new Savings(new PercentageAmount(percentageAmount));
        }
    }

    /**
     * Checks if this JsonAdaptedSavings violates the
     * assumptions of a model Savings object.
     *
     * @return Returns true, if the values in this
     *         JsonAdaptedSavings would result in
     *         an invalid Savings object.
     */
    private boolean checkIfValuesInvalid() {
        // check if no values present at all in this Savings
        return (monetaryAmounte == null && percentageAmount == null && saveables == null)
                // check if both monetaryAmount and percentageAmount present
                || (monetaryAmounte != null && percentageAmount != null);
    }
}
