package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DATETIME_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.jelphabot.logic.commands.CommandTestUtil.MODULE_CODE_DESC_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.MODULE_CODE_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.TAG_DESC_GRADED;
import static seedu.jelphabot.logic.commands.CommandTestUtil.TAG_DESC_PROJECT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DATETIME_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.jelphabot.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.EditCommand;
import seedu.jelphabot.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION_TUTORIAL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "!" + DESCRIPTION_DESC_TUTORIAL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "?" + DESCRIPTION_DESC_TUTORIAL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid desc
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);
        // invalid datetime
        assertParseFailure(parser, 1 + INVALID_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS);
        // invalid module code
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(
            parser,
            "1" + TAG_DESC_GRADED + TAG_DESC_PROJECT + TAG_EMPTY,
            Tag.MESSAGE_CONSTRAINTS
        );
        assertParseFailure(
            parser,
            "1" + TAG_DESC_GRADED + TAG_EMPTY + TAG_DESC_PROJECT,
            Tag.MESSAGE_CONSTRAINTS
        );
        assertParseFailure(
            parser,
            "1" + TAG_EMPTY + TAG_DESC_GRADED + TAG_DESC_PROJECT,
            Tag.MESSAGE_CONSTRAINTS
        );

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(
            parser,
            "1" + INVALID_DESCRIPTION_DESC + INVALID_MODULE_CODE_DESC + INVALID_TAG_DESC,
            Description.MESSAGE_CONSTRAINTS
        );
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput =
            targetIndex.getOneBased() + TAG_DESC_PROJECT + MODULE_CODE_DESC_TUTORIAL + DESCRIPTION_DESC_TUTORIAL
                + TAG_DESC_GRADED;

        EditCommand.EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withModuleCode(VALID_MODULE_CODE_TUTORIAL)
                .withTags(VALID_TAG_GRADED, VALID_TAG_PROJECT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_TUTORIAL;

        EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_TUTORIAL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // method should only test fields corresponding to isSameTask
        // description
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_TUTORIAL;
        EditCommand.EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module code
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_TUTORIAL;
        descriptor = new EditTaskDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_TUTORIAL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // datetime
        userInput = targetIndex.getOneBased() + DATETIME_DESC_TUTORIAL;
        descriptor = new EditTaskDescriptorBuilder().withDateTime(VALID_DATETIME_TUTORIAL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput =
            targetIndex.getOneBased() + MODULE_CODE_DESC_TUTORIAL + TAG_DESC_GRADED
                + MODULE_CODE_DESC_TUTORIAL + TAG_DESC_GRADED
                + MODULE_CODE_DESC_ASSIGNMENT + TAG_DESC_PROJECT;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                                            .withModuleCode(VALID_MODULE_CODE_ASSIGNMENT)
                                            .withTags(VALID_TAG_PROJECT, VALID_TAG_GRADED)
                                            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + "";
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().build();
        assertParseFailure(parser, userInput, MESSAGE_NOT_EDITED);

        // other valid values specified
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_ASSIGNMENT;
        descriptor = new EditTaskDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_ASSIGNMENT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
