package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.DESC_BENCH;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.DESC_PUSHUP;
import static seedu.address.testutil.TypicalClients.getTypicalFitBiz;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.model.ClientInView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.testutil.EditExerciseDescriptorBuilder;
import seedu.address.testutil.ExerciseBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EditExerciseCommand}.
 *
 * @author @yonggiee
 */
public class EditExerciseCommandTest {

    private Model model;
    private Client clientInView;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFitBiz(), new UserPrefs(), new ClientInView());
        clientInView = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
    }

    @Test
    public void execute_noClientInView_throwsCommandException() {
        Exercise editedExercise = new ExerciseBuilder().build();
        EditExerciseCommand.EditExerciseDescriptor descriptor =
            new EditExerciseDescriptorBuilder(editedExercise).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE, descriptor);

        assertCommandFailure(editExerciseCommand, model, EditExerciseCommand.MESSAGE_CLIENT_NOT_IN_VIEW);
    }

    @Test
    public void execute_duplicateExerciseUnfilteredList_failure() {
        model.setClientInView(clientInView);

        Exercise firstExercise = clientInView.getExerciseList().getExercise(INDEX_FIRST_EXERCISE);

        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(firstExercise).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_SECOND_EXERCISE, descriptor);
        assertCommandFailure(editExerciseCommand, model, EditExerciseCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.setClientInView(clientInView);
        Exercise firstExercise = clientInView.getExerciseList().getExercise(INDEX_FIRST_EXERCISE);
        EditExerciseDescriptor descriptor = (new EditExerciseDescriptorBuilder(firstExercise))
            .withExerciseReps("20").build();

        Exercise editedExercise = new ExerciseBuilder(firstExercise).withExerciseReps("20").build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE, descriptor);
        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS,
            editedExercise.getForOutput());

        ModelManager expectedModel = new ModelManager(getTypicalFitBiz(), new UserPrefs(), new ClientInView());
        Client clientInViewExpected = expectedModel.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        expectedModel.setClientInView(clientInViewExpected);

        Exercise firstExerciseExpected = clientInViewExpected.getExerciseList().getExercise(INDEX_FIRST_EXERCISE);
        expectedModel.editExercise(firstExerciseExpected, editedExercise);
        assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        model.setClientInView(clientInView);

        Index outOfBoundIndex = Index.fromOneBased(clientInView.getExerciseList().size() + 1);
        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(DESC_PUSHUP).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditExerciseCommand standardCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE, DESC_PUSHUP);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExerciseCommand(INDEX_SECOND_EXERCISE, DESC_BENCH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExerciseCommand(INDEX_FIRST_EXERCISE, DESC_BENCH)));
    }

}
