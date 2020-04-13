package seedu.address.logic.parser.lesson;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_SUNNYDAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_TYPE_FREE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CODE123;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_GEQ1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_HOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.lesson.LessonAddCommand;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.LessonBuilder;

public class LessonAddCommandParserTest {
    private LessonAddCommandParser parser = new LessonAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder().build();
        String userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " " + PREFIX_VENUE
                + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + userInput, new LessonAddCommand(expectedLesson));
        assertParseSuccess(parser, userInput, new LessonAddCommand(expectedLesson));
    }

    @Test
    public void parse_multipleArguments_throwsException() {
        // multiple module codes
        String userInput = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_GEQ1000 + " " + PREFIX_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME
                + " " + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));

        // multiple day and time
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + VALID_DAY_AND_TIME_MONDAY + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";

        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));

        // multiple venues
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " " + PREFIX_VENUE + " " + VALID_VENUE_HOME + " "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_VENUE));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        Lesson lesson = new LessonBuilder().withVenue(null).build();
        String userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " ";
        assertParseSuccess(parser, userInput, new LessonAddCommand(lesson));
    }

    @Test
    public void parse_compulsoryFieldMissingFailure_throwsException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonAddCommand.MESSAGE_USAGE);

        // missing module code prefix
        String userInput = " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing lesson type prefix
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing day and time prefix
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " " + PREFIX_VENUE
                + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, expectedMessage);

        // missing day
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " 14:00 16:00 "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);

        // missing time
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " MONDAY 14:00 "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);

        // missing all prefix
        userInput = " " + LessonBuilder.DEFAULT_MODULE_CODE
                + " " + LessonBuilder.DEFAULT_LESSON_TYPE
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidValue_throwsException() {
        // invalid module code
        String userInput = " " + PREFIX_MODULE_CODE + " " + INVALID_MODULE_CODE_CODE123 + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " " + PREFIX_VENUE + " "
                + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid lesson type
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + INVALID_LESSON_TYPE_FREE + " " + PREFIX_AT
                + " " + LessonBuilder.DEFAULT_DAY_AND_TIME + " " + PREFIX_VENUE + " "
                + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, LessonType.MESSAGE_CONSTRAINTS);

        // invalid day
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT
                + " " + INVALID_DAY_SUNNYDAY + " " + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);

        // invalid time
        userInput = " " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_TYPE + " " + LessonBuilder.DEFAULT_LESSON_TYPE + " " + PREFIX_AT + " "
                + INVALID_TIME + " "
                + PREFIX_VENUE + " " + LessonBuilder.DEFAULT_VENUE + " ";
        assertParseFailure(parser, userInput, DayAndTime.MESSAGE_CONSTRAINTS_DAY_AND_TIME);
    }
}
