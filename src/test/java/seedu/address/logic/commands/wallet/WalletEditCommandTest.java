package seedu.address.logic.commands.wallet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWallet.VALID_DESC_TA;
import static seedu.address.testutil.TypicalWallet.VALID_TAG_TA;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.wallet.WalletEditCommand.EditTransactionDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.Wallet;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class WalletEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());

    @Test
    public void execute_incomeAllFieldsSpecifiedUnfilteredList_success() {
        Income editedTransaction = new TransactionBuilder().buildIncome(); //returns 1 income obj
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        //stores edited income obj
        WalletEditCommand walletEditCommand = new WalletEditCommand(INDEX_THIRD, descriptor); //edit with
        // command -> copy from descrip.

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());

        expectedModel.setIncome(model.getFilteredIncomeList().get(0), editedTransaction); //add edited income back

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_expenseAllFieldsSpecifiedUnfilteredList_success() {
        Expense editedTransaction = new TransactionBuilder().buildExpense();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        WalletEditCommand walletEditCommand = new WalletEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new Wallet(model.getWallet()),
                new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedTransaction);

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIncome = Index.fromOneBased(model.getFilteredTransactionList().size());
        Income lastIncome =
                (Income) model.getFilteredTransactionList().get(indexLastIncome.getZeroBased());

        TransactionBuilder incomeInList = new TransactionBuilder(lastIncome);
        Income editedIncome =
                incomeInList.withDescription(VALID_DESC_TA).withAmount(VALID_AMOUNT).buildIncome();

        WalletEditCommand.EditTransactionDescriptor descriptor =
                new EditTransactionDescriptorBuilder().withDescription(VALID_DESC_TA)
                .withAmount(Double.parseDouble(VALID_AMOUNT)).build();
        WalletEditCommand walletEditCommand = new WalletEditCommand(indexLastIncome, descriptor);

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedIncome);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Wallet(model.getWallet()), new UserPrefs());
        expectedModel.setIncome(lastIncome, editedIncome);

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        WalletEditCommand walletEditCommand = new WalletEditCommand(INDEX_FIRST,
                new WalletEditCommand.EditTransactionDescriptor());
        Expense editedExpense = (Expense) model.getFilteredTransactionList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Wallet(model.getWallet()), new UserPrefs());

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST);

        Expense expenseInFilteredList =
                (Expense) model.getFilteredTransactionList().get(INDEX_FIRST.getZeroBased());
        Expense editedExpense =
                new TransactionBuilder(expenseInFilteredList).withDescription(VALID_DESC_AMY).buildExpense();
        WalletEditCommand walletEditCommand = new WalletEditCommand(INDEX_FIRST,
                new EditTransactionDescriptorBuilder().withDescription(VALID_DESC_AMY).build());

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedExpense);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Wallet(model.getWallet()), new UserPrefs());
        expectedModel.setExpense(expenseInFilteredList, editedExpense);

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        WalletEditCommand.EditTransactionDescriptor
                descriptor = new EditTransactionDescriptorBuilder().withDescription(VALID_DESC_AMY).build();
        WalletEditCommand walletEditCommand = new WalletEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(walletEditCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of wallet
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWallet().getTransactionList().size());

        WalletEditCommand walletEditCommand = new WalletEditCommand(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withDescription(VALID_DESC_AMY).build());

        assertCommandFailure(walletEditCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WalletEditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(VALID_DESC_BOB)
                .withAmount(Double.parseDouble(VALID_AMOUNT))
                .withDate(LocalDate.parse(VALID_DATE, DateTimeFormatter.ofPattern("dd/MM/uuuu")))
                .withTag(VALID_TAG_TA).build();
        WalletEditCommand.EditTransactionDescriptor anotherDescriptor = new EditTransactionDescriptorBuilder()
                .withAmount(Double.parseDouble(VALID_AMOUNT))
                .withDate(LocalDate.parse(VALID_DATE, DateTimeFormatter.ofPattern("dd/MM/uuuu")))
                .withTag(VALID_TAG_TA).build();

        final WalletEditCommand standardCommand = new WalletEditCommand(INDEX_FIRST, descriptor);

        // same values -> returns true
        WalletEditCommand.EditTransactionDescriptor copyDescriptor =
                new WalletEditCommand.EditTransactionDescriptor(descriptor);
        WalletEditCommand commandWithSameValues = new WalletEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new WalletListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new WalletEditCommand(INDEX_SECOND, descriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new WalletEditCommand(INDEX_FIRST, anotherDescriptor)));
    }

}
