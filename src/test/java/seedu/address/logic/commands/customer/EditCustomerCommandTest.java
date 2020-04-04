package seedu.address.logic.commands.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.customer.TypicalPersons.ALICE_ID;
import static seedu.address.testutil.customer.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.customer.EditCustomerCommand.EditPersonDescriptor;
import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.customer.EditPersonDescriptorBuilder;
import seedu.address.testutil.customer.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for EditCustomerCommand.
 */
public class EditCustomerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Customer editedCustomer = new PersonBuilder(ALICE_ID).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedCustomer).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCustomer);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredCustomerList().get(0), editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastCustomer);
        Customer editedCustomer = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCustomer);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());
        expectedModel.setPerson(lastCustomer, editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptor());
        Customer editedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCustomer);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Customer customerInFilteredList = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());
        Customer editedCustomer = new PersonBuilder(customerInFilteredList).withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedCustomer);

        Model expectedModel = new ModelManager(new InventorySystem(model.getInventorySystem()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredCustomerList().get(0), editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Customer firstCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstCustomer).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit customer in filtered list into a duplicate in address book
        Customer secondCustomerInList =
                model.getInventorySystem().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Customer firstCustomerInList =
                model.getInventorySystem().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor editPersonDescriptor =
                new EditPersonDescriptorBuilder(secondCustomerInList).withId(firstCustomerInList.getId()).build();
        EditCustomerCommand editCustomerCommand =
                new EditCustomerCommand(INDEX_FIRST_PERSON, editPersonDescriptor);

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventorySystem().getPersonList().size());

        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCustomerCommand standardCommand = new EditCustomerCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCustomerCommand commandWithSameValues = new EditCustomerCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCustomerCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
