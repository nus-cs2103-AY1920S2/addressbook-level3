package seedu.address.logic.parser.wallet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWallet.AMOUNT_DUCK;
import static seedu.address.testutil.TypicalWallet.DATE_DUCK;
import static seedu.address.testutil.TypicalWallet.DESC_DUCK;
import static seedu.address.testutil.TypicalWallet.DUCK_RICE;
import static seedu.address.testutil.TypicalWallet.TAG_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_AMOUNT_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_DESC_DUCK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wallet.WalletExpenseCommand;
import seedu.address.model.transaction.Expense;
import seedu.address.testutil.TransactionBuilder;

public class WalletExpenseCommandParserTest {
    private WalletExpenseCommandParser parser = new WalletExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, DESC_DUCK + AMOUNT_DUCK + DATE_DUCK + TAG_DUCK, new WalletExpenseCommand(DUCK_RICE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Expense expectedExpense = new TransactionBuilder().withDescription(VALID_DESC_DUCK)
                .withAmount(VALID_AMOUNT_DUCK).buildExpense();
        // no date, no tag
        assertParseSuccess(parser, DESC_DUCK + AMOUNT_DUCK, new WalletExpenseCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletExpenseCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, AMOUNT_DUCK, expectedMessage);

        // missing amount
        assertParseFailure(parser, DESC_DUCK, expectedMessage);

        // missing prefixes
        assertParseFailure(parser, VALID_DESC_DUCK + VALID_AMOUNT_DUCK, expectedMessage);
    }
}
