package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.util.DateUtil.DATE_FORMATTER;
import static csdev.couponstash.commons.util.DateUtil.MESSAGE_DATE_WRONG_FORMAT;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static csdev.couponstash.testutil.TypicalCoupons.ALICE_DATE_1;
import static csdev.couponstash.testutil.TypicalCoupons.ALICE_DATE_2;
import static csdev.couponstash.testutil.TypicalCoupons.ALICE_DATE_3;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.SavedCommand;

/**
 * Unit tests for Saved Command Parser.
 */
public class SavedCommandParserTest {

    private static DateTimeFormatter couponStashDateFormat = DATE_FORMATTER;

    private SavedCommandParser parser = new SavedCommandParser();

    @Test
    public void parse_validArgs_returnsSavedCommand() {
        // no args
        assertParseSuccess(parser, "", new SavedCommand());

        // one date
        assertParseSuccess(
                parser,
                " " + CliSyntax.PREFIX_DATE + ALICE_DATE_1.format(couponStashDateFormat),
                new SavedCommand(ALICE_DATE_1));

        // range of dates
        assertParseSuccess(
                parser,
                " " + CliSyntax.PREFIX_START_DATE + ALICE_DATE_2.format(couponStashDateFormat) + " "
                        + CliSyntax.PREFIX_EXPIRY_DATE + ALICE_DATE_3.format(couponStashDateFormat),
                new SavedCommand(ALICE_DATE_2, ALICE_DATE_3));
    }

    @Test
    public void parse_invalidDateProvided_throwsCommandException() {
        // date prefix with no argument
        assertParseFailure(parser, " " + CliSyntax.PREFIX_DATE + "",
                MESSAGE_DATE_WRONG_FORMAT);

        // start date and end date prefix without arguments
        assertParseFailure(parser, " " + CliSyntax.PREFIX_START_DATE + " " + CliSyntax.PREFIX_EXPIRY_DATE,
                MESSAGE_DATE_WRONG_FORMAT);
    }


    @Test
    public void parse_dateProvidedWithoutPrefix_throwsCommandException() {
        assertParseFailure(parser, " " + ALICE_DATE_1.format(couponStashDateFormat),
                SavedCommand.MESSAGE_UNEXPECTED_EXTRA_WORDS);
    }

    @Test
    public void parse_startDateNotBeforeEndDate_throwsCommandException() {
        // same date throws exception for range
        assertParseFailure(
                parser, " " + CliSyntax.PREFIX_START_DATE + ALICE_DATE_1.format(couponStashDateFormat)
                        + " " + CliSyntax.PREFIX_EXPIRY_DATE + ALICE_DATE_1.format(couponStashDateFormat),
                SavedCommand.MESSAGE_INVALID_DATE_RANGE);

        // start date after end date
        assertParseFailure(
                parser, " " + CliSyntax.PREFIX_START_DATE + ALICE_DATE_3.format(couponStashDateFormat)
                        + " " + CliSyntax.PREFIX_EXPIRY_DATE + ALICE_DATE_2.format(couponStashDateFormat),
                SavedCommand.MESSAGE_INVALID_DATE_RANGE);
    }

    @Test
    public void parse_startDateWithoutEndDate_throwsCommandException() {
        assertParseFailure(parser, " " + CliSyntax.PREFIX_START_DATE + ALICE_DATE_1.format(couponStashDateFormat),
                SavedCommand.MESSAGE_ONLY_ONE_DATE_OF_RANGE);
    }
}
