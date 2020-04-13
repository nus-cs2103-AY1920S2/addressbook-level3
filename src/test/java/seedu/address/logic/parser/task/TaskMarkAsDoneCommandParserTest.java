package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ID_DESC_VALID_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_FIRST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskMarkAsDoneCommand;

class TaskMarkAsDoneCommandParserTest {
    private TaskMarkAsDoneCommandParser parser = new TaskMarkAsDoneCommandParser();

    @Test
    public void parse_withPreamble_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS1101S + TASK_ID_DESC_VALID_ID,
                new TaskMarkAsDoneCommand(VALID_MODULE_CODE_CS1101S, VALID_TASK_ID_FIRST));

        // arbitrary preamble
        assertParseSuccess(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_DESC_CS1101S + TASK_ID_DESC_VALID_ID,
                new TaskMarkAsDoneCommand(VALID_MODULE_CODE_CS1101S, VALID_TASK_ID_FIRST));

        // the rest of the methods below will not test the presence of Preamble
        // since we already tested it here.
    }

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, MODULE_CODE_DESC_CS2103T + TASK_ID_DESC_VALID_ID,
                new TaskMarkAsDoneCommand(VALID_MODULE_CODE_CS2103T, VALID_TASK_ID_FIRST));
    }

    @Test
    public void parse_oneOrBothFieldsMissing_failure() {
        // apply heuristic ‘no more than one invalid input in a test case’

        // first invalid field is ModuleCode
        String expectedMessageMissingModCode = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TaskMarkAsDoneCommand.MESSAGE_MODULE_CODE_NOT_EXISTENT);

        assertParseFailure(parser, TASK_ID_DESC_VALID_ID, expectedMessageMissingModCode);

        // moduleCode is present, but Task ID is not
        String expectedMessageMissingTaskId = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TaskMarkAsDoneCommand.MESSAGE_TASK_ID_NOT_EXISTENT);

        assertParseFailure(parser, MODULE_CODE_DESC_CS2103T, expectedMessageMissingTaskId);

        // both fields are not present, prompt user for command usage guide
        String emptyArgumentsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TaskMarkAsDoneCommand.MESSAGE_USAGE);

        assertParseFailure(parser, EMPTY_ARGUMENTS, emptyArgumentsMessage);

    }
}
