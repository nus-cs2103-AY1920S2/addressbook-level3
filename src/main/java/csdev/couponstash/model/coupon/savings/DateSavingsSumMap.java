package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.Map;

/**
 * A wrapper class for a HashMap, this class allows
 * multiple PureMonetarySavings to be associated with a
 * certain LocalDate by adding up the PureMonetarySavings
 * whenever the value already exists in the Map. Hence
 * putting new entries should always return null.
 */
public class DateSavingsSumMap extends HashMap<LocalDate, PureMonetarySavings> {

    /**
     * Associates the specified LocalDate with the specified
     * PureMonetarySavings in this DateSavingsSumMap. The old
     * savings value will never be replaced, but instead
     * DateSavingsSumMap will add the new value to that
     * PureMonetarySavings value to represent the sum
     * of savings on a certain date.
     *
     * @param ld LocalDate with which pms is to be associated.
     * @param pms PureMonetarySavings value to be associated
     *            with the specified ld.
     * @return The previous value associated with {@code ld}, or
     *         {@code null} if there was no mapping for {@code key}.
     */
    public PureMonetarySavings add(LocalDate ld, PureMonetarySavings pms) {
        requireAllNonNull(ld, pms);
        if (super.containsKey(ld)) {
            PureMonetarySavings oldValue = super.get(ld);
            return super.put(ld, oldValue.add(pms));
        } else {
            return super.put(ld, pms);
        }
    }

    /**
     * Adds all of the mappings from the specified map to this map.
     * The effect of this call is equivalent to that
     * of calling {@link #add(LocalDate, PureMonetarySavings) add(k, v)}
     * for every mapping of key {@code k} to value {@code v}.
     * PureMonetarySavings values for a certain date will never
     * be overwritten, but instead summed up to produce a new value.
     *
     * @param m The mappings to be stored in this DateSavingsSumMap.
     */
    public void addAll(Map<? extends LocalDate, ? extends PureMonetarySavings> m) {
        m.forEach(this::add);
    }
}
