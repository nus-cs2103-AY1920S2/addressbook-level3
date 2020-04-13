package seedu.address.logic.commands.consult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConsults.getTypicalConsultTAble;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.DeleteConsultCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteConsultCommand}.
 */
public class DeleteConsultCommandTest {

    private Model model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), getTypicalConsultTAble(),
        new TutorialTAble(), new ModTAble(), new ReminderTAble());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Consult consultToDelete = model.getFilteredConsultList().get(INDEX_FIRST.getZeroBased());
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteConsultCommand.MESSAGE_DELETE_CONSULT_SUCCESS, consultToDelete);

        ModelManager expectedModel = new ModelManager(model.getStudentTAble(), new UserPrefs(), model.getConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
        expectedModel.deleteConsult(consultToDelete);

        assertCommandSuccess(deleteConsultCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConsultList().size() + 1);
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(outOfBoundIndex);

        assertCommandFailure(deleteConsultCommand, model, Messages.MESSAGE_INVALID_CONSULT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteConsultCommand deleteFirstCommand = new DeleteConsultCommand(INDEX_FIRST);
        DeleteConsultCommand deleteSecondCommand = new DeleteConsultCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteConsultCommand deleteFirstCommandCopy = new DeleteConsultCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
