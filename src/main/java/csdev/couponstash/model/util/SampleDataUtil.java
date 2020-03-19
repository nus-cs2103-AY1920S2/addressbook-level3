package csdev.couponstash.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.Phone;
import csdev.couponstash.model.coupon.RemindDate;
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
            new Coupon(new Name("Auntie Azah's Nasi Lemak"), new Phone("87438807"),
                new Savings(new MonetaryAmount(5.50)), new ExpiryDate("30-12-2020"),
                    new Usage("0"), new Limit("1"), new RemindDate("15-03-2020"), getTagSet("sedap")),
            new Coupon(new Name("Boszini Clothing"), new Phone("99272758"),
                new Savings(new PercentageAmount(25d)), new ExpiryDate("30-11-2020"),
                    new Usage("0"), new Limit("1"), new RemindDate("15-03-2020"), getTagSet("trendy", "cheap")),
            new Coupon(new Name("Capple oPhone"), new Phone("93210283"),
                new Savings(new PercentageAmount(12.5d)), new ExpiryDate("30-10-2020"),
                    new Usage("0"), new Limit("1"), new RemindDate("15-03-2020"), getTagSet("silver")),
            new Coupon(new Name("Daidas Shoes"), new Phone("91031282"),
                new Savings(new PercentageAmount(10d),
                        Arrays.asList(new Saveable("Water Bottle"), new Saveable("Notebook"))),
                            new ExpiryDate("30-9-2020"), new Usage("0"),
                    new Limit("1"), new RemindDate("15-03-2020"), getTagSet("whilestockslast")),
            new Coupon(new Name("IKEEA"), new Phone("92492021"),
                new Savings(new MonetaryAmount(1d),
                        Arrays.asList(new Saveable("Brattby Bag"))), new ExpiryDate("1-10-2020"),
                    new Usage("0"), new Limit("1"), new RemindDate("15-03-2020"), getTagSet("limitededition")),
            new Coupon(new Name("Rab's Kebabs"), new Phone("92624417"),
                new Savings(new PercentageAmount(100d)), new ExpiryDate("11-9-2020"),
                    new Usage("0"), new Limit("1"), new RemindDate("15-03-2020"), getTagSet("lunch"))
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
