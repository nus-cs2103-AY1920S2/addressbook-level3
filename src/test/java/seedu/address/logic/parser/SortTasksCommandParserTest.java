package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SORTING_PARAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.taskcommand.sortcommand.SortTasksCommand;

class SortTasksCommandParserTest {

    private SortTasksCommandParser parser = new SortTasksCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        String expectedSortingParam1 = "priority";

        assertParseSuccess(parser, " by/priority", new SortTasksCommand(expectedSortingParam1));

        String expectedSortingParam2 = "date";

        assertParseSuccess(parser, " by/date", new SortTasksCommand(expectedSortingParam2));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTasksCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " lhjjqs", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        //invalid sorting parameter
        assertParseFailure(parser, " by/dadsa",
                MESSAGE_INVALID_SORTING_PARAM + " " + SortTasksCommand.MESSAGE_USAGE);

        //empty sorting parameter
        assertParseFailure(parser, " by/",
                MESSAGE_INVALID_SORTING_PARAM + " " + SortTasksCommand.MESSAGE_USAGE);
    }


}
