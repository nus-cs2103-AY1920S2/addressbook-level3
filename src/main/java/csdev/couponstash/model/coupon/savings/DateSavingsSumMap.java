package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper class for a HashMap, this class allows
 * multiple PureMonetarySavings to be associated with a
 * certain LocalDate by adding up the PureMonetarySavings
 * whenever the value already exists in the Map. Hence
 * putting new entries should always return null.
 *
 * <p>WARNING: This class is mutable, just like HashMap!
 */
public class DateSavingsSumMap extends HashMap<LocalDate, PureMonetarySavings> {

    /**
     * Constructs a new empty DateSavingsSumMap.
     */
    public DateSavingsSumMap() {
        super();
    }

    /**
     * Constructs a DateSavingsSumMap with a single entry,
     * given the key and value that make up this entry.
     * @param ld LocalDate that acts as the key of the first entry.
     * @param pms PureMonetarySavings that acts as the
     *            associated value for this entry.
     */
    public DateSavingsSumMap(LocalDate ld, PureMonetarySavings pms) {
        super();
        this.add(ld, pms);
    }

    /**
     * Private constructor facilitating copy of HashMap
     * @param map current map DateSavingsSumMapToCopy
     */
    private DateSavingsSumMap(DateSavingsSumMap map) {
        super();
        for (LocalDate key : map.keySet()) {
            this.add(key, map.get(key).copy());
        }
    }

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
        requireNonNull(m);
        m.forEach(this::add);
    }

    /**
     * Copies current DateSavingsSumMap
     * @return copy of current DateSavingsSumMap
     */
    public DateSavingsSumMap copy() {
        return new DateSavingsSumMap(this);
    }
}
