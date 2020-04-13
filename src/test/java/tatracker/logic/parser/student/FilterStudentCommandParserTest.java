//@@author Chuayijing
package tatracker.logic.parser.student;

import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.student.FilterStudentCommand;

public class FilterStudentCommandParserTest {


    private FilterStudentCommandParser parser = new FilterStudentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMsg = Messages.getInvalidCommandMessage(FilterStudentCommand.DETAILS.getUsage());
        assertParseFailure(parser, "" + "", expectedMsg);
    }

    @Test
    void parse_wrongPrefix_promptsInvalidCommandFormat() {
        String expectedMsg = Messages.getInvalidCommandMessage(FilterStudentCommand.DETAILS.getUsage());

        //matric as prefix
        assertParseFailure(parser, VALID_MATRIC_AMY, expectedMsg);

        //name as prefix
        assertParseFailure(parser, VALID_NAME_AMY, expectedMsg);

        //phone as prefix
        assertParseFailure(parser, VALID_PHONE_AMY, expectedMsg);

        //email as prefix
        assertParseFailure(parser, VALID_EMAIL_AMY, expectedMsg);

        //rating as prefix
        assertParseFailure(parser, "2", expectedMsg);

        //tag as prefix
        assertParseFailure(parser, VALID_TAG_FRIEND, expectedMsg);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMsg = Messages.getInvalidCommandMessage(FilterStudentCommand.DETAILS.getUsage());

        //missing module group prefix
        assertParseFailure(parser, "g/G06", expectedMsg);

    }

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedModule = "CS3243";
        String expectedGroupCode = "G06";

        //whitespace only preamble
        assertParseSuccess(parser, " m/CS3243 g/G06",
                 new FilterStudentCommand(expectedModule, expectedGroupCode));

        //different order
        assertParseSuccess(parser, " m/CS3243 g/G06",
                new FilterStudentCommand(expectedModule, expectedGroupCode));
    }

}
