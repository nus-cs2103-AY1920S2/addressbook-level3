package seedu.address.logic.parser.facilitator;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.facilitator.FacilDeleteCommand;
import seedu.address.model.facilitator.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FacilDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FacilDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FacilDeleteCommandParserTest {
    private FacilDeleteCommandParser parser = new FacilDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new FacilDeleteCommand(INDEX_FIRST));
        assertParseSuccess(parser, "Akshay", new FacilDeleteCommand(new Name("Akshay")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);

    }
}
