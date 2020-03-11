package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Represents a Coupon in the CouponStash.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Coupon {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Savings field
    private final Savings savings;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Coupon(Name name, Phone phone, Email email, Savings savings, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.savings = savings;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Gets the Savings associated with this Coupon.
     * @return Savings representing either the monetary
     *     amount saved, percentage amount saved, or
     *     unquantifiable items (Saveables).
     */
    public Savings getSavings() {
        return savings;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both coupons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two coupons.
     */
    public boolean isSameCoupon(Coupon otherCoupon) {
        if (otherCoupon == this) {
            return true;
        }

        return otherCoupon != null
                && otherCoupon.getName().equals(getName())
                && (otherCoupon.getPhone().equals(getPhone()) || otherCoupon.getEmail().equals(getEmail())
                        || otherCoupon.getSavings().equals(getSavings()));
    }

    /**
     * Returns true if both coupons have the same identity and data fields.
     * This defines a stronger notion of equality between two coupons.
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
        return otherCoupon.getName().equals(getName())
                && otherCoupon.getPhone().equals(getPhone())
                && otherCoupon.getEmail().equals(getEmail())
                && otherCoupon.getSavings().equals(getSavings())
                && otherCoupon.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, savings, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Savings: ")
                .append(getSavings())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
