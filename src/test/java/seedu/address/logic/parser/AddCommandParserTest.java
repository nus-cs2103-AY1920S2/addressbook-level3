package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        //Profile expectedProfile = new PersonBuilder(BOB).build();
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = Integer.parseInt(VALID_SEMESTER_BOB);
        String grade = VALID_GRADE_BOB;
        String task = VALID_TASK_BOB;
        String deadline = VALID_DEADLINE_BOB;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, new AddCommand(moduleCode, semester, grade, task, deadline));

        // multiple modules - last module accepted
        assertParseSuccess(parser, MODCODE_DESC_AMY + MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, new AddCommand(moduleCode, semester, grade, task, deadline));

        // multiple semesters - last semester accepted
        assertParseSuccess(parser, MODCODE_DESC_BOB + SEMESTER_DESC_AMY + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, new AddCommand(moduleCode, semester, grade, task, deadline));

        // multiple grades - last grade accepted
        assertParseSuccess(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_AMY + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, new AddCommand(moduleCode, semester, grade, task, deadline));

        // multiple tasks - last task accepted
        assertParseSuccess(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_BOB + TASK_DESC_AMY
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, new AddCommand(moduleCode, semester, grade, task, deadline));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_BOB + TASK_DESC_BOB
                + DEADLINE_DESC_AMY + DEADLINE_DESC_BOB, new AddCommand(moduleCode, semester, grade, task, deadline));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = Integer.parseInt(VALID_SEMESTER_AMY);
        String grade = VALID_GRADE_AMY;
        String task = VALID_TASK_AMY;
        String deadline = VALID_DEADLINE_AMY;

        // No grade
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + TASK_DESC_AMY + DEADLINE_DESC_AMY,
                new AddCommand(moduleCode, semester, grade, task, deadline));

        // No task and deadline
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY,
                new AddCommand(moduleCode, semester, grade, task, deadline));

        // No grade, task and deadline
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY,
                new AddCommand(moduleCode, semester, grade, task, deadline));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_MODCODE_BOB + SEMESTER_DESC_BOB, expectedMessage);

        // missing semester prefix
        assertParseFailure(parser, MODCODE_DESC_BOB + VALID_SEMESTER_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODCODE_BOB + VALID_SEMESTER_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_MODCODE_DESC + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid semester (if error message differs, refer to parseSemester method under ParserUtil.java)
        assertParseFailure(parser, MODCODE_DESC_BOB + INVALID_SEMESTER_DESC + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, ParserUtil.MESSAGE_INVALID_SEMESTER);

        // invalid grade
        assertParseFailure(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + INVALID_GRADE_DESC
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, Grade.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + INVALID_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
