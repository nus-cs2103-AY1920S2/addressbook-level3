package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_RESUME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.add.AddCommand;
import seedu.address.logic.commands.add.AddInternshipCommand;
import seedu.address.logic.commands.delete.DeleteCommand;
import seedu.address.logic.commands.delete.DeleteResumeCommand;
import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.logic.commands.edit.EditInternshipCommand;
import seedu.address.logic.commands.edit.EditInternshipDescriptor;
import seedu.address.logic.commands.find.FindCommand;
import seedu.address.logic.commands.find.FindInternshipCommand;
import seedu.address.logic.commands.help.HelpCommand;
import seedu.address.logic.commands.list.ListCommand;
import seedu.address.logic.commands.view.ViewCommand;
import seedu.address.logic.commands.view.ViewResumeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.Internship;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.InternshipUtil;

public class ResumeBookParserTest {

    private final ResumeBookParser parser = new ResumeBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Internship internship = new InternshipBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(InternshipUtil.getAddCommand(internship));
        assertEquals(new AddInternshipCommand(internship), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(ViewResumeCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ITEM.getOneBased() + " " + ITEM_TYPE_RESUME);
        assertEquals(new ViewResumeCommand(INDEX_FIRST_ITEM), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(DeleteResumeCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ITEM.getOneBased() + " " + ITEM_TYPE_RESUME);
        assertEquals(new DeleteResumeCommand(INDEX_FIRST_ITEM), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Internship internship = new InternshipBuilder().build();
        EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder(internship).build();
        EditCommand command = (EditCommand) parser.parseCommand(InternshipUtil.getEditCommand(1, internship));
        assertEquals(new EditInternshipCommand(INDEX_FIRST_ITEM, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findInternship() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand
                (FindInternshipCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")) + " i/int");
        assertEquals(new FindInternshipCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " " + HelpCommand.COMMAND_OPTION_SUMMARY)
            instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " " + HelpCommand.COMMAND_OPTION_START)
            instanceof HelpCommand);
        assertThrows(ParseException.class, HelpCommand.MESSAGE_INVALID_OPTION, ()
            -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " i/res") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " i/int") instanceof ListCommand);
        // assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " i/res 3") instanceof ListCommand);
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
