package seedu.address.logic.parser.lesson;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_A;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CODE123;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.lesson.LessonDeleteCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.LessonBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the LessonDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the LessonDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class LessonDeleteCommandParserTest {
    private LessonDeleteCommandParser parser = new LessonDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, (INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE
                        + " " + VALID_MODULE_CODE_CS2103T),
                new LessonDeleteCommand(INDEX_FIRST, new ModuleCode(VALID_MODULE_CODE_CS2103T)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, (INVALID_INDEX_A + " " + PREFIX_MODULE_CODE + " "
                + VALID_MODULE_CODE_CS2103T), MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        assertParseFailure(parser, (INDEX_FIRST.getOneBased() + " " + PREFIX_MODULE_CODE + " "
                + INVALID_MODULE_CODE_CODE123), ModuleCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleModuleCode_throwsException() {
        String userInput = " 1 " + PREFIX_MODULE_CODE + " " + LessonBuilder.DEFAULT_MODULE_CODE + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2103T + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS1101S;
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));
    }
}
