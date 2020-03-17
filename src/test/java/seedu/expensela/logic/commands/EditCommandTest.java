package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.expensela.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.expensela.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.commons.core.index.Index;
import seedu.expensela.logic.commands.EditCommand.EditTransactionDescriptor;
import seedu.expensela.model.AddressBook;
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

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTransaction = Index.fromOneBased(model.getFilteredTransactionList().size());
        Transaction lastTransaction = model.getFilteredTransactionList().get(indexLastTransaction.getZeroBased());

        TransactionBuilder transactionInList = new TransactionBuilder(lastTransaction);
        Transaction editedTransaction = transactionInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .build();

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastTransaction, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(lastTransaction, editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST_PERSON);

        Transaction transactionInFilteredList = model.getFilteredTransactionList().get(INDEX_FIRST_PERSON.getZeroBased());
        Transaction editedTransaction = new TransactionBuilder(transactionInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTransactionDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTransactionUnfilteredList_failure() {
        Transaction firstTransaction = model.getFilteredTransactionList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(firstTransaction).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateTransactionFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST_PERSON);

        // edit transaction in filtered list into a duplicate in address book
        Transaction transactionInList = model.getAddressBook().getTransactionList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTransactionDescriptorBuilder(transactionInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        showTransactionAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTransactionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTransactionDescriptor copyDescriptor = new EditTransactionDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
