package seedu.address.logic.parser.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_MODULE_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.module.ModuleAddCommand;
import seedu.address.logic.commands.module.ModuleDeleteCommand;
import seedu.address.logic.commands.module.ModuleEditCommand;
import seedu.address.logic.commands.module.ModuleListCommand;
import seedu.address.logic.commands.module.ModuleViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.EditModuleDescriptorBuilder;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModuleUtil;

public class ModuleCommandParserTest {
    private ModuleCommandParser parser = new ModuleCommandParser();

    @Test
    public void parse_add() throws Exception {
        Module module = new ModuleBuilder().build();
        ModuleAddCommand command = (ModuleAddCommand) parser.parse(ModuleUtil.getModuleAddCommand(module));

        assertEquals(new ModuleAddCommand(module), command);
    }

    @Test
    public void parse_delete() throws Exception {
        ModuleDeleteCommand command = (ModuleDeleteCommand) parser.parse(Command.COMMAND_WORD_DELETE + " "
                + INDEX_FIRST.getOneBased());
        assertEquals(new ModuleDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parse_edit() throws Exception {
        Module module = new ModuleBuilder().build();
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(module).build();
        ModuleEditCommand command = (ModuleEditCommand) parser.parse(Command.COMMAND_WORD_EDIT + " "
                + INDEX_FIRST.getOneBased() + " " + ModuleUtil.getEditModuleDescriptorDetails(descriptor));
        assertEquals(new ModuleEditCommand(INDEX_FIRST, descriptor), command);

        ModuleEditCommand command1 = (ModuleEditCommand) parser.parse(Command.COMMAND_WORD_EDIT + " CS2030 "
                + ModuleUtil.getEditModuleDescriptorDetails(descriptor));
        assertEquals(new ModuleEditCommand(new ModuleCode("CS2030"), descriptor), command1);
    }

    @Test
    public void parse_list() throws Exception {
        assertTrue(parser.parse(Command.COMMAND_WORD_LIST) instanceof ModuleListCommand);
        assertTrue(parser.parse(Command.COMMAND_WORD_LIST + " 3") instanceof ModuleListCommand);
    }

    @Test
    public void parse_view() throws Exception {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        ModuleViewCommand command = (ModuleViewCommand) parser.parse(
                Command.COMMAND_WORD_VIEW + " " + moduleCode.value);
        assertEquals(new ModuleViewCommand(moduleCode), command);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_MODULE_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse(""));
    }

    @Test
    public void parse_unknownModuleCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_MODULE_COMMAND,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse("mod unknownCommand"));
    }
}
