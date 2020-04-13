package seedu.address.logic.parser.wallet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWallet.ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.AMOUNT_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.DATE_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.DESC_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.TAG_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.VALID_AMOUNT_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.VALID_DESC_ALLOWANCE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wallet.WalletIncomeCommand;
import seedu.address.model.transaction.Income;
import seedu.address.testutil.TransactionBuilder;

public class WalletIncomeCommandParserTest {
    private WalletIncomeCommandParser parser = new WalletIncomeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, DESC_ALLOWANCE + AMOUNT_ALLOWANCE + DATE_ALLOWANCE + TAG_ALLOWANCE,
                new WalletIncomeCommand(ALLOWANCE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Income expectedIncome = new TransactionBuilder().withDescription(VALID_DESC_ALLOWANCE)
                .withAmount(VALID_AMOUNT_ALLOWANCE).buildIncome();
        // no date, no tag
        assertParseSuccess(parser, DESC_ALLOWANCE + AMOUNT_ALLOWANCE, new WalletIncomeCommand(expectedIncome));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletIncomeCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, AMOUNT_ALLOWANCE, expectedMessage);

        // missing amount
        assertParseFailure(parser, DESC_ALLOWANCE, expectedMessage);

        // missing prefixes
        assertParseFailure(parser, VALID_DESC_ALLOWANCE + VALID_AMOUNT_ALLOWANCE, expectedMessage);
    }
}
