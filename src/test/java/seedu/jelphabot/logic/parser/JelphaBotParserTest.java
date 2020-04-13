package seedu.jelphabot.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_YEARMONTH_FORMAT;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.logic.commands.AddCommand;
import seedu.jelphabot.logic.commands.CalendarCommand;
import seedu.jelphabot.logic.commands.ClearCommand;
import seedu.jelphabot.logic.commands.DeleteCommand;
import seedu.jelphabot.logic.commands.DoneCommand;
import seedu.jelphabot.logic.commands.EditCommand;
import seedu.jelphabot.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.jelphabot.logic.commands.ExitCommand;
import seedu.jelphabot.logic.commands.FindCommand;
import seedu.jelphabot.logic.commands.HelpCommand;
import seedu.jelphabot.logic.commands.ListCommand;
import seedu.jelphabot.logic.commands.ProductivityCommand;
import seedu.jelphabot.logic.commands.SummaryCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.DescriptionContainsKeywordsPredicate;
import seedu.jelphabot.testutil.EditTaskDescriptorBuilder;
import seedu.jelphabot.testutil.TaskBuilder;
import seedu.jelphabot.testutil.TaskUtil;

public class JelphaBotParserTest {

    private final JelphaBotParser parser = new JelphaBotParser();

    @Test // test against a task with Status = INCOMPLETE as that is the default value upon task creation.
    public void parseCommand_add() throws Exception {
        Task task = new TaskBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(TaskUtil.getAddCommand(task));
        assertEquals(new AddCommand(task), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(ClearCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditCommand command = (EditCommand) parser.parseCommand(
            EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + " "
                + TaskUtil.getEditTaskDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_TASK, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command =
            (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " date") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " module") instanceof ListCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));
    }

    // @@author Clouddoggo
    @Test
    public void parseCommand_done() throws Exception {
        DoneCommand command = (DoneCommand) parser.parseCommand(
            DoneCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DoneCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommand_summary() throws Exception {
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD) instanceof SummaryCommand);
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_SHORTCUT_LOWER) instanceof SummaryCommand);
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_SHORTCUT_UPPER) instanceof SummaryCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SummaryCommand.MESSAGE_USAGE), () -> parser.parseCommand(SummaryCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_calendar() throws Exception {
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD) instanceof CalendarCommand);
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_SHORTCUT_LOWER) instanceof CalendarCommand);
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_SHORTCUT_UPPER) instanceof CalendarCommand);
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD + " May-2020") instanceof CalendarCommand);
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD + " Apr-1-2020") instanceof CalendarCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_YEARMONTH_FORMAT,
            CalendarCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(CalendarCommand.COMMAND_WORD + " apr"));
    }

    @Test
    public void parseCommand_productivity() throws Exception {
        assertTrue(parser.parseCommand(ProductivityCommand.COMMAND_WORD) instanceof ProductivityCommand);
        assertTrue(parser.parseCommand(ProductivityCommand.COMMAND_SHORTCUT_LOWER) instanceof ProductivityCommand);
        assertTrue(parser.parseCommand(ProductivityCommand.COMMAND_SHORTCUT_UPPER) instanceof ProductivityCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ProductivityCommand.MESSAGE_USAGE), () -> parser.parseCommand(ProductivityCommand.COMMAND_WORD + " 3"));
    }
    // @@author

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(
            ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand("")
        );
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(
            ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                -> parser.parseCommand("unknownCommand")
        );
    }
}
