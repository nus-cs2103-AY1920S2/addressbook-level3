package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DATE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DATE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_PROGRAMMING;
import static seedu.address.logic.commands.CommandTestUtil.TASK_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.TASK_TIME_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TASK_TIME_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROGRAMMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskAddCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;
import seedu.address.testutil.NonScheduledTaskBuilder;
import seedu.address.testutil.ScheduledTaskBuilder;

public class TaskAddCommandParserTest {
    private TaskAddCommandParser parser = new TaskAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new ScheduledTaskBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S)
                .withDescription(VALID_TASK_DESCRIPTION_PROGRAMMING)
                .withTaskDateTime(TASK_DATE_1, TASK_TIME_1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING
                + TASK_DATE_DESC_1 + TASK_TIME_DESC_1, new TaskAddCommand(expectedTask));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        // missing date time
        Task expectedTask = new NonScheduledTaskBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S)
                .withDescription(VALID_TASK_DESCRIPTION_PROGRAMMING).build();
        assertParseSuccess(parser, MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING,
                new TaskAddCommand(expectedTask));
    }

    @Test
    public void parse_tooManyArguments_failure() {
        // multiple module codes
        assertParseFailure(parser, MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_CS1101S
                + TASK_DESCRIPTION_DESC_PROGRAMMING,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));

        // multiple descriptions
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING
                + TASK_DESCRIPTION_DESC_HOMEWORK, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));

        // multiple dates
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING
                + TASK_DATE_DESC_1 + TASK_DATE_DESC_2, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_ON));

        // multiple times
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING
                + TASK_DATE_DESC_1 + TASK_TIME_DESC_1 + TASK_TIME_DESC_2,
                String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_MODULE_CODE_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + VALID_TASK_DESCRIPTION_PROGRAMMING, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODULE_CODE_CS1101S + VALID_TASK_DESCRIPTION_PROGRAMMING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + TASK_DESCRIPTION_DESC_PROGRAMMING,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING + INVALID_DATE_DESC,
                TaskDateTime.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING + INVALID_DATE_DESC
                + INVALID_TIME_DESC, TaskDateTime.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + INVALID_DESCRIPTION_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_DESC_CS1101S + TASK_DESCRIPTION_DESC_PROGRAMMING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE));
    }
}
