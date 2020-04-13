package seedu.nova.logic.parser.ptparser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_NOTE;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_PROJECT;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TASK;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_WEEK;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.nova.logic.commands.CommandTestUtil.PROJECT_IP;
import static seedu.nova.logic.commands.CommandTestUtil.PROJECT_TP;
import static seedu.nova.logic.commands.CommandTestUtil.PT_NOTE;
import static seedu.nova.logic.commands.CommandTestUtil.PT_NOTE2;
import static seedu.nova.logic.commands.CommandTestUtil.PT_TASK;
import static seedu.nova.logic.commands.CommandTestUtil.PT_TASK2;
import static seedu.nova.logic.commands.CommandTestUtil.WEEK_ONE;
import static seedu.nova.logic.commands.CommandTestUtil.WEEK_TWO;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_TASK;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_WEEK;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.ptcommands.PtEditNoteCommand;
import seedu.nova.logic.parser.ptparsers.PtEditNoteCommandParser;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.testutil.PtTaskBuilder;

public class PtEditNoteCommandParserTest {
    private PtEditNoteCommandParser parser = new PtEditNoteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        PtTask expectedPtTask = new PtTaskBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_ONE + PT_TASK + PT_NOTE,
                new PtEditNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName(),
                        expectedPtTask.getNoteString()));

        // multiple projects - last project accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_TP + PROJECT_IP + WEEK_ONE + PT_TASK + PT_NOTE,
                new PtEditNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName(),
                        expectedPtTask.getNoteString()));

        // multiple weeks - last week accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_TWO + WEEK_ONE + PT_TASK + PT_NOTE,
                new PtEditNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName(),
                        expectedPtTask.getNoteString()));

        // multiple taskNum - last taskNum accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_ONE + PT_TASK2 + PT_TASK + PT_NOTE,
                new PtEditNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName(),
                        expectedPtTask.getNoteString()));

        // multiple notes - last note accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_IP + WEEK_ONE + PT_TASK + PT_NOTE2 + PT_NOTE,
                new PtEditNoteCommand(expectedPtTask.getPtWeek(), 1, expectedPtTask.getProjectName(),
                        expectedPtTask.getNoteString()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PtEditNoteCommand.MESSAGE_USAGE);

        // missing project prefix
        assertParseFailure(parser, WEEK_ONE + PT_TASK + PT_NOTE, expectedMessage);

        // missing week prefix
        assertParseFailure(parser, PROJECT_IP + PT_TASK + PT_NOTE, expectedMessage);

        // missing task num prefix
        assertParseFailure(parser, PROJECT_IP + WEEK_ONE + PT_NOTE, expectedMessage);

        // missing note prefix
        assertParseFailure(parser, PROJECT_IP + WEEK_ONE + PT_TASK, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid project
        assertParseFailure(parser, INVALID_PROJECT + WEEK_ONE + PT_TASK + PT_NOTE, Project.MESSAGE_CONSTRAINTS);

        // invalid week - non-zero
        assertParseFailure(parser, PROJECT_IP + INVALID_WEEK + PT_TASK + PT_NOTE, MESSAGE_INVALID_WEEK);

        // invalid task num
        assertParseFailure(parser, PROJECT_IP + WEEK_ONE + INVALID_TASK + PT_NOTE, MESSAGE_INVALID_TASK);

        // invalid note
        assertParseFailure(parser, PROJECT_IP + WEEK_ONE + PT_TASK + INVALID_NOTE, PtNote.MESSAGE_CONSTRAINTS);
    }
}
