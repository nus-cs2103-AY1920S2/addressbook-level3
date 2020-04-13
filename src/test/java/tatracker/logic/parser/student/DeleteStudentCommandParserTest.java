//@@author fatin99

package tatracker.logic.parser.student;

import static tatracker.logic.commands.CommandTestUtil.GROUP_DESC_T04;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.MODULE_DESC_CS2030;
import static tatracker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_T04;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.student.DeleteStudentCommand;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteStudentCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteStudentCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteStudentCommandParserTest {

    private DeleteStudentCommandParser parser = new DeleteStudentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteStudentCommand() {
        // TODO: add all paths
        Matric matric = new Matric(VALID_MATRIC_BOB);

        Module module = new Module(VALID_MODULE_CS2030);
        Group group = new Group(VALID_GROUP_T04);

        StringBuilder command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(),
                new DeleteStudentCommand(matric, group.getIdentifier(), module.getIdentifier()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                Messages.getInvalidCommandMessage(DeleteStudentCommand.DETAILS.getUsage()));

        // no module specified
        assertParseFailure(parser, GROUP_DESC_T04 + MATRIC_DESC_AMY
                + NAME_DESC_AMY, Messages.getInvalidCommandMessage(DeleteStudentCommand.DETAILS.getUsage()));

        // no group specified
        assertParseFailure(parser, MODULE_DESC_CS2030 + MATRIC_DESC_AMY
                + NAME_DESC_AMY, Messages.getInvalidCommandMessage(DeleteStudentCommand.DETAILS.getUsage()));

        // no matric specified
        assertParseFailure(parser, GROUP_DESC_T04 + MODULE_DESC_CS2030
                + NAME_DESC_AMY, Messages.getInvalidCommandMessage(DeleteStudentCommand.DETAILS.getUsage()));
    }
}
