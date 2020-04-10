package csdev.couponstash.logic.parser;

import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.SetCurrencyCommand;


/**
 * Unit tests for Set Currency Command Parser.
 */
public class SetCurrencyCommandParserTest {

    private SetCurrencyCommandParser parser = new SetCurrencyCommandParser();

    @Test
    public void parse_validArgs_returnsSetCurrencyCommand() {
        assertParseSuccess(parser, " " + CliSyntax.PREFIX_MONEY_SYMBOL + "ABCDE",
                new SetCurrencyCommand("ABCDE"));
    }

    @Test
    public void parse_whiteSpace_parseExceptionThrown() {
        assertParseFailure(parser, " " + CliSyntax.PREFIX_MONEY_SYMBOL + " ",
                SetCurrencyCommand.MESSAGE_MISSING_VALUES);
    }

    @Test
    public void parse_numbersPresent_parseExceptionThrown() {
        assertParseFailure(parser, " " + CliSyntax.PREFIX_MONEY_SYMBOL + "HEL4O",
                SetCurrencyCommand.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_stringTooLong_parseExceptionThrown() {
        String invalidMoneySymbol = "Brattby";
        assertParseFailure(parser, " " + CliSyntax.PREFIX_MONEY_SYMBOL + invalidMoneySymbol,
                String.format(ParserUtil.MESSAGE_STRING_TOO_LONG, invalidMoneySymbol, 7, 5));
    }
}
