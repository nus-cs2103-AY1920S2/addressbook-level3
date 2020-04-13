package seedu.planner.logic.parser.timetable;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.DEGREE_YEAR_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_TIMETABLE_SEM_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_TIMETABLE_YEAR_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.commands.CommandTestUtil.SEM_DESC;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_SEM;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_YEAR;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.timetable.TimeTableAddCommand;
import seedu.planner.model.time.DegreeYear;
import seedu.planner.model.time.Semester;
import seedu.planner.model.time.SemesterYear;
import seedu.planner.model.time.StudentSemester;

//@@author thetruevincentchow
class TimeTableAddCommandParserTest {
    private TimeTableAddCommandParser parser = new TimeTableAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        StudentSemester expectedSemester = new StudentSemester(new SemesterYear(Semester.SPECIAL_TERM_ONE), 1);

        // whitespace in preamble, all fields present
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DEGREE_YEAR_DESC + SEM_DESC,
                new TimeTableAddCommand(expectedSemester));

        // arguments swapped
        assertParseSuccess(parser, SEM_DESC + DEGREE_YEAR_DESC,
                new TimeTableAddCommand(expectedSemester));

        // multiple prefixes
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DEGREE_YEAR_DESC + DEGREE_YEAR_DESC
                + SEM_DESC, new TimeTableAddCommand(expectedSemester));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // empty arguments
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREAMBLE_WHITESPACE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableAddCommand.MESSAGE_USAGE));

        // semester not present
        assertParseFailure(parser, DEGREE_YEAR_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableAddCommand.MESSAGE_USAGE));

        // degree year not present
        assertParseFailure(parser, SEM_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableAddCommand.MESSAGE_USAGE));

        // semester prefix present but empty argument
        assertParseFailure(parser, PREFIX_STUDENT_SEM + " " + DEGREE_YEAR_DESC, Semester.MESSAGE_CONSTRAINTS);

        // degree year prefix present but empty argument
        assertParseFailure(parser, SEM_DESC + " " + PREFIX_STUDENT_YEAR + PREAMBLE_WHITESPACE,
                DegreeYear.MESSAGE_CONSTRAINTS);

        // name and major prefixes present but both have empty arguments
        assertParseFailure(parser, PREFIX_STUDENT_SEM + " " + PREFIX_STUDENT_YEAR, DegreeYear.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryFieldInvalid_failure() {
        // degree year invalid
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_TIMETABLE_YEAR_DESC,
                DegreeYear.MESSAGE_CONSTRAINTS);

        // semester invalid
        assertParseFailure(parser, PREAMBLE_WHITESPACE + INVALID_TIMETABLE_SEM_DESC,
                Semester.MESSAGE_CONSTRAINTS);
    }
}
//@@author
