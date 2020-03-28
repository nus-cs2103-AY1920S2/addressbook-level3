package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ESTHOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ESTHOURS_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ESTHOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAssignments.CS2103_TP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder(CS2103_TP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_TITLE_DESC + VALID_DEADLINE_DESC
                + VALID_ESTHOURS_DESC, new AddAssignmentCommand(expectedAssignment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_CS2103 + VALID_DEADLINE_DESC + VALID_ESTHOURS_DESC,
                expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, VALID_TITLE_DESC + VALID_DEADLINE_CS2103 + VALID_ESTHOURS_DESC,
                expectedMessage);

        // missing workload prefix
        assertParseFailure(parser, VALID_TITLE_DESC + VALID_DEADLINE_DESC + VALID_ESTHOURS_CS2103,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_CS2103 + VALID_DEADLINE_CS2103 + VALID_ESTHOURS_CS2103,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + VALID_DEADLINE_DESC + VALID_ESTHOURS_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, VALID_TITLE_DESC + INVALID_DEADLINE_DESC + VALID_ESTHOURS_DESC,
                Deadline.MESSAGE_CONSTRAINTS);

        // invalid workload
        assertParseFailure(parser, VALID_TITLE_DESC + VALID_DEADLINE_DESC + INVALID_ESTHOURS_DESC,
                Workload.MESSAGE_CONSTRAINTS);
    }
}
