package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static csdev.couponstash.logic.commands.CommandTestUtil.CONDITION_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_LIMIT_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static csdev.couponstash.logic.commands.CommandTestUtil.LIMIT_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.LIMIT_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.PROMO_CODE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.PROMO_CODE_DESC_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.START_DATE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static csdev.couponstash.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static csdev.couponstash.logic.commands.CommandTestUtil.USAGE_DESC_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_CONDITION_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_MONEY_SYMBOL;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PROMO_CODE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_PROMO_CODE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.EditCommand;
import csdev.couponstash.logic.commands.EditCommand.EditCouponDescriptor;
import csdev.couponstash.model.coupon.ExpiryDate;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.StartDate;
import csdev.couponstash.model.tag.Tag;
import csdev.couponstash.testutil.EditCouponDescriptorBuilder;
import csdev.couponstash.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT = ParserUtil.MESSAGE_INVALID_INDEX + "\n\n"
            + String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser(VALID_MONEY_SYMBOL.toString());

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(
                parser,
                VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_FORMAT, VALID_NAME_AMY)
        );

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", String.format(MESSAGE_INVALID_FORMAT, ""));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(
                parser, "-5" + NAME_DESC_AMY, String.format(MESSAGE_INVALID_FORMAT, "-5")
        );

        // zero index
        CommandParserTestUtil.assertParseFailure(
                parser,
                "0" + NAME_DESC_AMY, String.format(MESSAGE_INVALID_FORMAT, "0")
        );

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(
                parser,
                "1 some random string",
                String.format(MESSAGE_INVALID_FORMAT, "1 some random string")
        );

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(
                parser,
                "1 i/ string",
                String.format(MESSAGE_INVALID_FORMAT, "1 i/ string")
        );
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + INVALID_LIMIT_DESC,
                Limit.MESSAGE_CONSTRAINTS); // invalid limit
        CommandParserTestUtil.assertParseFailure(parser, "1" + INVALID_EXPIRY_DATE_DESC,
                ExpiryDate.MESSAGE_CONSTRAINTS); // invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, "1" + INVALID_START_DATE_DESC,
                StartDate.MESSAGE_CONSTRAINTS); // invalid start date
        CommandParserTestUtil.assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Coupon} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + VALID_PROMO_CODE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        String userInput = targetIndex.getOneBased() + PROMO_CODE_DESC_BOB + TAG_DESC_HUSBAND
                + NAME_DESC_AMY + LIMIT_DESC_BOB + TAG_DESC_FRIEND;

        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPromoCode(VALID_PROMO_CODE_BOB)
                .withLimit(VALID_LIMIT_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_COUPON;
        String userInput = targetIndex.getOneBased() + PROMO_CODE_DESC_BOB;

        EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder().withPromoCode(VALID_PROMO_CODE_BOB)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_COUPON;

        // name
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditCouponDescriptor descriptor =
                new EditCouponDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // promoCode
        userInput = targetIndex.getOneBased() + PROMO_CODE_DESC_AMY;
        descriptor = new EditCouponDescriptorBuilder().withPromoCode(VALID_PROMO_CODE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // limit
        userInput = targetIndex.getOneBased() + LIMIT_DESC_AMY;
        descriptor = new EditCouponDescriptorBuilder().withLimit(VALID_LIMIT_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // expiry date
        userInput = targetIndex.getOneBased() + EXPIRY_DATE_DESC_AMY;
        descriptor = new EditCouponDescriptorBuilder().withExpiryDate(VALID_EXPIRY_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + START_DATE_DESC_AMY;
        descriptor = new EditCouponDescriptorBuilder().withStartDate(VALID_START_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditCouponDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // condition
        userInput = targetIndex.getOneBased() + CONDITION_DESC_AMY;
        descriptor = new EditCouponDescriptorBuilder().withCondition(VALID_CONDITION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editUsageField_failure() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_COUPON;
        String userInput = targetIndex.getOneBased() + USAGE_DESC_AMY;

        CommandParserTestUtil.assertParseFailure(parser, userInput, EditCommand.MESSAGE_CANNOT_EDIT_USAGE);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_COUPON;
        String userInput = targetIndex.getOneBased() + PROMO_CODE_DESC_AMY
                + TAG_DESC_FRIEND + PROMO_CODE_DESC_AMY + TAG_DESC_FRIEND
                + PROMO_CODE_DESC_BOB + TAG_DESC_HUSBAND;

        EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder().withPromoCode(VALID_PROMO_CODE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_COUPON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_integerOverflow_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser,
                Long.toString(Integer.MAX_VALUE + 1L),
                String.format(
                        ParserUtil.MESSAGE_INDEX_OVERFLOW + "\n\n"
                                + MESSAGE_INVALID_COMMAND_FORMAT,
                        EditCommand.MESSAGE_USAGE
                )
        );
    }
}
