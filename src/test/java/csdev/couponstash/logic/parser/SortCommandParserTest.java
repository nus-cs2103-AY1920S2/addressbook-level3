package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_REMIND;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;

import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static csdev.couponstash.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.SortCommand;

class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    void parse_nameOnly_success() {
        assertParseSuccess(
                parser, " " + PREFIX_NAME.toString(),
                new SortCommand(PREFIX_NAME)
        );
    }

    @Test
    void parse_remindOnly_success() {
        assertParseSuccess(
                parser, " " + PREFIX_REMIND.toString(),
                new SortCommand(PREFIX_REMIND)
        );
    }

    @Test
    void parse_expiryDateOnly_success() {
        assertParseSuccess(
                parser, " " + PREFIX_EXPIRY_DATE.toString(),
                new SortCommand(PREFIX_EXPIRY_DATE)
        );
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(
                parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)
        );
    }

    @Test
    void parse_multipleFields_failure() {
        assertParseFailure(
                parser, " " + PREFIX_NAME + " " + PREFIX_REMIND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)
        );
    }

    @Test
    void parse_invalidField_failure() {
        assertParseFailure(
                parser, " " + PREFIX_TAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)
        );
    }
}
