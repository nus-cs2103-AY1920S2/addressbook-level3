package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.calendar.CalCommand;
import seedu.address.logic.commands.cmd.CmdCommand;
import seedu.address.logic.commands.facilitator.FacilCommand;
import seedu.address.logic.commands.module.ModuleCommand;
import seedu.address.logic.commands.task.TaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ModManagerParserTest {

    private final ModManagerParser parser = new ModManagerParser();

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_WORD_CLEAR) instanceof ClearCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_WORD_CLEAR + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_EXIT) instanceof ExitCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_EXIT + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_WORD_HELP) instanceof HelpCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_WORD_HELP + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_mod() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_MOD + " " + Command.COMMAND_WORD_LIST)
                instanceof ModuleCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_MOD + " " + Command.COMMAND_WORD_LIST + " 3")
                instanceof ModuleCommand);
    }

    @Test
    public void parseCommand_facil() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_FACIL + " " + Command.COMMAND_WORD_LIST)
                instanceof FacilCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_FACIL + " " + Command.COMMAND_WORD_LIST + " 3")
                instanceof FacilCommand);
    }

    @Test
    public void parseCommand_cal() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_CAL + " " + Command.COMMAND_WORD_VIEW + " "
                + PREFIX_WEEK + " this") instanceof CalCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_CAL + " " + Command.COMMAND_WORD_FIND + " empty")
                instanceof CalCommand);
    }

    @Test
    public void parseCommand_task() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_TASK + " " + Command.COMMAND_WORD_LIST)
                instanceof TaskCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_TASK + " " + Command.COMMAND_WORD_LIST + " 3")
                instanceof TaskCommand);
    }

    @Test
    public void parseCommand_cmd() throws Exception {
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_CMD + " " + Command.COMMAND_WORD_ALL)
                instanceof CmdCommand);
        assertTrue(parser.parseCommand(Command.COMMAND_GROUP_CMD + " " + Command.COMMAND_WORD_GROUP + " "
                + Command.COMMAND_GROUP_TASK) instanceof CmdCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("unknownCommand"));
    }
}
