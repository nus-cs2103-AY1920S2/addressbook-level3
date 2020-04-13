package seedu.nova.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.nova.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.nova.commons.core.Messages;
import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbDeleteCommand;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.person.Person;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AbDeleteCommand}.
 */
public class AbDeleteCommandTest {

    private final Nova nova = new Nova();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AbDeleteCommand abDeleteCommand = new AbDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                AbDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getNova(), new UserPrefs());

        //expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(abDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AbDeleteCommand abDeleteCommand = new AbDeleteCommand(outOfBoundIndex);

        assertCommandFailure(abDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AbDeleteCommand abDeleteCommand = new AbDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                AbDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getNova(), new UserPrefs());
        //expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(abDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of nova book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AbDeleteCommand abDeleteCommand = new AbDeleteCommand(outOfBoundIndex);

        assertCommandFailure(abDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AbDeleteCommand deleteFirstCommand = new AbDeleteCommand(INDEX_FIRST_PERSON);
        AbDeleteCommand deleteSecondCommand = new AbDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        AbDeleteCommand deleteFirstCommandCopy = new AbDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */


    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

}
