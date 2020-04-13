package seedu.expensela.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.commands.BudgetCommand;
import seedu.expensela.logic.commands.ClearCommand;
import seedu.expensela.logic.commands.DeleteCommand;
import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.logic.commands.ExitCommand;
import seedu.expensela.logic.commands.ExportCommand;
import seedu.expensela.logic.commands.FindCommand;
import seedu.expensela.logic.commands.HelpCommand;
import seedu.expensela.logic.commands.ListCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.transaction.NameContainsKeywordsPredicate;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.testutil.EditTransactionDescriptorBuilder;
import seedu.expensela.testutil.TransactionBuilder;
import seedu.expensela.testutil.TransactionUtil;

public class ExpenseLaParserTest {

    private final ExpenseLaParser parser = new ExpenseLaParser();

    @Test
    public void parseCommand_add() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(TransactionUtil.getAddCommand(transaction));
        assertEquals(new AddCommand(transaction), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_TRANSACTION), command);
    }

    @Test
    public void parseCommand_budget() throws Exception {
        BudgetCommand command = (BudgetCommand) parser.parseCommand(
                BudgetCommand.COMMAND_WORD + " b/1000");
        assertEquals(new BudgetCommand(1000.0, false), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_TRANSACTION, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

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
    public void parseCommand_export() throws Exception {
        assertTrue(parser.parseCommand(ExportCommand.COMMAND_WORD) instanceof ExportCommand);
        assertTrue(parser.parseCommand(ExportCommand.COMMAND_WORD + " 3") instanceof ExportCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
