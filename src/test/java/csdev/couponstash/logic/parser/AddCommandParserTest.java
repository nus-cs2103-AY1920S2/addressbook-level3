package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_LIMIT_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_SAVINGS_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.LIMIT_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.LIMIT_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static csdev.couponstash.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static csdev.couponstash.logic.commands.CommandTestUtil.PROMO_CODE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.PROMO_CODE_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.SAVINGS_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.SAVINGS_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.SAVINGS_DESC_BOB_TWO_MONETARY_AMOUNT;
import static csdev.couponstash.logic.commands.CommandTestUtil.START_DATE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.START_DATE_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static csdev.couponstash.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static csdev.couponstash.logic.commands.CommandTestUtil.USAGE_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_MONETARY_AMOUNT_TWO_TWENTY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_MONEY_SYMBOL;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PROMO_CODE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.model.tag.Tag;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.TypicalCoupons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser(VALID_MONEY_SYMBOL.toString());

    @Test
    public void parse_allFieldsPresent_success() {
        Coupon expectedCoupon = new CouponBuilder(TypicalCoupons.BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + EXPIRY_DATE_DESC_BOB + START_DATE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCoupon));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + EXPIRY_DATE_DESC_BOB + START_DATE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCoupon));

        // multiple promoCodes - last promoCode accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PROMO_CODE_DESC_AMY + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + EXPIRY_DATE_DESC_BOB + START_DATE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCoupon));

        // multiple limits - last limit accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + EXPIRY_DATE_DESC_BOB + START_DATE_DESC_BOB + LIMIT_DESC_AMY + LIMIT_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedCoupon));

        // multiple tags - all accepted
        Coupon expectedCouponMultipleTags = new CouponBuilder(TypicalCoupons.BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB + TAG_DESC_HUSBAND
                + EXPIRY_DATE_DESC_BOB + START_DATE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCouponMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Coupon expectedCoupon = new CouponBuilder(TypicalCoupons.AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PROMO_CODE_DESC_AMY + SAVINGS_DESC_AMY
                        + EXPIRY_DATE_DESC_AMY + START_DATE_DESC_AMY + LIMIT_DESC_AMY,
                new AddCommand(expectedCoupon));

        // no start date
        Coupon expectedCouponNoStartDate = new CouponBuilder(TypicalCoupons.AMY).withStartDate().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PROMO_CODE_DESC_AMY + SAVINGS_DESC_AMY
                        + EXPIRY_DATE_DESC_AMY + LIMIT_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedCouponNoStartDate));

        // no usages
        Coupon expectedCouponNoUsages = new CouponBuilder(TypicalCoupons.AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PROMO_CODE_DESC_AMY + SAVINGS_DESC_AMY
                        + EXPIRY_DATE_DESC_AMY + START_DATE_DESC_AMY + LIMIT_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedCouponNoUsages));

        // no limit
        Coupon expectedCouponNoLimit = new CouponBuilder(TypicalCoupons.AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PROMO_CODE_DESC_AMY + SAVINGS_DESC_AMY
                        + EXPIRY_DATE_DESC_AMY + START_DATE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedCouponNoLimit));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB,
                expectedMessage);

        // missing savings prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PROMO_CODE_BOB + VALID_MONEY_SYMBOL
                + VALID_MONETARY_AMOUNT_TWO_TWENTY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PROMO_CODE_BOB + VALID_MONEY_SYMBOL
                + VALID_MONETARY_AMOUNT_TWO_TWENTY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + EXPIRY_DATE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid expiry date
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + INVALID_EXPIRY_DATE_DESC + LIMIT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                ExpiryDate.MESSAGE_CONSTRAINTS);

        //invalid start date
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB
                + EXPIRY_DATE_DESC_BOB + INVALID_START_DATE_DESC + LIMIT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                StartDate.MESSAGE_CONSTRAINTS);

        // invalid savings
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + INVALID_SAVINGS_DESC
                + EXPIRY_DATE_DESC_BOB + LIMIT_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Savings.MESSAGE_CONSTRAINTS);

        // multiple savings monetary amount fails
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB_TWO_MONETARY_AMOUNT
                        + EXPIRY_DATE_DESC_BOB + START_DATE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_FRIEND,
                Savings.MULTIPLE_NUMBER_AMOUNTS);

        // non-addable usage
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB + EXPIRY_DATE_DESC_BOB
                + USAGE_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Usage.MESSAGE_UNEDITABLE);

        // invalid limit
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB + EXPIRY_DATE_DESC_BOB
                + INVALID_LIMIT_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Limit.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PROMO_CODE_DESC_BOB + SAVINGS_DESC_BOB + EXPIRY_DATE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_PROMO_CODE_BOB + SAVINGS_DESC_BOB
                        + EXPIRY_DATE_DESC_BOB + LIMIT_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PROMO_CODE_DESC_BOB + EXPIRY_DATE_DESC_BOB
                + SAVINGS_DESC_BOB + LIMIT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
