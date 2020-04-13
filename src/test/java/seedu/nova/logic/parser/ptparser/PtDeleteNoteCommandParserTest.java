package seedu.nova.logic.parser.ptparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_PROJECT;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TASK;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_WEEK;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.nova.logic.commands.CommandTestUtil.PROJECT_IP;
import static seedu.nova.logic.commands.CommandTestUtil.PROJECT_TP;
import static seedu.nova.logic.commands.CommandTestUtil.PT_TASK;
import static seedu.nova.logic.commands.CommandTestUtil.PT_TASK2;
import static seedu.nova.logic.commands.CommandTestUtil.WEEK_ONE;
import static seedu.nova.logic.commands.CommandTestUtil.WEEK_TWO;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_TASK;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_WEEK;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.ptcommands.PtDeleteNoteCommand;
import seedu.nova.logic.parser.ptparsers.PtDeleteNoteCommandParser;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.testutil.PtTaskBuilder;

public class PtDeleteNoteCommandParserTest {
    private PtDeleteNoteCommandParser parser = new PtDeleteNoteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        PtTask expectedPtTask = new PtTaskBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_ONE + PT_TASK,
                new PtDeleteNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName()));

        // multiple projects - last project accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_TP + PROJECT_IP + WEEK_ONE + PT_TASK,
                new PtDeleteNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName()));

        // multiple weeks - last week accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_TWO + WEEK_ONE + PT_TASK,
                new PtDeleteNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName()));

        // multiple taskNum - last taskNum accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_ONE + PT_TASK2 + PT_TASK,
                new PtDeleteNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PtDeleteNoteCommand.MESSAGE_USAGE);

        // missing project prefix
        assertParseFailure(parser, WEEK_ONE + PT_TASK, expectedMessage);

        // missing week prefix
        assertParseFailure(parser, PROJECT_IP + PT_TASK, expectedMessage);

        // missing task num prefix
        assertParseFailure(parser, PROJECT_IP + WEEK_ONE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid project
        assertParseFailure(parser, INVALID_PROJECT + WEEK_ONE + PT_TASK, Project.MESSAGE_CONSTRAINTS);

        // invalid week - non-zero
        assertParseFailure(parser, PROJECT_IP + INVALID_WEEK + PT_TASK, MESSAGE_INVALID_WEEK);

        // invalid task num
        assertParseFailure(parser, PROJECT_IP + WEEK_ONE + INVALID_TASK, MESSAGE_INVALID_TASK);
    }
}
