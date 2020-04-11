package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Grade;

import java.util.Set;

//@@author wanxuanong
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_allProfileFieldsPresent_success() {

        // Name present
        Name name = new Name(VALID_NAME_BOB);
        assertParseSuccess(parser, " " + PREFIX_NAME + VALID_NAME_BOB, new DeleteCommand(name));
    }

    @Test
    public void parse_allModuleFieldsPresent_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        Deadline task = new Deadline(VALID_MODCODE_AMY, VALID_TASK_AMY);
        String grade = VALID_GRADE_AMY;

        // Only Module field present
        assertParseSuccess(parser, MODCODE_DESC_AMY, new DeleteCommand(Set.of(moduleCode)));

        // Module and Task field present
        assertParseSuccess(parser, MODCODE_DESC_AMY + TASK_DESC_AMY, new DeleteCommand(Set.of(moduleCode), task));

        // Module and Grade field present
        assertParseSuccess(parser, MODCODE_DESC_AMY + GRADE_DESC_AMY, new DeleteCommand(Set.of(moduleCode), grade));

    }

    @Test
    public void parse_validNameOneProfile_success() {
        Name noCap = new Name("john");
        Name firstCap = new Name("John");
        Name allCap = new Name("JOHN");

        assertParseSuccess(parser, " " + PREFIX_NAME + noCap, new DeleteCommand(noCap));
        assertParseSuccess(parser, " " + PREFIX_NAME + firstCap, new DeleteCommand(firstCap));
        assertParseSuccess(parser, " " + PREFIX_NAME + allCap, new DeleteCommand(allCap));
    }

    @Test
    public void parse_validGradeOneModule_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        String gradeA = "A";
        String gradeB = "B";
        String gradeC = "C";

        assertParseSuccess(parser, MODCODE_DESC_AMY + " " + PREFIX_GRADE + gradeA,
                new DeleteCommand(Set.of(moduleCode), gradeA));
        assertParseSuccess(parser, MODCODE_DESC_AMY + " " + PREFIX_GRADE + gradeB,
                new DeleteCommand(Set.of(moduleCode), gradeB));
        assertParseSuccess(parser, MODCODE_DESC_AMY + " " + PREFIX_GRADE + gradeC,
                new DeleteCommand(Set.of(moduleCode), gradeC));
        assertParseSuccess(parser, MODCODE_DESC_AMY + " " + PREFIX_GRADE,
                new DeleteCommand(Set.of(moduleCode), ""));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // No fields present
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Only Task present
        assertParseFailure(parser, TASK_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Only Grade present
        assertParseFailure(parser, GRADE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Invalid module code
        assertParseFailure(parser, INVALID_MODCODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);

        // Invalid task
        assertParseFailure(parser, "", DeleteCommand.MESSAGE_DELETE_DEADLINE_FAILURE);

        // Invalid grade
        assertParseFailure(parser, INVALID_GRADE_DESC, Grade.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
