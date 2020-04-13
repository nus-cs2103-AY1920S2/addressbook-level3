package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliPrefix.PEOPLE_COMMAND_TYPE;
import static seedu.address.logic.parser.CliPrefix.WALLET_COMMAND_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDebts.TEXTBOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLoans.DINNER;
import static seedu.address.testutil.TypicalWallet.ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.BUDGET_APRIL_2020;
import static seedu.address.testutil.TypicalWallet.DUCK_RICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.global.ExitCommand;
import seedu.address.logic.commands.global.HelpCommand;
import seedu.address.logic.commands.people.PeopleAddCommand;
import seedu.address.logic.commands.people.PeopleClearCommand;
import seedu.address.logic.commands.people.PeopleDeleteCommand;
import seedu.address.logic.commands.people.PeopleEditCommand;
import seedu.address.logic.commands.people.PeopleEditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.people.PeopleFindCommand;
import seedu.address.logic.commands.people.PeopleLendCommand;
import seedu.address.logic.commands.people.PeopleListCommand;
import seedu.address.logic.commands.people.PeopleOweCommand;
import seedu.address.logic.commands.people.PeopleReceivedCommand;
import seedu.address.logic.commands.people.PeopleRemindAllCommand;
import seedu.address.logic.commands.people.PeopleRemindCommand;
import seedu.address.logic.commands.people.PeopleReturnedCommand;
import seedu.address.logic.commands.wallet.WalletBudgetCommand;
import seedu.address.logic.commands.wallet.WalletDeleteCommand;
import seedu.address.logic.commands.wallet.WalletEditCommand;
import seedu.address.logic.commands.wallet.WalletEditCommand.EditTransactionDescriptor;
import seedu.address.logic.commands.wallet.WalletExpenseCommand;
import seedu.address.logic.commands.wallet.WalletFindCommand;
import seedu.address.logic.commands.wallet.WalletIncomeCommand;
import seedu.address.logic.commands.wallet.WalletListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PeopleNamePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.DescriptionContainsKeywordsPredicate;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.WalletUtil;

public class SharkieParserTest {

    private final SharkieParser parser = new SharkieParser();

    // =========== People commands============================================================================

    @Test
    public void parsePeopleCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        PeopleAddCommand command = (PeopleAddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new PeopleAddCommand(person), command);
    }

    @Test
    public void parsePeopleCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(PEOPLE_COMMAND_TYPE + " " + PeopleClearCommand.COMMAND_WORD)
                instanceof PeopleClearCommand);
        assertTrue(parser.parseCommand(PEOPLE_COMMAND_TYPE + " " + PeopleClearCommand.COMMAND_WORD + " 3")
                instanceof PeopleClearCommand);
    }

    @Test
    public void parsePeopleCommand_delete() throws Exception {
        PeopleDeleteCommand command = (PeopleDeleteCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new PeopleDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parsePeopleCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        PeopleEditCommand command = (PeopleEditCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new PeopleEditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parsePeopleCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        PeopleFindCommand command = (PeopleFindCommand) parser.parseCommand(
                PEOPLE_COMMAND_TYPE + " " + PeopleFindCommand.COMMAND_WORD + " "
                        + " " + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new PeopleFindCommand(new PeopleNamePredicate(keywords)), command);
    }

    @Test
    public void parsePeopleCommand_list() throws Exception {
        assertTrue(parser.parseCommand(PEOPLE_COMMAND_TYPE + " " + PeopleListCommand.COMMAND_WORD)
                instanceof PeopleListCommand);
        assertTrue(parser.parseCommand(PEOPLE_COMMAND_TYPE + " " + PeopleListCommand.COMMAND_WORD + " 3")
                instanceof PeopleListCommand);
    }


    // @@author cheyannesim
    @Test
    public void parsePeopleCommand_owe() throws Exception {
        PeopleOweCommand command = (PeopleOweCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleOweCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PersonUtil.getDebtDescription(TEXTBOOK));
        assertEquals(new PeopleOweCommand(INDEX_FIRST, TEXTBOOK), command);
    }
    // @@author

    @Test
    public void parsePeopleCommand_returned() throws Exception {
        PeopleReturnedCommand command = (PeopleReturnedCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleReturnedCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + CliSyntax.PREFIX_TRANSACTION_INDEX + INDEX_FIRST.getOneBased());
        assertEquals(new PeopleReturnedCommand(INDEX_FIRST, INDEX_FIRST), command);
    }

    @Test
    public void parsePeopleCommand_lend() throws Exception {
        PeopleLendCommand command = (PeopleLendCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleLendCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PersonUtil.getLoanDescription(DINNER));
        assertEquals(new PeopleLendCommand(INDEX_FIRST, DINNER), command);
    }

    @Test
    public void parsePeopleCommand_received() throws Exception {
        PeopleReceivedCommand command = (PeopleReceivedCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleReceivedCommand.COMMAND_WORD + " " + INDEX_SECOND.getOneBased() + " "
                + PREFIX_TRANSACTION_INDEX + INDEX_FIRST.getOneBased());
        assertEquals(new PeopleReceivedCommand(INDEX_SECOND, INDEX_FIRST), command);
    }

    @Test
    public void parsePeopleCommand_remind() throws Exception {
        PeopleRemindCommand command = (PeopleRemindCommand) parser.parseCommand(PEOPLE_COMMAND_TYPE + " "
                + PeopleRemindCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new PeopleRemindCommand(INDEX_FIRST), command);
    }

    @Test
    public void parsePeopleCommand_remindAll() throws Exception {
        assertTrue(parser.parseCommand(PEOPLE_COMMAND_TYPE + " " + PeopleRemindAllCommand.COMMAND_WORD)
                instanceof PeopleRemindAllCommand);
        assertTrue(parser.parseCommand(PEOPLE_COMMAND_TYPE + " " + PeopleRemindAllCommand.COMMAND_WORD + " 3")
                instanceof PeopleRemindAllCommand);
    }

    // =========== Global commands============================================================================

    @Test
    public void parseGlobalCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD)
                instanceof ExitCommand);
    }

    @Test
    public void parseGlobalCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD)
                instanceof HelpCommand);
    }

    // =========== Wallet commands============================================================================

    @Test
    public void parseWalletCommand_expense() throws Exception {
        WalletExpenseCommand command =
                (WalletExpenseCommand) parser.parseCommand(WalletUtil.getExpenseCommand(DUCK_RICE));
        assertEquals(new WalletExpenseCommand(DUCK_RICE), command);
    }

    @Test
    public void parseWalletCommand_income() throws Exception {
        WalletIncomeCommand command =
                (WalletIncomeCommand) parser.parseCommand(WalletUtil.getIncomeCommand(ALLOWANCE));
        assertEquals(new WalletIncomeCommand(ALLOWANCE), command);
    }

    @Test
    public void parseWalletCommand_budget() throws Exception {
        WalletBudgetCommand command =
                (WalletBudgetCommand) parser.parseCommand(WalletUtil.getBudgetCommand(BUDGET_APRIL_2020));
        assertEquals(new WalletBudgetCommand(BUDGET_APRIL_2020), command);
    }

    @Test
    public void parseWalletCommand_delete() throws Exception {
        WalletDeleteCommand command = (WalletDeleteCommand) parser.parseCommand(WALLET_COMMAND_TYPE + " "
                + WalletDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new WalletDeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseWalletCommand_list() throws Exception {
        assertTrue(parser.parseCommand(WALLET_COMMAND_TYPE + " " + WalletListCommand.COMMAND_WORD)
                instanceof WalletListCommand);
        assertTrue(parser.parseCommand(WALLET_COMMAND_TYPE + " " + WalletListCommand.COMMAND_WORD + " 3")
                instanceof WalletListCommand);
    }

    @Test
    public void parseWalletCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        WalletFindCommand command = (WalletFindCommand) parser.parseCommand(
                WALLET_COMMAND_TYPE + " " + WalletFindCommand.COMMAND_WORD + " "
                        + " " + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new WalletFindCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseWalletCommand_edit() throws Exception {
        Transaction transaction = new TransactionBuilder().withAmount("2").buildExpense();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        WalletEditCommand command = (WalletEditCommand) parser.parseCommand(WALLET_COMMAND_TYPE + " "
                + WalletEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + WalletUtil.getEditTransactionDescriptorDetails(descriptor));
        assertEquals(new WalletEditCommand(INDEX_FIRST, descriptor), command);
    }

    // =========== Exceptions thrown =======================================================================

    @Test
    public void parseWalletCommand_unknownCommandWord_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                WALLET_COMMAND_TYPE + " unknown command word"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_globalCommands_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_prefixOnly_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                PEOPLE_COMMAND_TYPE));

        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                WALLET_COMMAND_TYPE));
    }

    @Test
    public void parseCommand_commandWithoutPrefix_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                PeopleListCommand.COMMAND_WORD));
    }

    @Test
    public void parsePeopleCommand_nullCommandWord_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                PEOPLE_COMMAND_TYPE));
    }

    @Test
    public void parsePeopleCommand_unknownCommandWord_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                PEOPLE_COMMAND_TYPE + " unknown command word"));
    }

}
