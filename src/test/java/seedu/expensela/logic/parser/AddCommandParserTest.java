package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.expensela.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.expensela.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.expensela.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.expensela.testutil.TypicalTransactions.AMY;
import static seedu.expensela.testutil.TypicalTransactions.BOB;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.testutil.TransactionBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedTransaction));

        // multiple tags - all accepted
        Transaction expectedTransactionMultipleTags = new TransactionBuilder(BOB)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedTransactionMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Transaction expectedTransaction = new TransactionBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedTransaction));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Amount.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
