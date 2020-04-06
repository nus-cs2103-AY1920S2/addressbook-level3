package csdev.couponstash.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.coupon.Condition;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CouponStash} with sample data.
 */
public class SampleDataUtil {
    public static Coupon[] getSampleCoupons() {
        return new Coupon[] {
            new Coupon(new Name("Auntie Azah's Nasi Lemak"), new PromoCode("AZAH BAIK LA"),
                new Savings(new MonetaryAmount(5, 50)), new ExpiryDate("30-12-2020"), new StartDate("01-12-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("sedap"),
                    new Condition("Min spending of $15 on sambal sauce")),
            new Coupon(new Name("Boszini Clothing"), new PromoCode("NAKED SUMMER"),
                new Savings(new PercentageAmount(25d)), new ExpiryDate("30-11-2020"), new StartDate("01-11-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("trendy", "cheap"),
                    new Condition("Only applicable to selected beachwear")),
            new Coupon(new Name("Capple oPhone"), new PromoCode("ILOVECAP_5.0"),
                new Savings(new PercentageAmount(12.5d)), new ExpiryDate("30-10-2020"), new StartDate("01-10-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("silver"),
                    new Condition("Need to trade in one apple phone")),
            new Coupon(new Name("Daidas Shoes"), new PromoCode("JUSTDOIT"),
                new Savings(new PercentageAmount(10d),
                        Arrays.asList(new Saveable("Water Bottle"), new Saveable("Notebook"))),
                            new ExpiryDate("30-09-2020"), new StartDate("01-08-2020"), new Usage("0"),
                    new Limit("1"), getTagSet("whilestockslast"),
                    new Condition("While Stocks Last")),
            new Coupon(new Name("IKEEA"), new PromoCode("BRATTBY_IS_YOUR_FRIEND"),
                new Savings(new MonetaryAmount(1, 0),
                        Arrays.asList(new Saveable("Brattby Bag"))), new ExpiryDate("01-10-2020"),
                    new StartDate("01-09-2020"), new Usage("0"), new Limit("1"), getTagSet("limitededition"),
                    new Condition("Meatball are made of horsemeat")),
            new Coupon(new Name("Rab's Kebabs"), new PromoCode("UPZ KEBABZ"),
                new Savings(new PercentageAmount(100d)), new ExpiryDate("11-09-2020"), new StartDate("01-8-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("lunch"),
                    new Condition("Muslim students get an additional begedil (Worth $50)"))
        };
    }

    public static ReadOnlyCouponStash getSampleCouponStash() {
        CouponStash sampleAb = new CouponStash();
        for (Coupon sampleCoupon : getSampleCoupons()) {
            sampleAb.addCoupon(sampleCoupon);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new).collect(Collectors.toSet());
    }

}
