package csdev.couponstash.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.model.coupon.Archived;
import csdev.couponstash.model.coupon.Condition;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.RemindDate;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
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
    public static final String DEFAULT_PROMO_CODE = "ILOVESTASH";
    public static final Savings DEFAULT_SAVINGS = new Savings(new MonetaryAmount(32, 5));
    public static final DateSavingsSumMap DEFAULT_TOTAL_SAVINGS =
            new DateSavingsSumMap(
                    LocalDate.of(2020, 2, 2),
                    new PureMonetarySavings(new MonetaryAmount(97, 5)));
    public static final String DEFAULT_EXPIRY_DATE = "30-08-2020";
    public static final String DEFAULT_START_DATE = DateUtil.formatDateToString(LocalDate.now());
    public static final String DEFAULT_USAGE = "0";
    public static final String DEFAULT_LIMIT = "7";
    public static final String DEFAULT_REMIND_DATE = "27-08-2020";
    public static final String DEFAULT_CONDITION = "While Stocks Last";
    public static final Boolean DEFAULT_ARCHIVED = false;

    public static final String FULL_COMMAND_TEXT =
            String.format("n/%s p/%s e/%s s/%s sd/%s l/%s",
                    DEFAULT_NAME,
                    DEFAULT_PROMO_CODE,
                    DEFAULT_EXPIRY_DATE,
                    DEFAULT_SAVINGS,
                    DEFAULT_START_DATE,
                    DEFAULT_LIMIT
            );

    private Name name;
    private PromoCode promoCode;
    private Savings savings;
    private DateSavingsSumMap totalSavings;
    private ExpiryDate expiryDate;
    private StartDate startDate;
    private Usage usage;
    private Limit limit;
    private RemindDate remindDate;
    private Set<Tag> tags;
    private Condition condition;
    private Archived archived;

    private boolean isUsageChanged = false;

    public CouponBuilder() {
        name = new Name(DEFAULT_NAME);
        promoCode = new PromoCode(DEFAULT_PROMO_CODE);
        savings = new Savings(DEFAULT_SAVINGS);
        totalSavings = DEFAULT_TOTAL_SAVINGS;
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        startDate = new StartDate(DEFAULT_START_DATE);
        usage = new Usage(DEFAULT_USAGE);
        limit = new Limit(DEFAULT_LIMIT);
        remindDate = new RemindDate(DEFAULT_REMIND_DATE);
        tags = new HashSet<>();
        condition = new Condition(DEFAULT_CONDITION);
        archived = new Archived(DEFAULT_ARCHIVED);
    }

    /**
     * Initializes the CouponBuilder with the data of {@code couponToCopy}.
     */
    public CouponBuilder(Coupon couponToCopy) {
        name = couponToCopy.getName();
        promoCode = couponToCopy.getPromoCode();
        savings = new Savings(couponToCopy.getSavingsForEachUse());
        totalSavings = couponToCopy.getSavingsMap();
        expiryDate = couponToCopy.getExpiryDate();
        startDate = couponToCopy.getStartDate();
        usage = couponToCopy.getUsage();
        limit = couponToCopy.getLimit();
        remindDate = couponToCopy.getRemindDate();
        tags = new HashSet<>(couponToCopy.getTags());
        condition = couponToCopy.getCondition();
        archived = couponToCopy.getArchived();
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
    public CouponBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code PromoCode} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withPromoCode(String promoCode) {
        this.promoCode = new PromoCode(promoCode);
        return this;
    }

    /**
     * Sets the {@code Savings} of the {@code Coupon} that we are building.
     *
     * @param sv The Savings to set.
     * @return This CouponBuilder (mutated).
     */
    public CouponBuilder withSavings(Savings sv) {
        this.savings = sv;
        return this;
    }

    /**
     * Sets the {@code DateSavingsSumMap} of the {@code Coupon} that we are building.
     * DateSavingsSumMap represents the daily savings earned from using the Coupon.
     *
     * @param dssm The DateSavingsSumMap to set.
     * @return This CouponBuilder (mutated).
     */
    public CouponBuilder withTotalSavings(DateSavingsSumMap dssm) {
        this.totalSavings = dssm;
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
     * Sets the {@code StartDate} of the {@code Coupon} that we are building. Start Date will set the date to today's
     * date if it is an empty argument.
     */
    public CouponBuilder withStartDate() {
        this.startDate = new StartDate(DateUtil.formatDateToString(LocalDate.now()));
        return this;
    }

    /**
     * Sets the {@code Usage} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withUsage(int usage) {
        this.usage = new Usage(usage);
        this.isUsageChanged = true;
        return this;
    }

    /**
     * Sets the {@code Limit} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withLimit(int limit) {
        this.limit = new Limit(limit);
        return this;
    }

    /**
     * Sets the {@code RemindDate} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withRemindDate(String remindDate) {
        this.remindDate = new RemindDate(remindDate);
        return this;
    }
    /**
     * Sets the {@code Condition} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withCondition(String condition) {
        this.condition = new Condition(condition);
        return this;
    }

    /**
     * Sets the {@code Archive} of the {@code Coupon} that we are building.
     */
    public CouponBuilder withArchived(Boolean state) {
        this.archived = new Archived(state);
        return this;
    }

    /**
     * Builds the Coupon from the provided fields.
     *
     * @return A new Coupon.
     */
    public Coupon build() {
        boolean isUsageAtLimit = usage.isAtLimit(limit);
        if (isUsageChanged && isUsageAtLimit) {
            archived = new Archived(true);
        }

        return new Coupon(name, promoCode, savings, expiryDate, startDate,
                usage, limit, tags, totalSavings, remindDate, condition, archived);
    }

}
