//@@author Chuayijing

package tatracker.logic.parser.session;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.session.DeleteSessionCommand;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteStudentCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteSessionCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteSessionCommandParserTest {

    private DeleteSessionCommandParser parser = new DeleteSessionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteSessionCommand() throws ParseException {
        String expectedIndex = "1";
        Index index = ParserUtil.parseIndex(expectedIndex);

        assertParseSuccess(parser, expectedIndex, new DeleteSessionCommand(index));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                Messages.getInvalidCommandMessage(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX));
    }
}
