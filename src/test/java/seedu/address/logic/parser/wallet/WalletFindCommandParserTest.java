package seedu.address.logic.parser.wallet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_KEYWORD_NOT_FOUND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wallet.WalletFindCommand;
import seedu.address.model.transaction.AmountContainsKeywordsPredicate;
import seedu.address.model.transaction.DateContainsKeywordsPredicate;
import seedu.address.model.transaction.DescriptionContainsKeywordsPredicate;
import seedu.address.model.transaction.TagContainsKeywordsPredicate;

public class WalletFindCommandParserTest {
    private WalletFindCommandParser parser = new WalletFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                WalletFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        assertParseFailure(parser, " chicken", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                WalletFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_moreThanOneParameter_throwsParseException() {
        assertParseFailure(parser, " n/Chicken $/5",
                String.format(WalletFindCommand.ONLY_ONE_PARAMETER_ALLOWED, WalletFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noKeyword_throwsParseException() {
        assertParseFailure(parser, " n/",
                String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " $/",
                String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " d/",
                String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/",
                String.format(MESSAGE_KEYWORD_NOT_FOUND, WalletFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_amountContainsDecimal_throwsParseExpection() {
        assertParseFailure(parser, " $/5.50",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletFindCommand.WRONG_AMT));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        WalletFindCommand expectedWalletFindCommand =
                new WalletFindCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("Chicken", "Duck")));
        assertParseSuccess(parser, " n/Chicken Duck", expectedWalletFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Chicken \n \t Duck  \t", expectedWalletFindCommand);
    }

    @Test
    public void parse_prefixName_returnsFindCommand() {
        // no leading and trailing whitespaces
        WalletFindCommand expectedWalletFindCommand =
                new WalletFindCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("Chicken", "Laksa")));
        assertParseSuccess(parser, " n/Chicken Laksa", expectedWalletFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Chicken \n \t Laksa  \t", expectedWalletFindCommand);
    }

    @Test
    public void parse_prefixAmount_returnsFindCommand() {
        // no leading and trailing whitespaces
        WalletFindCommand expectedWalletFindCommand =
                new WalletFindCommand(new AmountContainsKeywordsPredicate(Arrays.asList("5", "10")));
        assertParseSuccess(parser, " $/5 10", expectedWalletFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " $/ \n 5 \n \t 10  \t", expectedWalletFindCommand);
    }

    @Test
    public void parse_prefixDate_returnsFindCommand() {
        // no leading and trailing whitespaces
        WalletFindCommand expectedWalletFindCommand =
                new WalletFindCommand(new DateContainsKeywordsPredicate(Arrays.asList("13/02/2020",
                        "12/01/2020")));
        assertParseSuccess(parser, " d/13/02/2020 12/01/2020", expectedWalletFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " d/ \n 13/02/2020 \n \t 12/01/2020  \t", expectedWalletFindCommand);
    }

    @Test
    public void parse_prefixTag_returnsFindCommand() {
        // no leading and trailing whitespaces
        WalletFindCommand expectedWalletFindCommand =
                new WalletFindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friend", "colleague")));
        assertParseSuccess(parser, " t/friend colleague", expectedWalletFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " t/ \n friend \n \t colleague  \t", expectedWalletFindCommand);
    }

}
