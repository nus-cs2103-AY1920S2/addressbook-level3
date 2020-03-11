package csdev.couponstash.model.coupon.savings;

import java.util.List;
import java.util.Optional;

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
 *
 * <p>Guaranteed to be immutable.
 */
public class Savings implements Comparable<Savings> {
    // coupons could have a certain monetary value
    private final Optional<MonetaryAmount> monetaryAmount;
    // coupons could have a certain percentage value
    private final Optional<PercentageAmount> percentage;
    // coupons could offer some gifts which their
    // value is not easily quantifiable
    private final Optional<List<Saveable>> saveables;

    /**
     * Constructor for a Savings value, given only
     * a MonetaryAmount representing the amount
     * in terms of some denomination of currency.
     * @param monetaryAmount MonetaryAmount representing
     *                       amount of money.
     */
    public Savings(MonetaryAmount monetaryAmount) {
        this.monetaryAmount = Optional.of(monetaryAmount);
        this.percentage = Optional.empty();
        this.saveables = Optional.empty();
    }

    /**
     * Constructor for a Savings value, given only
     * a PercentageAmount representing the percentage
     * off original price of a certain product.
     * @param percentage PercentageAmount representing
     *                   percentage off.
     */
    public Savings(PercentageAmount percentage) {
        this.monetaryAmount = Optional.empty();
        this.percentage = Optional.of(percentage);
        this.saveables = Optional.empty();
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
        isNotEmptyList(saveables);
        this.monetaryAmount = Optional.empty();
        this.percentage = Optional.empty();
        this.saveables = Optional.of(saveables);
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
        isNotEmptyList(saveables);
        this.monetaryAmount = Optional.of(monetaryAmount);
        this.percentage = Optional.empty();
        this.saveables = Optional.of(saveables);
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
        isNotEmptyList(saveables);
        this.monetaryAmount = Optional.empty();
        this.percentage = Optional.of(percentage);
        this.saveables = Optional.of(saveables);
    }

    @Override
    public int compareTo(Savings s) {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "";
    }

    private <T> void isNotEmptyList(List<T> list) {

    }
}
