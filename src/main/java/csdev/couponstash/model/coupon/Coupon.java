package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Represents a Coupon in the CouponStash.
 * Guarantees: details are present and not null, field values are validated.
 * All fields are immutable except for total savings.
 */
public class Coupon {

    // Identity fields
    // ("immutable" properties of Coupon that will never
    // change unless edited)
    private final Name name;
    private final PromoCode promoCode;
    private final ExpiryDate expiryDate;
    private final StartDate startDate;
    private final Savings savingsForEachUse;
    private final Set<Tag> tags = new HashSet<>();
    private final Limit limit;
    private final Condition condition;
    private final RemindDate remind;

    // Data fields
    // ("mutable" properties of Coupon that will change,
    // for implementation of certain commands)
    private final Usage usage;
    private final DateSavingsSumMap totalSavings;
    private final Archived archived;


    /**
     * Standard constructor for a new Coupon (when
     * a Coupon is added for the first time, with 0
     * total savings, no reminder, and not archived).
     * Every field must be present and not null.
     * @param name The Name of this Coupon.
     * @param promoCode Promo code for this Coupon.
     * @param savingsForEachUse How much Savings saved when this Coupon is used.
     * @param expiryDate The ExpiryDate for this Coupon.
     * @param startDate The StartDate for this Coupon.
     * @param usage The Usage for this Coupon.
     * @param limit The usage Limit for this Coupon.
     * @param tags The List of tags for this Coupon.
     * @param remindDate The RemindDate for this Coupon.
     * @param condition The Condition for this Coupon.
     */
    public Coupon(Name name, PromoCode promoCode, Savings savingsForEachUse, ExpiryDate expiryDate, StartDate startDate,
                  Usage usage, Limit limit, Set<Tag> tags, RemindDate remindDate, Condition condition) {

        this(name, promoCode, savingsForEachUse, expiryDate, startDate, usage,
                limit, tags, new DateSavingsSumMap(), remindDate, condition, new Archived());
    }

    /**
     * Constructor for a Coupon, given every required field.
     * Each field should not be null, otherwise a
     * NullPointerException will be thrown!
     * @param name The Name of this Coupon.
     * @param promoCode Promo code for this Coupon..
     * @param savingsForEachUse How much Savings saved
     *                          when this Coupon is used.
     * @param expiryDate The ExpiryDate for this Coupon.
     * @param startDate The StartDate for this Coupon.
     * @param usage The Usage for this Coupon.
     * @param limit The usage Limit for this Coupon.
     * @param tags The List of tags for this Coupon.
     * @param totalSavings DateSavingsSumMap representing
     *                     the total savings accumulated.
     * @param remind Remind representing a reminder for
     *               this Coupon.
     */
    public Coupon(
            Name name,
            PromoCode promoCode,
            Savings savingsForEachUse,
            ExpiryDate expiryDate,
            StartDate startDate,
            Usage usage,
            Limit limit,
            Set<Tag> tags,
            DateSavingsSumMap totalSavings,
            RemindDate remind,
            Condition condition,
            Archived archived) {

        requireAllNonNull(name, promoCode, savingsForEachUse, expiryDate, usage, limit, tags,
                totalSavings, remind, condition, archived);

        this.name = name;
        this.promoCode = promoCode;
        this.savingsForEachUse = savingsForEachUse;
        this.totalSavings = totalSavings;
        this.expiryDate = expiryDate;
        this.startDate = startDate;
        this.remind = remind;
        this.usage = usage;
        this.limit = limit;
        this.archived = archived;
        this.tags.addAll(tags);
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }
    public RemindDate getRemindDate() {
        return remind;
    }

    public Name getName() {
        return name;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    /**
     * Gets the Savings per use associated with this Coupon.
     * @return Savings representing either the monetary
     *     amount saved, percentage amount saved, or
     *     unquantifiable items (Saveables) that will
     *     be earned for every use of this Coupon.
     */
    public Savings getSavingsForEachUse() {
        return savingsForEachUse;
    }

    /**
     * Gets the total Savings stored with this Coupon as
     * a PureMonetarySavings. This total Savings will be
     * increased whenever the Coupon is marked as used.
     * @return PureMonetarySavings representing total
     *     amount of money saved from using this
     *     Coupon, as well as unquantifiable
     *     items (Saveables) earned.
     */
    public PureMonetarySavings getTotalSavings() {
        return this.totalSavings.values().stream()
                .reduce(new PureMonetarySavings(), PureMonetarySavings::add);
    }

    /**
     * Gets a shallow clone of the date and savings map
     * that is associated with this Coupon. LocalDate
     * and PureMonetarySavings are immutable, however.
     * @return DateSavingsSumMap representing the savings
     *     earned for specific dates.
     */
    public DateSavingsSumMap getSavingsMap() {
        return (DateSavingsSumMap) this.totalSavings.clone();
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public Usage getUsage() {
        return usage;
    }

    public Limit getLimit() {
        return limit;
    }

    public Archived getArchived() {
        return archived;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Associates a certain PureMonetarySavings to a LocalDate,
     * and adds it to the DateSavingsSumMap in the totalSavings
     * field of this Coupon, which keeps track of how much has
     * been saved on which date by using this Coupon (for SavedCommand).
     * @param ld The LocalDate representing when the
     *           savings value pms was earned.
     * @param pms The PureMonetarySavings to be added.
     * @return A new Coupon with total savings modified.
     */
    public Coupon addToTotalSavings(LocalDate ld, PureMonetarySavings pms) {
        DateSavingsSumMap updatedMap = (DateSavingsSumMap) this.totalSavings.clone();
        updatedMap.add(ld, pms);
        return new Coupon(this.name, this.promoCode, this.savingsForEachUse,
                this.expiryDate, this.startDate, this.usage, this.limit,
                this.tags, updatedMap, this.remind, this.condition, this.archived);
    }

    /**
     * Returns a new Coupon with total usage increased by one.
     */
    public Coupon increaseUsageByOne() {
        Usage newUsage = this.usage.increaseUsageByOne();
        return new Coupon(this.name, this.promoCode, this.savingsForEachUse,
                this.expiryDate, this.startDate, newUsage,
                this.limit, this.tags, this.totalSavings, this.remind, this.condition, this.archived);
    }

    /**
     * Returns a new archived Coupon.
     */
    public Coupon archive() {
        return setArchive(true);
    }

    /**
     * Returns a new active Coupon.
     */
    public Coupon unarchive() {
        return setArchive(false);
    }

    /**
     * Helper function to set the state of archival for a Coupon.
     * @param state State of archival.
     */
    private Coupon setArchive(boolean state) {
        return new Coupon(this.name, this.promoCode, this.savingsForEachUse,
                this.expiryDate, this.startDate, this.usage, this.limit,
                this.tags, this.totalSavings, this.remind, this.condition, new Archived(state));
    }


    /**
     * Returns true if coupon is archived.
     */
    public boolean isArchived() {
        return this.archived.state;
    }

    /**
     * Returns true if the coupon has expired.
     */
    public boolean hasExpired() {
        return this.expiryDate.hasExpired();
    }

    /**
     * Returns true if the coupon has been used before.
     */
    public boolean isUsed() {
        return this.usage.value > 0;
    }

    /**
     * Returns true if both coupons have the same name, and all
     * of the fields of promo code, savings for each use, expiry date or
     * start date is the same. However, if either of the coupon is archived,
     * they are immediately considered different coupons.
     * This defines a weaker notion of equality between two coupons.
     */
    public boolean isSameCoupon(Coupon otherCoupon) {
        if (otherCoupon == this) {
            return true;
        }

        if (otherCoupon == null || archived.state || otherCoupon.archived.state) {
            return false;
        }

        return otherCoupon.getName().equals(getName())
                && otherCoupon.getPromoCode().equals(getPromoCode())
                && otherCoupon.getExpiryDate().equals(getExpiryDate())
                && otherCoupon.getSavingsForEachUse().equals(getSavingsForEachUse());
    }

    /**
     * Returns true if both coupons have the same identity and data fields.
     * This defines a stronger notion of equality between two coupons.
     *
     * <p>However, total savings is ignored as this field is just used to
     * cache an amount representing how much the Coupon was used to save,
     * and two Coupons with different total savings recorded
     * does not necessarily translate to different Coupons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Coupon)) {
            return false;
        }

        Coupon otherCoupon = (Coupon) other;

        // Loop through the tags to check for equality
        if (getTags().size() != otherCoupon.getTags().size()) {
            return false;
        } else {
            for (Tag tag : getTags()) {
                if (!otherCoupon.getTags().contains(tag)) {
                    return false;
                }
            }
        }

        return otherCoupon.getName().equals(getName())
                && otherCoupon.getPromoCode().equals(getPromoCode())
                && otherCoupon.getSavingsForEachUse().equals(getSavingsForEachUse())
                && otherCoupon.getExpiryDate().equals(getExpiryDate())
                && otherCoupon.getStartDate().equals(getStartDate())
                && otherCoupon.getUsage().equals(getUsage())
                && otherCoupon.getLimit().equals(getLimit())
                && otherCoupon.getTags().equals(getTags())
                && otherCoupon.getArchived().equals(getArchived());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, promoCode, savingsForEachUse, expiryDate,
                startDate, usage, limit, tags, totalSavings, archived);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Promo Code: ")
                .append(getPromoCode())
                .append(" Savings: ")
                .append(getSavingsForEachUse())
                .append(" Expiry Date: ")
                .append(getExpiryDate())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Usage: ")
                .append(getUsage())
                .append(" Limit: ")
                .append(getLimit())
                .append(" Remind Date: ")
                .append(getRemindDate())
                .append(" Condition: ")
                .append(getCondition())
                .append(" Archived: ")
                .append(getArchived())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Deep copy of a coupon.
     * @return The copy of the coupon
     */
    public Coupon copy() {
        // Copy all the tags
        Set<Tag> copiedTags = new HashSet<>(tags);

        Coupon copy = new Coupon(
                new Name(name.toString()), new PromoCode(promoCode.toString()),
                savingsForEachUse.copy(), new ExpiryDate(expiryDate.value),
                new StartDate(startDate.value),
                new Usage(usage.value), new Limit(limit.value), copiedTags,
                totalSavings.copy(), remind.copy(), new Condition(condition.toString()),
                new Archived(archived.state)
        );

        return copy;
    }

}
