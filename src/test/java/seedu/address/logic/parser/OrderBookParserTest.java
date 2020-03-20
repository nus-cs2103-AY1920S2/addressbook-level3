package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NearbyCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;

public class OrderBookParserTest {

    private final OrderBookParser parser = new OrderBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Order order = new OrderBuilder().build();
        InsertCommand command = (InsertCommand) parser.parseCommand(OrderUtil.getInsertCommand(order));
        assertEquals(new InsertCommand(order), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " -f") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Order order = new OrderBuilder().build();
        EditCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(order).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ORDER.getOneBased() + " " + OrderUtil.getEditOrderDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_ORDER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new OrderContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_nearby() throws Exception {
        NearbyCommand nearbyCommand = (NearbyCommand) parser.parseCommand(
                NearbyCommand.COMMAND_WORD + " " + "1"
        );
        assertEquals(new NearbyCommand("1"), nearbyCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
