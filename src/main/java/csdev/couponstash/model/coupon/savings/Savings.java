package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import csdev.couponstash.model.coupon.exceptions.InvalidSavingsException;

/**
 * Represents a certain amount of savings in CouponStash
 * that can be associated to a Coupon. Savings can either be
 * a monetary amount, a percentage of some original price, or
 * certain items that cannot be easily translatable to a
 * monetary value.
 *
 * <p>It must be either one of the three. It
 * can also be a combination of a monetary amount and certain
 * unquantifiable items, or a percentage amount and certain
 * unquantifiable items, but should not be both a monetary
 * amount and a percentage amount.
 *
 * <p>This is because coupons rarely offer both a monetary
 * value and a percentage value, for example you never see
 * (50% + $1) off for a box of chicken nuggets, but you only
 * see 50% off by itself, or $1 off by itself.
 */
public class Savings implements Comparable<Savings> {
    public static final String MESSAGE_CONSTRAINTS = "Savings should not be blank!";
    public static final String EMPTY_LIST_ERROR =
            "ERROR: Parser identified that this Savings should have"
            + "Saveables, but no Saveables received in class Savings";
    public static final String NUMBER_DETECTED_BUT_NOT_IN_FORMAT =
            "For savings, did you mean to specify a percentage amount (e.g. s/10%%)"
                    + " or a monetary amount (e.g. s/%1$s1.00)?";
    public static final String MULTIPLE_NUMBER_AMOUNTS =
            "Please only specify one numerical amount for savings.";

    // coupons could have a certain monetary value
    private final MonetaryAmount monetaryAmount;
    // coupons could have a certain percentage value
    private final PercentageAmount percentage;
    // coupons could offer some gifts which their
    // value is not easily quantifiable
    private final List<Saveable> saveables;

    /**
     * Constructor for a Savings value, given only
     * a MonetaryAmount representing the amount
     * in terms of some denomination of currency.
     * @param monetaryAmount MonetaryAmount representing
     *                       amount of money.
     */
    public Savings(MonetaryAmount monetaryAmount) {
        requireNonNull(monetaryAmount);
        this.monetaryAmount = monetaryAmount;
        this.percentage = null;
        this.saveables = null;
    }

    /**
     * Constructor for a Savings value, given only
     * a PercentageAmount representing the percentage
     * off original price of a certain product.
     * @param percentage PercentageAmount representing
     *                   percentage off.
     */
    public Savings(PercentageAmount percentage) {
        requireNonNull(percentage);
        this.monetaryAmount = null;
        this.percentage = percentage;
        this.saveables = null;
    }

    /**
     * Constructor for a Savings value, given only
     * certain Saveables that cannot be easily represented
     * in terms of numerical values
     * @param saveables The List of Saveables that are to be
     *                  associated with this Savings. This
     *                  List should have 1 item, at least.
     */
    public Savings(List<Saveable> saveables) {
        requireNonNull(saveables);
        checkArgument(isValidSaveablesList(saveables),
                Savings.EMPTY_LIST_ERROR);
        this.monetaryAmount = null;
        this.percentage = null;
        this.saveables = condenseSaveablesList(saveables);
    }

    /**
     * Constructor for a Savings value, given monetary
     * amount and other Saveables that cannot be easily
     * represented in terms of numerical values
     * @param monetaryAmount MonetaryAmount representing
     *                       amount of money.
     * @param saveables The List of Saveables that are to be
     *                  associated with this Savings. This
     *                  List should have 1 item, at least.
     */
    public Savings(MonetaryAmount monetaryAmount, List<Saveable> saveables) {
        requireAllNonNull(monetaryAmount, saveables);
        checkArgument(isValidSaveablesList(saveables),
                Savings.EMPTY_LIST_ERROR);
        this.monetaryAmount = monetaryAmount;
        this.percentage = null;
        this.saveables = condenseSaveablesList(saveables);
    }

    /**
     * Constructor for a Savings value, given percentage
     * amount and other Saveables that cannot be easily
     * represented in terms of numerical values
     * @param percentage PercentageAmount representing
     *                   percentage off.
     * @param saveables The List of Saveables that are to be
     *                  associated with this Savings. This
     *                  List should have 1 item, at least.
     */
    public Savings(PercentageAmount percentage, List<Saveable> saveables) {
        requireAllNonNull(percentage, saveables);
        checkArgument(isValidSaveablesList(saveables),
                Savings.EMPTY_LIST_ERROR);
        this.monetaryAmount = null;
        this.percentage = percentage;
        this.saveables = condenseSaveablesList(saveables);
    }

    /**
     * Cloning constructor for Savings. The internal
     * MonetaryAmount, PercentageAmount and Saveables
     * will not be cloned, as they are immutable. But
     * the Saveables List will be cloned. Assumes s is a
     * valid Savings that obeys the rules of Savings
     * (at least one field, does not have both monetary
     * amount and percentage amount)
     * @param s The Savings to be cloned.
     */
    public Savings(Savings s) {
        requireNonNull(s);
        this.monetaryAmount = s.monetaryAmount;
        this.percentage = s.percentage;
        this.saveables = s.saveables == null
                ? null
                : new ArrayList<Saveable>(s.saveables);
    }

    /**
     * Checks whether this Savings has a MonetaryAmount.
     * @return True, if this Savings has a MonetaryAmount.
     *     False, if it does not.
     */
    public boolean hasMonetaryAmount() {
        return this.monetaryAmount != null;
    }

    /**
     * Checks whether this Savings has a PercentageAmount.
     * @return True, if this Savings has a PercentageAmount.
     *     False, if it does not.
     */
    public boolean hasPercentageAmount() {
        return this.percentage != null;
    }

    /**
     * Checks whether this Savings has Saveables.
     * @return True, if this Savings has Saveables.
     *     False, if it does not.
     */
    public boolean hasSaveables() {
        return this.saveables != null;
    }

    public Optional<MonetaryAmount> getMonetaryAmount() {
        return Optional.ofNullable(this.monetaryAmount);
    }

    public Optional<PercentageAmount> getPercentageAmount() {
        return Optional.ofNullable(this.percentage);
    }

    public Optional<List<Saveable>> getSaveables() {
        return Optional.ofNullable(this.saveables);
    }

    /**
     * Make a new copy of current Savings
     * @return a copy of the current Savings
     */
    public Savings copy() {
        return new Savings(this);
    }

    @Override
    public int compareTo(Savings s) {
        if (this.hasMonetaryAmount() || s.hasMonetaryAmount()) {
            // first, compare monetary amount
            if (this.hasMonetaryAmount() && s.hasMonetaryAmount()) {
                return this.monetaryAmount.compareTo(s.monetaryAmount);
            } else {
                // prioritise the one with actual monetary amount
                return this.hasMonetaryAmount() ? 1 : -1;
            }
        } else if (this.hasPercentageAmount() || s.hasPercentageAmount()) {
            // next, compare percentage
            if (this.hasPercentageAmount() && s.hasPercentageAmount()) {
                return this.percentage.compareTo(s.percentage);
            } else {
                // prioritise the one with percentage
                return this.hasPercentageAmount() ? 1 : -1;
            }
        } else if (this.hasSaveables() || s.hasSaveables()) {
            // last resort, compare saveables list
            if (this.hasSaveables() && s.hasSaveables()) {
                return this.saveables.size() - s.saveables.size();
            } else {
                // error, either this or s has no information at all
                throw new InvalidSavingsException();
            }
        } else {
            // error, both this or s have no information at all
            throw new InvalidSavingsException();
        }
    }

    /**
     * Given a custom money symbol, represent this
     * Savings using that symbol, provided the savings
     * has some MonetaryAmount that will use that
     * custom money symbol.
     * @param symbol String representing Savings,
     *               with custom money symbol for
     *               exact monetary amounts.
     * @return Returns a String that represents this
     *     Savings, only with a custom money symbol.
     */
    public String getStringWithMoneySymbol(String symbol) {
        StringBuilder sb = new StringBuilder();
        if (!this.hasMonetaryAmount() && !this.hasPercentageAmount()) {
            sb.append("You get ");
        } else {
            sb.append("You save ");
            this.getMonetaryAmount().ifPresent(ma -> {
                sb.append(ma.getStringWithMoneySymbol(symbol));
                sb.append(", ");
            });
            this.getPercentageAmount().ifPresent(pc -> {
                sb.append(pc.toString());
                sb.append(", ");
            });
            this.getSaveables().ifPresent(s -> sb.append(" and you get "));
        }
        this.getSaveables().ifPresent(savedItems -> {
            for (Saveable sv : savedItems) {
                sb.append(sv.toString());
                sb.append(", ");
            }
        });
        // remove last comma and space (extra length 2)
        return sb.substring(0, sb.length() - 2);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Savings)) {
            return false;
        } else {
            Savings s = (Savings) o;
            if (this.getMonetaryAmount().equals(s.getMonetaryAmount())
                && this.getPercentageAmount().equals(s.getPercentageAmount())) {
                // don't take order of saveables list into account
                return compareSaveablesList(this.saveables, s.saveables);
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.monetaryAmount, this.percentage, this.saveables);
    }

    @Override
    public String toString() {
        // use $ symbol as default
        return this.getStringWithMoneySymbol("$");
    }

    /**
     * Utility function used by Savings.java to check if
     * the List&lt;Saveable&gt; is non-empty, and none
     * of the Saveables are empty Strings or null.
     * This should never return false, as if no Saveables
     * are given, the SavingsParser would have determined
     * that the command did not have any Saveables and
     * not passed a list to Savings.java in the first place.
     * @param list The list to be checked.
     * @return True, if the list is non-empty.
     *     False if the list is empty.
     */
    protected static boolean isValidSaveablesList(List<Saveable> list) {
        requireNonNull(list);
        return !list.isEmpty() && list.stream()
                .allMatch(sva -> Saveable.isValidSaveableValue(sva.getValue(), sva.getCount()));
    }

    /**
     * Given a Saveables list that possibly has duplicate items,
     * find these duplicate items and put them together instead,
     * increasing the count value of the item accordingly.
     *
     * @param list The original Saveables list.
     * @return A new Saveables list without any duplicates.
     */
    protected static List<Saveable> condenseSaveablesList(List<Saveable> list) {
        assert isValidSaveablesList(list);
        HashMap<String, Integer> nameToCountMap = new HashMap<String, Integer>();
        list.forEach(sva -> {
            nameToCountMap.merge(sva.getValue(), sva.getCount(), Integer::sum);
        });
        return nameToCountMap.entrySet().stream()
                .map(mapping -> new Saveable(mapping.getKey(), mapping.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Utility function to compare two Saveable Lists.
     * Returns true if the items are the same in list1
     * and list2, regardless of order of the items.
     *
     * @param list1 The first list to be compared.
     * @param list2 The second list to be compared.
     *
     * @return Returns true, if every Saveable in list1
     *         can be found in list2, and vice versa.
     *         Returns false otherwise (if some Saveable
     *         does not exist in both lists).
     */
    private static boolean compareSaveablesList(List<Saveable> list1, List<Saveable> list2) {
        if (list1 == null && list2 == null) {
            return true;
        } else if (list1 == null || list2 == null) {
            return false;
        } else {
            return list1.containsAll(list2) && list2.containsAll(list1);
        }
    }
}
