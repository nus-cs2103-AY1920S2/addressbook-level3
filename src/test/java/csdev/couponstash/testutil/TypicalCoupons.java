package csdev.couponstash.testutil;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_SAVINGS_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_SAVINGS_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_USAGE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_USAGE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.PercentageAmount;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;

/**
 * A utility class containing a list of {@code Coupon} objects to be used in tests.
 */
public class TypicalCoupons {

    public static final MonetaryAmount ALICE_MONETARY_AMOUNT = new MonetaryAmount(0.5);
    public static final PercentageAmount BENSON_PERCENTAGE_AMOUNT = new PercentageAmount(30.0);

    public static final Savings ALICE_SAVINGS = new Savings(ALICE_MONETARY_AMOUNT);
    public static final Savings BENSON_SAVINGS = new Savings(BENSON_PERCENTAGE_AMOUNT);
    public static final Savings CARL_SAVINGS = new Savings(
            new MonetaryAmount(20.0),
            Arrays.asList(new Saveable("Spa"), new Saveable("Door Gift"), new Saveable("Drink"))
    );
    public static final Savings DANIEL_SAVINGS = new Savings(
            new PercentageAmount(5.0),
            Arrays.asList(new Saveable("Pencil"), new Saveable("Notebook"), new Saveable("Candy"))
    );
    public static final Savings ELLE_SAVINGS = new Savings(
            Arrays.asList(new Saveable("Brattby Bag"), new Saveable("Channel Bag"), new Saveable("Doir Bag"))
    );
    public static final Savings FIONA_SAVINGS = new Savings(new MonetaryAmount(3.0));
    public static final Savings GEORGE_SAVINGS = new Savings(new PercentageAmount(17.0));
    public static final Savings HOON_SAVINGS = new Savings(
            new MonetaryAmount(2.5),
            Arrays.asList(new Saveable("Haircut"), new Saveable("Hair Colouring"))
    );
    public static final Savings IDA_SAVINGS = new Savings(
            new PercentageAmount(50.0),
            Arrays.asList(new Saveable("Billy Bookcase"), new Saveable("Poäng Chair"),
                    new Saveable("Norråker Table "))
    );

    public static final PureMonetarySavings NO_TOTAL_SAVINGS = new PureMonetarySavings();

    public static final Coupon ALICE = new CouponBuilder().withName("Alice Pauline")
            .withPhone("94351253")
            .withSavings(ALICE_SAVINGS)
            .withExpiryDate("2-12-2020")
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .withTags("friends").build();
    public static final Coupon BENSON = new CouponBuilder().withName("Benson Meier")
            .withPhone("98765432")
            .withSavings(BENSON_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2020")
            .withUsage("2")
            .withLimit("5")
            .withTags("owesMoney", "friends").build();
    public static final Coupon CARL = new CouponBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withSavings(CARL_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("30-8-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .build();
    public static final Coupon DANIEL = new CouponBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withSavings(DANIEL_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .withTags("friends").build();
    public static final Coupon ELLE = new CouponBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withSavings(ELLE_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("30-8-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .build();
    public static final Coupon FIONA = new CouponBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withSavings(FIONA_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("30-8-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .build();
    public static final Coupon GEORGE = new CouponBuilder().withName("George Best")
            .withPhone("9482442")
            .withSavings(GEORGE_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("2")
            .build();

    // Manually added
    public static final Coupon HOON = new CouponBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withSavings(HOON_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .build();
    public static final Coupon IDA = new CouponBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withSavings(IDA_SAVINGS)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate("31-12-2020")
            .withStartDate("31-7-2020")
            .withUsage("0")
            .withLimit("1")
            .build();

    // Manually added - Coupon's details found in {@code CommandTestUtil}
    public static final Coupon AMY = new CouponBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withSavings(VALID_SAVINGS_AMY)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate(VALID_EXPIRY_DATE_AMY)
            .withStartDate(VALID_START_DATE_AMY)
            .withUsage(VALID_USAGE_AMY)
            .withLimit(VALID_LIMIT_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Coupon BOB = new CouponBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withSavings(VALID_SAVINGS_BOB)
            .withTotalSavings(NO_TOTAL_SAVINGS)
            .withExpiryDate(VALID_EXPIRY_DATE_BOB)
            .withStartDate(VALID_START_DATE_BOB)
            .withUsage(VALID_USAGE_BOB)
            .withLimit(VALID_LIMIT_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
