package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DATETIME_DESC_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DATETIME_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.MODULE_CODE_DESC_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.MODULE_CODE_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.jelphabot.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jelphabot.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.TAG_DESC_GRADED;
import static seedu.jelphabot.logic.commands.CommandTestUtil.TAG_DESC_PROJECT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DATETIME_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jelphabot.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.jelphabot.testutil.TypicalTasks.TUTORIAL;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.logic.commands.AddCommand;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(ASSIGNMENT).withTags(VALID_TAG_GRADED).build();

        // whitespace only preamble
        assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            new AddCommand(expectedTask)
        );

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_TUTORIAL + DESCRIPTION_DESC_ASSIGNMENT
                                       + DATETIME_DESC_ASSIGNMENT + MODULE_CODE_DESC_ASSIGNMENT
                                       + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            new AddCommand(expectedTask)
        );

        // multiple datetime - last datetime accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_TUTORIAL
                                       + DATETIME_DESC_ASSIGNMENT + MODULE_CODE_DESC_ASSIGNMENT
                                       + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            new AddCommand(expectedTask)
        );

        // multiple module codes - last module code accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_TUTORIAL + MODULE_CODE_DESC_ASSIGNMENT
                                       + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            new AddCommand(expectedTask)
        );

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_TUTORIAL
                                       + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            new AddCommand(expectedTask)
        );

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(ASSIGNMENT).withTags(VALID_TAG_PROJECT, VALID_TAG_GRADED)
                                            .build();
        assertParseSuccess(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED
                                       + TAG_DESC_PROJECT,
            new AddCommand(expectedTaskMultipleTags)
        );
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no priority - defaults to medium priority
        Task expectedTask = new TaskBuilder(TUTORIAL).withTags(VALID_TAG_PROJECT).build();
        assertParseSuccess(parser, DESCRIPTION_DESC_TUTORIAL + DATETIME_DESC_TUTORIAL
                                       + MODULE_CODE_DESC_TUTORIAL + TAG_DESC_PROJECT,
            new AddCommand(expectedTask)
        );

        // zero tags
        expectedTask = new TaskBuilder(TUTORIAL)
                           .withPriority(VALID_PRIORITY_TUTORIAL)
                           .withTags().build();
        assertParseSuccess(parser, DESCRIPTION_DESC_TUTORIAL + DATETIME_DESC_TUTORIAL
                                       + MODULE_CODE_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL,
            new AddCommand(expectedTask)
        );
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_DESCRIPTION_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            expectedMessage
        );

        // missing datetime prefix
        assertParseFailure(parser, DESCRIPTION_DESC_ASSIGNMENT + VALID_DATETIME_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            expectedMessage
        );

        // missing module code prefix
        assertParseFailure(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + VALID_MODULE_CODE_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            expectedMessage
        );

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION_ASSIGNMENT + VALID_DATETIME_ASSIGNMENT
                                       + VALID_MODULE_CODE_ASSIGNMENT,
            expectedMessage
        );
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            Description.MESSAGE_CONSTRAINTS
        );

        // invalid datetime
        assertParseFailure(parser, DESCRIPTION_DESC_ASSIGNMENT + INVALID_DATETIME_DESC
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            DateTime.MESSAGE_CONSTRAINTS
        );

        // invalid module code
        assertParseFailure(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + INVALID_MODULE_CODE_DESC + PRIORITY_DESC_ASSIGNMENT + TAG_DESC_GRADED,
            ModuleCode.MESSAGE_CONSTRAINTS
        );

        // invalid priority
        assertParseFailure(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + INVALID_PRIORITY_DESC + TAG_DESC_GRADED,
            Priority.MESSAGE_CONSTRAINTS
        );

        // invalid tag
        assertParseFailure(parser, DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                                       + INVALID_TAG_DESC,
            Tag.MESSAGE_CONSTRAINTS
        );

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
            INVALID_DESCRIPTION_DESC + DATETIME_DESC_ASSIGNMENT + INVALID_MODULE_CODE_DESC
                + PRIORITY_DESC_ASSIGNMENT + INVALID_TAG_DESC,
            Description.MESSAGE_CONSTRAINTS
        );

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DESCRIPTION_DESC_ASSIGNMENT + DATETIME_DESC_ASSIGNMENT
                                       + MODULE_CODE_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                                       + TAG_DESC_PROJECT + TAG_DESC_GRADED,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE)
        );
    }
}
