package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WORKLOAD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS3243;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKLOAD_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.WORKLOAD_DESC_CS2103;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;
import static seedu.address.testutil.TypicalIndexes.THIRD_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditAssignmentDescriptor;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Status;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE);

    private EditAssignmentCommandParser parser = new EditAssignmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TITLE_DESC_CS2103, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditAssignmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_CS2103, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_CS2103, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 u/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_WORKLOAD_DESC, Workload.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);

        // invalid title followed by valid deadline
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + DEADLINE_DESC_CS2103, Title.MESSAGE_CONSTRAINTS);

        // valid title followed by invalid title. The test case for invalid title followed by valid title
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TITLE_DESC_CS2103 + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_WORKLOAD_DESC + VALID_DEADLINE_CS2103,
            Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = SECOND_INDEX;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_CS2103 + WORKLOAD_DESC_CS2103 + STATUS_DESC_CS2103
            + DEADLINE_DESC_CS2103;

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS2103)
            .withDeadline(VALID_DEADLINE_CS2103).withStatus(VALID_STATUS_CS2103)
            .withWorkload(VALID_WORKLOAD_CS2103).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = FIRST_INDEX;
        String userInput = targetIndex.getOneBased() + DEADLINE_DESC_CS2103 + STATUS_DESC_CS2103;

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withDeadline(VALID_DEADLINE_CS2103)
            .withStatus(VALID_STATUS_CS2103).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = THIRD_INDEX;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_CS2103;
        EditAssignmentDescriptor descriptor =
            new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS2103).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_CS2103;
        descriptor = new EditAssignmentDescriptorBuilder().withDeadline(VALID_DEADLINE_CS2103).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // workload
        userInput = targetIndex.getOneBased() + WORKLOAD_DESC_CS2103;
        descriptor = new EditAssignmentDescriptorBuilder().withWorkload(VALID_WORKLOAD_CS2103).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC_CS2103;
        descriptor = new EditAssignmentDescriptorBuilder().withStatus(VALID_STATUS_CS2103).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = FIRST_INDEX;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_CS2103 + DEADLINE_DESC_CS2103 + TITLE_DESC_CS3243
            + DEADLINE_DESC_CS3243;

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS3243)
            .withDeadline(VALID_DEADLINE_CS3243).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = FIRST_INDEX;
        String userInput = targetIndex.getOneBased() + INVALID_DEADLINE_DESC + DEADLINE_DESC_CS3243;
        EditAssignmentDescriptor descriptor =
            new EditAssignmentDescriptorBuilder().withDeadline(VALID_DEADLINE_CS3243).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_CS2103 + INVALID_TITLE_DESC + STATUS_DESC_CS2103
            + TITLE_DESC_CS2103;
        descriptor = new EditAssignmentDescriptorBuilder().withDeadline(VALID_DEADLINE_CS2103)
            .withStatus(VALID_STATUS_CS2103).withTitle(VALID_TITLE_CS2103).build();
        expectedCommand = new EditAssignmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
