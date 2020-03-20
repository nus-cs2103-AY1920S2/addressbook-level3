package csdev.couponstash.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.commons.util.DateFormatUtil;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;

/**
 * Jackson-friendly version of {@link DateSavingsSumMap}.
 * DateSavingsSumMap is used to store earned savings
 * information for Coupons, specifically which savings
 * were accumulated on a given date.
 */
public class JsonAdaptedDssm {
    private final List<JsonAdaptedDateSavingsPair> mappings = new ArrayList<JsonAdaptedDateSavingsPair>();

    public JsonAdaptedDssm(@JsonProperty("mappings") List<JsonAdaptedDateSavingsPair> mappings) {
        if (mappings != null) {
            this.mappings.addAll(mappings);
        }
    }

    public JsonAdaptedDssm(DateSavingsSumMap dssm) {
        dssm.forEach((ld, pms) -> mappings.add(new JsonAdaptedDateSavingsPair(ld, pms)));
    }

    public DateSavingsSumMap toModelType() {
        DateSavingsSumMap modelDssm = new DateSavingsSumMap();
        this.mappings.forEach(
                jdsp -> {
                    try {
                        modelDssm.add(jdsp.getDate(), jdsp.getSavings());
                    } catch (IllegalValueException e) {
                        // ignore invalid pairs
                        e.printStackTrace();
                    }
                });
        return modelDssm;
    }

    /**
     * This class is used to represent a key-value
     * mapping for the JsonAdaptedDssm, specifically
     * a mapping from LocalDate to PureMonetarySavings.
     */
    public static class JsonAdaptedDateSavingsPair {
        private final String date;
        private final JsonAdaptedPureMonetarySavings savings;

        public JsonAdaptedDateSavingsPair(@JsonProperty("date") String date,
                                          @JsonProperty("savings") JsonAdaptedPureMonetarySavings savings) {
            this.date = date;
            this.savings = savings;
        }

        public JsonAdaptedDateSavingsPair(LocalDate ld, PureMonetarySavings pms) {
            this.date = DateFormatUtil.formatDate(ld);
            this.savings = new JsonAdaptedPureMonetarySavings(pms);
        }

        public LocalDate getDate() {
            return DateFormatUtil.parseString(date);
        }

        public PureMonetarySavings getSavings() throws IllegalValueException {
            return savings.toModelType();
        }
    }
}
