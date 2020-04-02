package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.ClientInView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteExerciseCommand}.
 */
public class DeleteExerciseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ClientInView());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client clientInView = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        model.setClientInView(clientInView);
        System.out.println(clientInView.getExerciseList());
        Exercise exerciseToDelete = clientInView.getExerciseList().getExercise(INDEX_FIRST_EXERCISE);
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_SUCCESS, exerciseToDelete);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ClientInView());
        Client expectedClientInView = expectedModel.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        expectedModel.setClientInView(expectedClientInView);
        // ALICE is static so deleting in model will also stimulate delete in expectedModel

        assertCommandSuccess(deleteExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Client clientInView = model.getFilteredClientList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        model.setClientInView(clientInView);

        Index outOfBoundIndex = Index.fromOneBased(clientInView.getExerciseList().size() + 1);
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteExerciseCommand deleteFirstCommand = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);
        DeleteExerciseCommand deleteSecondCommand = new DeleteExerciseCommand(INDEX_SECOND_EXERCISE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExerciseCommand deleteFirstCommandCopy = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
