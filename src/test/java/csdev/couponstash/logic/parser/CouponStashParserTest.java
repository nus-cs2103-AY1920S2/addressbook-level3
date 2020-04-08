package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_MONEY_SYMBOL;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.logic.commands.ClearCommand;
import csdev.couponstash.logic.commands.DeleteCommand;
import csdev.couponstash.logic.commands.EditCommand;
import csdev.couponstash.logic.commands.ExitCommand;
import csdev.couponstash.logic.commands.FindCommand;
import csdev.couponstash.logic.commands.HelpCommand;
import csdev.couponstash.logic.commands.ListCommand;
import csdev.couponstash.logic.commands.UsedCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.NameContainsKeywordsPredicate;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.CouponUtil;
import csdev.couponstash.testutil.EditCouponDescriptorBuilder;
import csdev.couponstash.testutil.TypicalIndexes;

public class CouponStashParserTest {
    // use an arbitrary symbol as the money symbol
    private final CouponStashParser parser =
            new CouponStashParser(VALID_MONEY_SYMBOL);

    @Test
    public void parseCommand_add() throws Exception {
        Coupon coupon = new CouponBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(
                CouponUtil.getAddCommand(coupon, VALID_MONEY_SYMBOL.toString()));
        AddCommand newCommand = new AddCommand(coupon);
        assertEquals(newCommand, command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_COUPON.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_COUPON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {

        Coupon coupon = new CouponBuilder().build();
        EditCommand.EditCouponDescriptor descriptor = new EditCouponDescriptorBuilder(coupon).build();
        String test = CouponUtil.getEditCouponDescriptorDetails(descriptor, VALID_MONEY_SYMBOL.toString());
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_COUPON.getOneBased() + " "
                + CouponUtil.getEditCouponDescriptorDetails(descriptor, VALID_MONEY_SYMBOL.toString()));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_COUPON, descriptor), command);
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
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " a/") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " u/") instanceof ListCommand);
    }

    @Test
    public void parseCommand_used() throws Exception {
        UsedCommand command = (UsedCommand) parser.parseCommand(
                UsedCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_COUPON.getOneBased());
        assertEquals(new UsedCommand(TypicalIndexes.INDEX_FIRST_COUPON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }
}
