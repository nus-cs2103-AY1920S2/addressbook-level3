package csdev.couponstash.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;
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
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CouponStash} with sample data.
 */
public class SampleDataUtil {
    public static Coupon[] getSampleCoupons() {
        ExpiryDate azahExpiry = new ExpiryDate("30-12-2020");
        ExpiryDate bosziniExpiry = new ExpiryDate("30-11-2020");
        ExpiryDate cappleExpiry = new ExpiryDate("30-10-2020");
        ExpiryDate daidasExpiry = new ExpiryDate("30-09-2020");
        ExpiryDate ikeeaExpiry = new ExpiryDate("01-10-2020");
        ExpiryDate rabsExpiry = new ExpiryDate("11-09-2020");

        ArrayList<Saveable> azahSaveables = new ArrayList<Saveable>();
        azahSaveables.add(new Saveable("Sambal Sauce"));
        DateSavingsSumMap azahSavings = new DateSavingsSumMap();
        azahSavings.add(LocalDate.of(2020, 1, 29),
                new PureMonetarySavings(new MonetaryAmount(1, 0), azahSaveables));
        azahSavings.add(LocalDate.of(2020, 2, 19),
                new PureMonetarySavings(new MonetaryAmount(13, 60)));

        DateSavingsSumMap bosziniSavings = new DateSavingsSumMap();
        bosziniSavings.add(LocalDate.of(2020, 3, 11),
                new PureMonetarySavings(new MonetaryAmount(7, 30)));
        bosziniSavings.add(LocalDate.of(2020, 3, 18),
                new PureMonetarySavings(new MonetaryAmount(17, 0)));

        ArrayList<Saveable> cappleSaveables = new ArrayList<Saveable>();
        cappleSaveables.add(new Saveable("Brattby Bag"));
        cappleSaveables.add(new Saveable("Goggle Pix 1"));
        DateSavingsSumMap cappleSavings = new DateSavingsSumMap();
        cappleSavings.add(LocalDate.of(2020, 2, 26),
                new PureMonetarySavings(new MonetaryAmount(4, 0)));
        cappleSavings.add(LocalDate.of(2020, 4, 1),
                new PureMonetarySavings(new MonetaryAmount(25, 20)));

        DateSavingsSumMap daidasSavings = new DateSavingsSumMap();
        daidasSavings.add(LocalDate.of(2020, 3, 25),
                new PureMonetarySavings(new MonetaryAmount(1, 50)));

        ArrayList<Saveable> ikeeaSaveables = new ArrayList<Saveable>();
        ikeeaSaveables.add(new Saveable("Brattby Bag"));
        ikeeaSaveables.add(new Saveable("IKEEA Pencil"));
        DateSavingsSumMap ikeeaSavings = new DateSavingsSumMap();
        ikeeaSavings.add(LocalDate.of(2020, 2, 5),
                new PureMonetarySavings(new MonetaryAmount(2, 75), ikeeaSaveables));

        DateSavingsSumMap rabsSavings = new DateSavingsSumMap();
        rabsSavings.add(LocalDate.of(2020, 3, 4),
                new PureMonetarySavings(new MonetaryAmount(11, 0)));

        return new Coupon[] {
            new Coupon(new Name("Auntie Azah's Nasi Lemak"), new PromoCode("AZAH BAIK LA"),
                new Savings(new MonetaryAmount(5, 50)), azahExpiry,
                    new StartDate("01-12-2020"), new Usage("0"), new Limit("1"),
                    getTagSet("sedap"), azahSavings, new RemindDate(azahExpiry),
                    new Condition("Min spending of $15 on sambal sauce"), new Archived()),
            new Coupon(new Name("Boszini Clothing"), new PromoCode("NAKED SUMMER"),
                new Savings(new PercentageAmount(25d)), bosziniExpiry, new StartDate("01-11-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("trendy", "cheap"),
                    bosziniSavings, new RemindDate(bosziniExpiry),
                    new Condition("Only applicable to selected beachwear"), new Archived()),
            new Coupon(new Name("Capple oPhone"), new PromoCode("ILOVECAP_5.0"),
                new Savings(new PercentageAmount(12.5d)), cappleExpiry, new StartDate("01-10-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("silver"),
                    cappleSavings, new RemindDate(cappleExpiry),
                    new Condition("Need to trade in one apple phone"), new Archived()),
            new Coupon(new Name("Daidas Shoes"), new PromoCode("JUSTDOIT"),
                new Savings(new PercentageAmount(10d),
                        Arrays.asList(new Saveable("Water Bottle"), new Saveable("Notebook"))),
                            daidasExpiry, new StartDate("01-08-2020"), new Usage("0"),
                    new Limit("1"), getTagSet("whilestockslast"),
                    daidasSavings, new RemindDate(daidasExpiry),
                    new Condition("While Stocks Last"), new Archived()),
            new Coupon(new Name("IKEEA"), new PromoCode("BRATTBY_IS_YOUR_FRIEND"),
                new Savings(new MonetaryAmount(1, 0),
                        Arrays.asList(new Saveable("Brattby Bag"))), ikeeaExpiry,
                    new StartDate("01-09-2020"), new Usage("0"), new Limit("1"), getTagSet("limitededition"),
                    ikeeaSavings, new RemindDate(ikeeaExpiry),
                    new Condition("Meatball are made of horsemeat"), new Archived()),
            new Coupon(new Name("Rab's Kebabs"), new PromoCode("UPZ KEBABZ"),
                new Savings(new PercentageAmount(100d)), rabsExpiry, new StartDate("01-8-2020"),
                    new Usage("0"), new Limit("1"), getTagSet("lunch"),
                    rabsSavings, new RemindDate(rabsExpiry),
                    new Condition("Muslim students get an additional begedil (Worth $50)"), new Archived())
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
