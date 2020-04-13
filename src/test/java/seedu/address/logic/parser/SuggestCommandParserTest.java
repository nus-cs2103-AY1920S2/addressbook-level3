package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SuggestCommand;

public class SuggestCommandParserTest {
    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser, "some invalid string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSuggestPersonCommand() {
        // no leading and trailing whitespaces
        SuggestCommand expectedSuggestCommand =
                new SuggestCommand("person");
        assertParseSuccess(parser, " person", expectedSuggestCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " person", expectedSuggestCommand);
    }

    @Test
    public void parse_validArgs_returnsSuggestPlaceCommand() {
        // no leading and trailing whitespaces
        SuggestCommand expectedSuggestCommand =
                new SuggestCommand("place");
        assertParseSuccess(parser, " place", expectedSuggestCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  place   ", expectedSuggestCommand);
    }

    @Test
    public void parse_validArgs_returnsSuggestActivityCommand() {
        // no leading and trailing whitespaces
        SuggestCommand expectedSuggestCommand =
                new SuggestCommand("activity");
        assertParseSuccess(parser, " activity", expectedSuggestCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " activity   ", expectedSuggestCommand);
    }


}
