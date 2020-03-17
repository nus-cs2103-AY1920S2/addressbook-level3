package csdev.couponstash.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.Phone;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;
import csdev.couponstash.model.util.SampleDataUtil;

/**
 * A utility class to help with building Coupon objects.
 */
public class CouponBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final Savings DEFAULT_SAVINGS = new Savings(new MonetaryAmount(32.5));
    public static final PureMonetarySavings DEFAULT_TOTAL_SAVINGS =
            new PureMonetarySavings(new MonetaryAmount(97.5));
    public static final String DEFAULT_EXPIRY_DATE = "30-08-2020";
    public static final String DEFAULT_START_DATE = LocalDate.now().format(StartDate.DATE_FORMATTER);
    public static final String DEFAULT_USAGE = "3";
    public static final String DEFAULT_LIMIT = "7";

    private Name name;
    private Phone phone;
    private Savings savings;
    private PureMonetarySavings totalSavings;
    private ExpiryDate expiryDate;
    private StartDate startDate;
    private Usage usage;
    private Limit limit;
    private Set<Tag> tags;

    public CouponBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        savings = new Savings(DEFAULT_SAVINGS);
        totalSavings = DEFAULT_TOTAL_SAVINGS;
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        startDate = new StartDate(DEFAULT_START_DATE);
        usage = new Usage(DEFAULT_USAGE);
        limit = new Limit(DEFAULT_LIMIT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CouponBuilder with the data of {@code couponToCopy}.
     */
    public CouponBuilder(Coupon couponToCopy) {
        name = couponToCopy.getName();
        phone = couponToCopy.getPhone();
        savings = new Savings(couponToCopy.getSavingsForEachUse());
        totalSavings = new PureMonetarySavings(couponToCopy.getTotalSavings());
        expiryDate = couponToCopy.getExpiryDate();
        startDate = couponToCopy.getStartDate();
        usage = couponToCopy.getUsage();
        limit = couponToCopy.getLimit();
        tags = new HashSet<>(couponToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Coupon} that we are building.
     */
    public CouponBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Savings} of the {@code Coupon} that we are building.
     * @param sv The Savings to set.
     * @return This CouponBuilder (mutated).
     */
    public CouponBuilder withSavings(Savings sv) {
        this.savings = sv;
        return this;
    }

    /**
     * Sets the {@code PureMonetarySavings} of the {@code Coupon} that we are building.
     * PureMonetarySavings represents the total savings earned from using the Coupon.
     * @param pms The PureMonetarySavings to set.
     * @return This CouponBuilder (mutated).
     */
    public CouponBuilder withTotalSavings(PureMonetarySavings pms) {
        this.totalSavings = pms;
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withStartDate(String startDate) {
        this.startDate = new StartDate(startDate);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Coupon} that we are building. For empty arguments.
     */
    public CouponBuilder withStartDate() {
        this.startDate = new StartDate("");
        return this;
    }

    /**
     * Sets the {@code Usage} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withUsage(String usage) {
        this.usage = new Usage(usage);
        return this;
    }

    /**
     * Sets the {@code Limit} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withLimit(String limit) {
        this.limit = new Limit(limit);
        return this;
    }

    public Coupon build() {
        return new Coupon(name, phone, savings, expiryDate, startDate, usage, limit, tags);
    }

}
