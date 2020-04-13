package seedu.address.logic.commands.facilitator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFacilitatorAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_FACILITATORS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code FacilDeleteCommand}.
 */
public class FacilDeleteCommandTest {

    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Facilitator facilitatorToDelete = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST.getZeroBased());
        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(FacilDeleteCommand.MESSAGE_DELETE_FACILITATOR_SUCCESS,
                facilitatorToDelete);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteFacilitator(facilitatorToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFacilitatorList().size() + 1);
        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFacilitatorAtIndex(model, INDEX_FIRST);

        Facilitator facilitatorToDelete = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST.getZeroBased());
        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(FacilDeleteCommand.MESSAGE_DELETE_FACILITATOR_SUCCESS,
                facilitatorToDelete);

        Model expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteFacilitator(facilitatorToDelete);
        showNoFacilitator(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFacilitatorAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of facilitator list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getModManager().getFacilitatorList().size());

        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validName_success() {
        Facilitator facilitatorToDelete = model.getFilteredFacilitatorList()
                .get(INDEX_FIRST.getZeroBased());
        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(facilitatorToDelete.getName());

        String expectedMessage = String.format(FacilDeleteCommand.MESSAGE_DELETE_FACILITATOR_SUCCESS,
                facilitatorToDelete);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteFacilitator(facilitatorToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.FACILITATOR, expectedModel);
    }

    @Test
    public void execute_validPartialName_success() {
        Name name = new Name("Alice");
        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(name);

        List<Facilitator> fetch = new ArrayList<>();
        fetch.add(model.getFilteredFacilitatorList().get(0));

        StringBuilder builder = new StringBuilder(
                String.format(Messages.MESSAGE_PARTIAL_FACILITATOR_NAME_MATCHING_FOUND, name));
        fetch.forEach(x -> builder.append("  ").append(x.getName().toString()).append('\n'));
        builder.append(Messages.MESSAGE_ASK_TO_CONFIRM_FACILITATOR);

        String expectedMessage = builder.toString();

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.PROMPTING, model);
    }

    @Test
    public void execute_nameNotFound_success() {
        Name name = new Name("Amy");
        FacilDeleteCommand deleteCommand = new FacilDeleteCommand(name);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_FACILITATOR_NOT_FOUND, name));
    }

    @Test
    public void equals() {
        FacilDeleteCommand deleteFirstCommand = new FacilDeleteCommand(INDEX_FIRST);
        FacilDeleteCommand deleteSecondCommand = new FacilDeleteCommand(INDEX_SECOND);
        FacilDeleteCommand deleteAmyCommand = new FacilDeleteCommand(new Name(VALID_NAME_AMY));
        FacilDeleteCommand deleteBobCommand = new FacilDeleteCommand(new Name(VALID_NAME_BOB));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteAmyCommand.equals(deleteAmyCommand));

        // same values -> returns true
        FacilDeleteCommand deleteFirstCommandCopy = new FacilDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        FacilDeleteCommand deleteAmyCommandCopy = new FacilDeleteCommand(new Name(VALID_NAME_AMY));
        assertTrue(deleteAmyCommand.equals(deleteAmyCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteAmyCommand.equals(1));
        assertFalse(deleteFirstCommand.equals(deleteAmyCommand));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteAmyCommand.equals(null));

        // different facilitator -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertFalse(deleteAmyCommand.equals(deleteBobCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoFacilitator(Model model) {
        model.updateFilteredFacilitatorList(PREDICATE_SHOW_NO_FACILITATORS);

        assertTrue(model.getFilteredFacilitatorList().isEmpty());
    }
}
