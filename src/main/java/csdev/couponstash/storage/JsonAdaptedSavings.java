package csdev.couponstash.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonAdaptedSavings {
    private final Double monetaryAmount;
    private final Double percentageAmount;
    private final List<JsonAdaptedSaveable> saveables;

    /**
     * Constructs a {@code JsonAdaptedSavings} with the given savings details.
     * @param ma Double representing monetary amount (could be null).
     * @param pc Double representing percentage amount (could be null).
     * @param sva List of JsonAdaptedSaveables (could be null).
     */
    @JsonCreator
    public JsonAdaptedSavings(@JsonProperty("monetaryAmount") Double ma,
                              @JsonProperty("percentageAmount") Double pc,
                              @JsonProperty("saveables") List<JsonAdaptedSaveable> sva) {

        this.monetaryAmount = ma;
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
        this.monetaryAmount = sv.getMonetaryAmount().map(MonetaryAmount::getValue).orElse(null);
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
        if ((monetaryAmount == null && percentageAmount == null && saveables == null)
                || (monetaryAmount != null && percentageAmount != null)) {

            throw new IllegalValueException(Savings.MESSAGE_CONSTRAINTS);

        } else if (saveables != null) {

            List<Saveable> modelSaveables = new ArrayList<Saveable>();
            for (JsonAdaptedSaveable jsv : saveables) {
                modelSaveables.add(jsv.toModelType());
            }
            if (monetaryAmount != null) {
                return new Savings(new MonetaryAmount(monetaryAmount), modelSaveables);
            } else if (percentageAmount != null) {
                return new Savings(new PercentageAmount(percentageAmount), modelSaveables);
            } else {
                return new Savings(modelSaveables);
            }

        } else if (monetaryAmount != null) {
            return new Savings(new MonetaryAmount(monetaryAmount));
        } else {
            return new Savings(new PercentageAmount(percentageAmount));
        }
    }
}
