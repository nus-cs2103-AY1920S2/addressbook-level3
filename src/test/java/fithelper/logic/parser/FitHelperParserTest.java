package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_INDEX;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import fithelper.commons.core.index.Index;
import fithelper.logic.commands.AddCommand;
import fithelper.logic.commands.ClearCommand;
import fithelper.logic.commands.DeleteCommand;
import fithelper.logic.commands.EditCommand;
import fithelper.logic.commands.ExitCommand;
import fithelper.logic.commands.FindCommand;
import fithelper.logic.commands.HelpCommand;
import fithelper.logic.commands.ListCommand;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.Entry;
import fithelper.model.entry.NameContainsKeywordsPredicate;
import fithelper.model.entry.Type;
import fithelper.testutil.EditEntryDescriptorBuilder;
import fithelper.testutil.EntryBuilder;
import fithelper.testutil.EntryUtil;

public class FitHelperParserTest {
    private final FitHelperParser parser = new FitHelperParser();

    @Test
    public void parseCommandAdd() throws Exception {
        Entry entry = new EntryBuilder().build();
        System.out.println(entry.toString());
        AddCommand command = (AddCommand) parser.parseCommand(EntryUtil.getAddCommand(entry));
        assertEquals(new AddCommand(entry), command);
    }

    @Test
    public void parseCommandClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommandDelete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
               DeleteCommand.COMMAND_WORD + " " + PREFIX_TYPE + "f" + " " + PREFIX_INDEX + "1");
        assertEquals(new DeleteCommand(new Type("food"), new Index(0)), command);
    }

    @Test
    public void parseCommandEdit() throws Exception {
        Entry entry = new EntryBuilder().build();
        EditCommand.EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(entry).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + "1" + " " + EntryUtil.getEditEntryDescriptorDetails(descriptor));
        assertEquals(new EditCommand(new Index(0), descriptor), command);
    }

    @Test
    public void parseCommandExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommandFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_KEYWORD + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommandList() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }
}


