package seedu.address.logic.parser.lesson;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_SUNNYDAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_WITH_STRING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_TYPE_FREE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CODE123;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_SUNDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_REC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_SEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_GEQ1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_HOME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_PARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.lesson.LessonEditCommand;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;

public class LessonEditCommandParserTest {
    private LessonEditCommandParser parser = new LessonEditCommandParser();

    @Test
    public void parse_missingCompulsoryFields_throwsException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonEditCommand.MESSAGE_USAGE);

        // missing index
        String userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        //missing module code
        userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_TYPE + " " + VALID_LESSON_TYPE_SEC + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing all optional parts
        userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " ";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidPreamble_throwsException() {
        String expectedMessage = MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
        String userInput = " 0 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // negative index
        userInput = " -5 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // additional invalid arguments in index
        userInput = INVALID_INDEX_WITH_STRING + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // invalid prefix
        userInput = INVALID_INDEX_WITH_PREFIX + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " ";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidValue_throwsException() {
        // invalid module code
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + INVALID_MODULE_CODE_CODE123 + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " ";
        assertParseFailure(parser, userInput, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid type
        userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + INVALID_LESSON_TYPE_FREE + " ";
        assertParseFailure(parser, userInput, LessonType.MESSAGE_CONSTRAINTS);

        // invalid day and time
        userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_AT + " " + INVALID_TIME;
        assertParseFailure(parser, userInput, DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);
        userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_AT + " " + INVALID_DAY_SUNNYDAY;
        assertParseFailure(parser, userInput, DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_TYPE + " " + VALID_LESSON_TYPE_REC + " "
                + PREFIX_AT + " " + VALID_DAY_AND_TIME_SUNDAY + " "
                + PREFIX_VENUE + " " + VALID_VENUE_HOME;
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .withLessonType(VALID_LESSON_TYPE_REC)
                .withDayAndTime(VALID_DAY_AND_TIME_SUNDAY)
                .withVenue(VALID_VENUE_HOME).build();
        LessonEditCommand command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // module only
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " ";

        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .build();

        LessonEditCommand command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);

        // lesson type only
        userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + VALID_LESSON_TYPE_REC + " ";

        descriptor = new EditLessonDescriptorBuilder()
                .withLessonType(VALID_LESSON_TYPE_REC).build();

        command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);

        // day and time only
        userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_AT + VALID_DAY_AND_TIME_SUNDAY;

        descriptor = new EditLessonDescriptorBuilder()
                .withDayAndTime(VALID_DAY_AND_TIME_SUNDAY).build();

        command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);

        // venue only
        userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_VENUE + " " + VALID_VENUE_HOME;

        descriptor = new EditLessonDescriptorBuilder()
                .withVenue(VALID_VENUE_HOME).build();

        command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " " + PREFIX_TYPE
                + " " + VALID_LESSON_TYPE_SEC;

        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .withLessonType(VALID_LESSON_TYPE_SEC).build();

        LessonEditCommand command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_multipleType_throwsException() {
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + VALID_LESSON_TYPE_REC + " "
                + PREFIX_TYPE + " " + VALID_LESSON_TYPE_SEC;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_TYPE));
    }

    @Test
    public void parse_multipleModuleCode_throwsException() {
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " "
                + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + VALID_LESSON_TYPE_REC;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "two", PREFIX_MODULE_CODE));
    }

    @Test
    public void parse_multipleDayAndTime_throwsException() {
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_AT + " " + VALID_DAY_AND_TIME_SUNDAY + " "
                + PREFIX_AT + " " + VALID_DAY_AND_TIME_MONDAY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));
    }

    @Test
    public void parse_multipleVenue_throwsException() {
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_VENUE + " " + VALID_VENUE_HOME + " "
                + PREFIX_VENUE + " " + VALID_VENUE_PARK;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_VENUE));
    }

    @Test
    public void parse_resetVenue_success() {
        String userInput = " " + INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " " + PREFIX_VENUE
                + " ";
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withVenue("").build();
        LessonEditCommand command = new LessonEditCommand(new ModuleCode(LessonBuilder.DEFAULT_MODULE_CODE),
                INDEX_FIRST, descriptor);
        assertParseSuccess(parser, userInput, command);
    }
}
