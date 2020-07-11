package csdev.couponstash.testutil;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_CONDITION_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_CONDITION_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PROMO_CODE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PROMO_CODE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_REMIND_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_REMIND_DATE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_SAVINGS_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_SAVINGS_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_USAGE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_USAGE_BOB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

/**
 * A utility class containing a list of {@code Coupon} objects to be used in tests.
 */
public class TypicalCoupons {

    public static final MonetaryAmount ALICE_MONETARY_AMOUNT = new MonetaryAmount(0, 50);
    public static final PercentageAmount BENSON_PERCENTAGE_AMOUNT = new PercentageAmount(30.0);

    public static final Savings ALICE_SAVINGS = new Savings(ALICE_MONETARY_AMOUNT,
            Arrays.asList(new Saveable("Album", 3), new Saveable("MRT Ticket")));
    public static final Savings BENSON_SAVINGS = new Savings(BENSON_PERCENTAGE_AMOUNT);
    public static final Savings CARL_SAVINGS = new Savings(
            new MonetaryAmount(20, 0),
            Arrays.asList(new Saveable("Spa"), new Saveable("Door Gift"), new Saveable("Drink"))
    );
    public static final Savings DANIEL_SAVINGS = new Savings(
            new PercentageAmount(5.0),
            Arrays.asList(new Saveable("Pencil"), new Saveable("Notebook"), new Saveable("Candy"))
    );
    public static final Savings ELLE_SAVINGS = new Savings(
            Arrays.asList(new Saveable("Brattby Bag"), new Saveable("Channel Bag"), new Saveable("Doir Bag"))
    );
    public static final Savings FIONA_SAVINGS = new Savings(new MonetaryAmount(3, 0));
    public static final Savings GEORGE_SAVINGS = new Savings(new PercentageAmount(17.0));
    public static final Savings HOON_SAVINGS = new Savings(
            new MonetaryAmount(2, 5),
            Arrays.asList(new Saveable("Haircut"), new Saveable("Hair Colouring"))
    );
    public static final Savings IDA_SAVINGS = new Savings(
            new PercentageAmount(50.0),
            Arrays.asList(new Saveable("Billy Bookcase"), new Saveable("Poäng Chair"),
                    new Saveable("Norråker Table "))
    );

    public static final DateSavingsSumMap NO_TOTAL_SAVINGS = new DateSavingsSumMap();

    public static final LocalDate SHARED_DATE = LocalDate.of(2019, 2, 7);

    public static final DateSavingsSumMap ALICE_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate ALICE_DATE_1 = LocalDate.of(2019, 2, 11);
    public static final LocalDate ALICE_DATE_2 = LocalDate.of(2019, 9, 14);
    public static final LocalDate ALICE_DATE_3 = LocalDate.of(2019, 12, 19);
    public static final PureMonetarySavings ALICE_PMS_1 = new PureMonetarySavings(new MonetaryAmount(71, 16));
    public static final PureMonetarySavings ALICE_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Apartment", 5), new Saveable("Vehicle"))
    );
    public static final PureMonetarySavings ALICE_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(38, 68),
            Arrays.asList(new Saveable("Addition"), new Saveable("Promotion", 8))
    );
    static {
        ALICE_TOTAL_SAVINGS.add(ALICE_DATE_1, ALICE_PMS_1);
        ALICE_TOTAL_SAVINGS.add(ALICE_DATE_2, ALICE_PMS_2);
        ALICE_TOTAL_SAVINGS.add(ALICE_DATE_3, ALICE_PMS_3);
    }

    public static final DateSavingsSumMap BENSON_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate BENSON_DATE_1 = LocalDate.of(2019, 5, 14);
    public static final LocalDate BENSON_DATE_2 = LocalDate.of(2019, 8, 11);
    public static final LocalDate BENSON_DATE_3 = LocalDate.of(2019, 10, 31);
    public static final PureMonetarySavings BENSON_PMS_1 = new PureMonetarySavings(new MonetaryAmount(1, 24));
    public static final PureMonetarySavings BENSON_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Apartment"), new Saveable("Election", 2))
    );
    public static final PureMonetarySavings BENSON_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(79, 23),
            Arrays.asList(new Saveable("Perception", 3), new Saveable("Insurance", 2))
    );
    static {
        BENSON_TOTAL_SAVINGS.add(BENSON_DATE_1, BENSON_PMS_1);
        BENSON_TOTAL_SAVINGS.add(BENSON_DATE_2, BENSON_PMS_2);
        BENSON_TOTAL_SAVINGS.add(BENSON_DATE_3, BENSON_PMS_3);
    }

    public static final DateSavingsSumMap CARL_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate CARL_DATE_1 = LocalDate.of(2019, 1, 14);
    public static final LocalDate CARL_DATE_2 = LocalDate.of(2019, 9, 6);
    public static final PureMonetarySavings CARL_PMS_1 = new PureMonetarySavings(new MonetaryAmount(39, 52));
    public static final PureMonetarySavings CARL_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Cabinet"), new Saveable("Intention"))
    );
    public static final PureMonetarySavings CARL_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(34, 57),
            Arrays.asList(new Saveable("Charity", 4), new Saveable("Cabinet"))
    );
    static {
        CARL_TOTAL_SAVINGS.add(CARL_DATE_1, CARL_PMS_1);
        CARL_TOTAL_SAVINGS.add(CARL_DATE_2, CARL_PMS_2);
        CARL_TOTAL_SAVINGS.add(SHARED_DATE, CARL_PMS_3);
    }

    public static final DateSavingsSumMap DANIEL_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate DANIEL_DATE_1 = LocalDate.of(2019, 4, 15);
    public static final LocalDate DANIEL_DATE_2 = LocalDate.of(2019, 5, 24);
    public static final LocalDate DANIEL_DATE_3 = LocalDate.of(2019, 9, 12);
    public static final PureMonetarySavings DANIEL_PMS_1 = new PureMonetarySavings(new MonetaryAmount(7, 23));
    public static final PureMonetarySavings DANIEL_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Media"), new Saveable("Professor", 34))
    );
    public static final PureMonetarySavings DANIEL_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(26, 8),
            Arrays.asList(new Saveable("Reception", 41), new Saveable("Perception"))
    );
    static {
        DANIEL_TOTAL_SAVINGS.add(DANIEL_DATE_1, DANIEL_PMS_1);
        DANIEL_TOTAL_SAVINGS.add(DANIEL_DATE_2, DANIEL_PMS_2);
        DANIEL_TOTAL_SAVINGS.add(DANIEL_DATE_3, DANIEL_PMS_3);
    }

    public static final DateSavingsSumMap ELLE_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate ELLE_DATE_1 = LocalDate.of(2019, 3, 30);
    public static final LocalDate ELLE_DATE_2 = LocalDate.of(2019, 4, 17);
    public static final PureMonetarySavings ELLE_PMS_1 = new PureMonetarySavings(new MonetaryAmount(50, 21));
    public static final PureMonetarySavings ELLE_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Conclusion", 3), new Saveable("Strategy"))
    );
    public static final PureMonetarySavings ELLE_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(72, 29),
            Arrays.asList(new Saveable("Investment"), new Saveable("Inspection"))
    );
    static {
        ELLE_TOTAL_SAVINGS.add(ELLE_DATE_1, ELLE_PMS_1);
        ELLE_TOTAL_SAVINGS.add(ELLE_DATE_2, ELLE_PMS_2);
        ELLE_TOTAL_SAVINGS.add(SHARED_DATE, ELLE_PMS_3);
    }

    public static final DateSavingsSumMap FIONA_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate FIONA_DATE_2 = LocalDate.of(2019, 9, 28);
    public static final LocalDate FIONA_DATE_3 = LocalDate.of(2019, 10, 5);
    public static final PureMonetarySavings FIONA_PMS_1 = new PureMonetarySavings(new MonetaryAmount(216, 54));
    public static final PureMonetarySavings FIONA_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Policy", 2), new Saveable("President"))
    );
    public static final PureMonetarySavings FIONA_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(583, 50),
            Arrays.asList(new Saveable("Performance"), new Saveable("Proposal"))
    );
    static {
        FIONA_TOTAL_SAVINGS.add(SHARED_DATE, FIONA_PMS_1);
        FIONA_TOTAL_SAVINGS.add(FIONA_DATE_2, FIONA_PMS_2);
        FIONA_TOTAL_SAVINGS.add(FIONA_DATE_3, FIONA_PMS_3);
    }

    public static final DateSavingsSumMap GEORGE_TOTAL_SAVINGS = new DateSavingsSumMap();
    public static final LocalDate GEORGE_DATE_1 = LocalDate.of(2019, 2, 10);
    public static final LocalDate GEORGE_DATE_3 = LocalDate.of(2019, 12, 14);
    public static final PureMonetarySavings GEORGE_PMS_1 = new PureMonetarySavings(new MonetaryAmount(3648, 7));
    public static final PureMonetarySavings GEORGE_PMS_2 = new PureMonetarySavings(
            Arrays.asList(new Saveable("Charity"), new Saveable("Construction"))
    );
    public static final PureMonetarySavings GEORGE_PMS_3 = new PureMonetarySavings(
            new MonetaryAmount(475103, 66),
            Arrays.asList(new Saveable("Expression", 6), new Saveable("Perception"))
    );
    static {
        GEORGE_TOTAL_SAVINGS.add(GEORGE_DATE_1, GEORGE_PMS_1);
        GEORGE_TOTAL_SAVINGS.add(SHARED_DATE, GEORGE_PMS_2);
        GEORGE_TOTAL_SAVINGS.add(GEORGE_DATE_3, GEORGE_PMS_3);
    }

    public static final Coupon ALICE = new CouponBuilder().withName("Alice Pauline")
            .withPromoCode("I<3STASH")
            .withSavings(ALICE_SAVINGS)
            .withExpiryDate("2-12-2020")
            .withTotalSavings(ALICE_TOTAL_SAVINGS)
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(1)
            .withRemindDate("29-11-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .withTags("friends").build();
    public static final Coupon BENSON = new CouponBuilder().withName("Benson Meier")
            .withPromoCode("ILUVSTASH")
            .withSavings(BENSON_SAVINGS)
            .withTotalSavings(BENSON_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2019")
            .withUsage(2)
            .withLimit(5)
            .withRemindDate("28-11-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .withTags("owesMoney", "friends").build();
    public static final Coupon CARL = new CouponBuilder().withName("Carl Kurz")
            .withPromoCode("KKB")
            .withSavings(CARL_SAVINGS)
            .withTotalSavings(CARL_TOTAL_SAVINGS)
            .withExpiryDate("30-8-2020")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(2)
            .withRemindDate("27-8-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .build();

    // Expired coupon
    public static final Coupon DANIEL = new CouponBuilder().withName("Daniel Meier")
            .withPromoCode("DANIELZXC")
            .withSavings(DANIEL_SAVINGS)
            .withTotalSavings(DANIEL_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2019")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(1)
            .withTags("friends")
            .withRemindDate("28-12-2019")
            .withCondition("While Stock Last")
            .withArchived(false)
            .build();

    // Archived coupon
    public static final Coupon ELLE = new CouponBuilder().withName("Elle Meyer")
            .withPromoCode("9482224")
            .withSavings(ELLE_SAVINGS)
            .withTotalSavings(ELLE_TOTAL_SAVINGS)
            .withExpiryDate("30-8-2020")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(1)
            .withRemindDate("27-8-2020")
            .withCondition("While Stock Last")
            .withArchived(true)
            .build();

    public static final Coupon FIONA = new CouponBuilder().withName("Fiona Kunz")
            .withPromoCode("9482427")
            .withSavings(FIONA_SAVINGS)
            .withTotalSavings(FIONA_TOTAL_SAVINGS)
            .withExpiryDate("30-8-2020")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(1)
            .withRemindDate("27-8-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .build();


    public static final Coupon GEORGE = new CouponBuilder().withName("George Best")
            .withPromoCode("9482442")
            .withSavings(GEORGE_SAVINGS)
            .withTotalSavings(GEORGE_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(2)
            .withRemindDate("28-12-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .build();

    // Manually added
    public static final Coupon HOON = new CouponBuilder().withName("Hoon Meier")
            .withPromoCode("ILOVEHOOTERS")
            .withSavings(HOON_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(1)
            .withRemindDate("28-12-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .build();
    public static final Coupon IDA = new CouponBuilder().withName("Ida Mueller")
            .withPromoCode("ILOVEICECREAM")
            .withSavings(IDA_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2019")
            .withUsage(0)
            .withLimit(1)
            .withRemindDate("28-12-2020")
            .withCondition("While Stock Last")
            .withArchived(false)
            .build();

    // Manually added - Coupon's details found in {@code CommandTestUtil}
    public static final Coupon AMY = new CouponBuilder()
            .withName(VALID_NAME_AMY)
            .withPromoCode(VALID_PROMO_CODE_AMY)
            .withSavings(VALID_SAVINGS_AMY)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate(VALID_EXPIRY_DATE_AMY)
            .withStartDate(VALID_START_DATE_AMY)
            .withUsage(VALID_USAGE_AMY)
            .withLimit(VALID_LIMIT_AMY)
            .withRemindDate(VALID_REMIND_DATE_AMY)
            .withCondition(VALID_CONDITION_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Coupon BOB = new CouponBuilder()
            .withName(VALID_NAME_BOB)
            .withPromoCode(VALID_PROMO_CODE_BOB)
            .withSavings(VALID_SAVINGS_BOB)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate(VALID_EXPIRY_DATE_BOB)
            .withStartDate(VALID_START_DATE_BOB)
            .withUsage(VALID_USAGE_BOB)
            .withLimit(VALID_LIMIT_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withRemindDate(VALID_REMIND_DATE_BOB)
            .withCondition(VALID_CONDITION_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCoupons() {} // prevents instantiation

    /**
     * Returns an {@code CouponStash} with all the typical coupons.
     */
    public static CouponStash getTypicalCouponStash() {
        CouponStash ab = new CouponStash();
        for (Coupon coupon : getTypicalCoupons()) {
            ab.addCoupon(coupon);
        }
        return ab;
    }

    public static List<Coupon> getTypicalCoupons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
