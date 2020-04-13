package seedu.nova.logic.parser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbDeleteCommand;
import seedu.nova.logic.parser.abparsers.AbDeleteCommandParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the AbDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the AbDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class AbDeleteCommandParserTest {

    private AbDeleteCommandParser parser = new AbDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " i\\1", new AbDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AbDeleteCommand.MESSAGE_USAGE));
    }
}
