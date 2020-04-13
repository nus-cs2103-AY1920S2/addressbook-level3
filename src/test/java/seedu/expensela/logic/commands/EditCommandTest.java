package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.commons.core.index.Index;
import seedu.expensela.logic.commands.EditCommand.EditTransactionDescriptor;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.testutil.EditTransactionDescriptorBuilder;
import seedu.expensela.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new ExpenseLa(model.getExpenseLa()), new UserPrefs(), new GlobalData());

        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        TransactionBuilder transactionInList = new TransactionBuilder(lastTransaction);
        Transaction editedTransaction = transactionInList.withName(VALID_NAME_AIRPODS)
                .withAmount(VALID_AMOUNT_AIRPODS, false)
                .build();

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName(VALID_NAME_AIRPODS)
                .withAmount(VALID_AMOUNT_AIRPODS).build();
        EditCommand editCommand = new EditCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new ExpenseLa(model.getExpenseLa()), new UserPrefs(), new GlobalData());
        expectedModel.setTransaction(lastTransaction, editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION, new EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_TRANSACTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new ExpenseLa(model.getExpenseLa()), new UserPrefs(), new GlobalData());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);

        Transaction transactionInFilteredList = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        Transaction editedTransaction = new TransactionBuilder(transactionInFilteredList)
                .withName(VALID_NAME_AIRPODS)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION,
                new EditTransactionDescriptorBuilder().withName(VALID_NAME_AIRPODS).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new ExpenseLa(model.getExpenseLa()), new UserPrefs(), new GlobalData());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTransactionUnfilteredList_failure() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_TRANSACTION.getZeroBased());
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(firstTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_TRANSACTION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TRANSACTION);
    }

    @Test
    public void execute_duplicateTransactionFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);

        // edit transaction in filtered list into a duplicate in expense la
        Transaction transactionInList = model.getExpenseLa().getTransactionList()
                .get(INDEX_SECOND_TRANSACTION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TRANSACTION,
                new EditTransactionDescriptorBuilder(transactionInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TRANSACTION);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withName(VALID_NAME_AIRPODS)
                .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of expense la
     */
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);
        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of expense la list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExpenseLa().getTransactionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withName(VALID_NAME_AIRPODS).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TRANSACTION, DESC_PIZZA);

        // same values -> returns true
        EditCommand.EditTransactionDescriptor copyDescriptor = new EditTransactionDescriptor(DESC_PIZZA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TRANSACTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TRANSACTION, DESC_PIZZA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TRANSACTION, DESC_AIRPODS)));
    }

}
