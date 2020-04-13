package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_SPACES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskDeleteCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskNumManager;

public class TaskDeleteCommandParserTest {

    private TaskDeleteCommandParser parser = new TaskDeleteCommandParser();

    @Test
    public void parse_validArgs_returnTaskDeleteCommand() {
        assertParseSuccess(parser, "CS2103T 888",
                new TaskDeleteCommand(new ModuleCode("CS2103T"), 888));
        assertParseSuccess(parser, "CS3232S 111",
                new TaskDeleteCommand(new ModuleCode("CS3232S"), 111));
        assertParseSuccess(parser, "PCS3232 2",
                new TaskDeleteCommand(new ModuleCode("PCS3232"), 2));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //empty
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, EMPTY_SPACES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDeleteCommand.MESSAGE_USAGE));

        //invalid module code
        assertParseFailure(parser, "CS323232", ModuleCode.MESSAGE_CONSTRAINTS);

        //no taskNum
        assertParseFailure(parser, "CS2103", TaskNumManager.MESSAGE_USAGE_CONSTRAINTS);

        //not invalidTaskNum
        assertParseFailure(parser, "CS2103 abc", TaskNumManager.MESSAGE_USAGE_CONSTRAINTS);
        assertParseFailure(parser, "CS2103 848 abc", TaskNumManager.MESSAGE_USAGE_CONSTRAINTS);
    }

}
