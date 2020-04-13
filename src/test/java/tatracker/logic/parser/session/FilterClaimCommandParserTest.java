//@@author Chuayijing

package tatracker.logic.parser.session;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.session.FilterClaimCommand;

public class FilterClaimCommandParserTest {

    private static final String COMMAND_WITH_WRONG_PREFIX_START = "s/14:00";
    private static final String COMMAND_WITH_WRONG_PREFIX_END = "e/19:00";
    private static final String COMMAND_WITH_WRONG_PREFIX_RECUR = "w/2";
    private static final String COMMAND_WITH_WRONG_PREFIX_NOTES = "n/notes";
    private static final String COMMAND_WITH_WRONG_PREFIX_SESSIONTYPE = "t/lab";
    private static final String COMMAND_WITH_WRONG_PREFIX_DATE = "d/2020-06-20";

    private FilterClaimCommandParser parser = new FilterClaimCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMsg = Messages.getInvalidCommandMessage(FilterClaimCommand.DETAILS.getUsage());
        assertParseFailure(parser, "", expectedMsg);
    }

    @Test
    void parse_wrongPrefix_promptsInvalidCommandFormat() {
        String expectedMsg = Messages.getInvalidCommandMessage(FilterClaimCommand.DETAILS.getUsage());

        //start time as prefix
        assertParseFailure(parser, COMMAND_WITH_WRONG_PREFIX_START, expectedMsg);

        //end time as prefix
        assertParseFailure(parser, COMMAND_WITH_WRONG_PREFIX_END, expectedMsg);

        //recurring period as prefix
        assertParseFailure(parser, COMMAND_WITH_WRONG_PREFIX_RECUR, expectedMsg);

        //notes as prefix
        assertParseFailure(parser, COMMAND_WITH_WRONG_PREFIX_NOTES, expectedMsg);

        //sessiontype as prefix
        assertParseFailure(parser, COMMAND_WITH_WRONG_PREFIX_SESSIONTYPE, expectedMsg);

        //date as prefix
        assertParseFailure(parser, COMMAND_WITH_WRONG_PREFIX_DATE, expectedMsg);
    }
}
