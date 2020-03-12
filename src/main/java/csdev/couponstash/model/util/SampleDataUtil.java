package csdev.couponstash.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.Phone;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CouponStash} with sample data.
 */
public class SampleDataUtil {
    public static Coupon[] getSampleCoupons() {
        return new Coupon[] {
            new Coupon(new Name("Alex Yeoh"), new Phone("87438807"),
                new Usage("0"), getTagSet("friends")),
            new Coupon(new Name("Bernice Yu"), new Phone("99272758"),
                new Usage("1"), getTagSet("colleagues", "friends")),
            new Coupon(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Usage("2"), getTagSet("neighbours")),
            new Coupon(new Name("David Li"), new Phone("91031282"),
                new Usage("3"), getTagSet("family")),
            new Coupon(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Usage("4"), getTagSet("classmates")),
            new Coupon(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Usage("5"), getTagSet("colleagues"))
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
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
