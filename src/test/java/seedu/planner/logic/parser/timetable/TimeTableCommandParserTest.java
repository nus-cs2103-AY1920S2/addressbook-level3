package seedu.planner.logic.parser.timetable;

import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_SUBCOMMAND;
import static seedu.planner.logic.commands.CommandTestUtil.DEGREE_YEAR_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_SUBCOMMAND;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.commands.CommandTestUtil.SEM_DESC;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.timetable.TimeTableActiveCommand;
import seedu.planner.logic.commands.timetable.TimeTableAddCommand;
import seedu.planner.logic.commands.timetable.TimeTableCommand;
import seedu.planner.logic.commands.timetable.TimeTableListCommand;
import seedu.planner.logic.commands.timetable.TimeTableRemoveCommand;
import seedu.planner.model.time.Semester;
import seedu.planner.model.time.SemesterYear;
import seedu.planner.model.time.StudentSemester;

//@@author thetruevincentchow
class TimeTableCommandParserTest {
    private TimeTableCommandParser parser = new TimeTableCommandParser();
    private StudentSemester expectedSemester = new StudentSemester(new SemesterYear(Semester.SPECIAL_TERM_ONE), 1);

    @Test
    public void parse_validAddCommand_success() {
        // whitespace in preamble, all fields present
        assertParseSuccess(parser, "add " + PREAMBLE_WHITESPACE + DEGREE_YEAR_DESC + SEM_DESC,
                new TimeTableAddCommand(expectedSemester));
    }

    @Test
    public void parse_validRemoveCommand_success() {
        assertParseSuccess(parser, "remove " + SEM_DESC + DEGREE_YEAR_DESC,
                new TimeTableRemoveCommand(expectedSemester));
        assertParseSuccess(parser, "remove " + PREAMBLE_WHITESPACE + SEM_DESC + DEGREE_YEAR_DESC,
                new TimeTableRemoveCommand(expectedSemester));
    }

    @Test
    public void parse_validActiveCommand_success() {
        assertParseSuccess(parser, "active " + SEM_DESC + DEGREE_YEAR_DESC,
                new TimeTableActiveCommand(expectedSemester));
        assertParseSuccess(parser, "active " + PREAMBLE_WHITESPACE + SEM_DESC + DEGREE_YEAR_DESC,
                new TimeTableActiveCommand(expectedSemester));
    }

    @Test
    public void parse_validListCommand_success() {
        assertParseSuccess(parser, "list", new TimeTableListCommand());

        // any text is allowed after "list "
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "list" + " fajslkfjalk",
                new TimeTableListCommand());
    }

    @Test
    public void parse_subCommandMissing_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_UNKNOWN_SUBCOMMAND, TimeTableCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREAMBLE_WHITESPACE, String.format(MESSAGE_UNKNOWN_SUBCOMMAND,
                TimeTableCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_subCommandInvalid_failure() {
        assertParseFailure(parser, INVALID_SUBCOMMAND, String.format(MESSAGE_UNKNOWN_SUBCOMMAND,
                TimeTableCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_SUBCOMMAND, String.format(MESSAGE_UNKNOWN_SUBCOMMAND,
                TimeTableCommand.MESSAGE_USAGE));
    }
}
//@@author
