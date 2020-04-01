package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.ClearCommand;
import nasa.logic.commands.ExitCommand;
import nasa.logic.commands.HelpCommand;
import nasa.logic.commands.ListCommand;
import nasa.logic.parser.exceptions.ParseException;

public class NasaBookParserTest {

    private final NasaBookParser parser = new NasaBookParser();

    /*
    @Test
    public void parseCommand_add() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModuleCommand command = (AddModuleCommand) parser.parseCommand(ModuleUtil.getAddModuleCommand(module));
        assertEquals(new AddModuleCommand(module), command);
    }

     */

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }
    /*
        @Test
        public void parseCommand_delete() throws Exception {
            DeleteModuleCommand command = (DeleteModuleCommand) parser.parseCommand(
                    DeleteModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
            assertEquals(new DeleteModuleCommand(INDEX_FIRST_PERSON), command);
        }

        @Test
        public void parseCommand_edit() throws Exception {
            Person person = new PersonBuilder().build();
            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
            EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                    + INDEX_FIRST_PERSON.getOneBased() + " " + ModuleUtil.getEditPersonDescriptorDetails(descriptor));
            assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
        }

     */
    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }
    /*
        @Test
        public void parseCommand_find() throws Exception {
            List<String> keywords = Arrays.asList("foo", "bar", "baz");
            FindCommand command = (FindCommand) parser.parseCommand(
                    FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
            assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
        }

     */

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
