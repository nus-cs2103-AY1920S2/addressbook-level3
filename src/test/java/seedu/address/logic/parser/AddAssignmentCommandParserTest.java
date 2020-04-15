package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WORKLOAD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKLOAD_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.WORKLOAD_DESC_CS2103;
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
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_CS2103 + DEADLINE_DESC_CS2103
                + WORKLOAD_DESC_CS2103, new AddAssignmentCommand(expectedAssignment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_CS2103 + DEADLINE_DESC_CS2103 + WORKLOAD_DESC_CS2103,
                expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, TITLE_DESC_CS2103 + VALID_DEADLINE_CS2103 + WORKLOAD_DESC_CS2103,
                expectedMessage);

        // missing workload prefix
        assertParseFailure(parser, TITLE_DESC_CS2103 + DEADLINE_DESC_CS2103 + VALID_WORKLOAD_CS2103,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_CS2103 + VALID_DEADLINE_CS2103 + VALID_WORKLOAD_CS2103,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + DEADLINE_DESC_CS2103 + WORKLOAD_DESC_CS2103,
                Title.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, TITLE_DESC_CS2103 + INVALID_DEADLINE_DESC + WORKLOAD_DESC_CS2103,
                Deadline.MESSAGE_CONSTRAINTS);

        // invalid workload
        assertParseFailure(parser, TITLE_DESC_CS2103 + DEADLINE_DESC_CS2103 + INVALID_WORKLOAD_DESC,
                Workload.MESSAGE_CONSTRAINTS);
    }
}
