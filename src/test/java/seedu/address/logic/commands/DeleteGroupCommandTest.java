package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;

public class DeleteGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index targetIndex = Index.fromOneBased(1);
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Group groupToDelete = model.getFilteredGroupList().get(0);
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(Index.fromOneBased(1));
        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);
        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(outOfBoundIndex);

        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGroupAtIndex(model, targetIndex);

        Group groupToDelete = model.getFilteredGroupList().get(targetIndex.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(targetIndex);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);
        showNoGroup(expectedModel);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showGroupAtIndex(model, targetIndex);
        Index outOfBounds = Index.fromOneBased(2);
        assertTrue(outOfBounds.getZeroBased() < model.getAddressBook().getGroupList().size());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(outOfBounds);
        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteGroupCommand deleteGroupCommand1 = new DeleteGroupCommand(Index.fromZeroBased(0));
        DeleteGroupCommand deleteGroupCommand2 = new DeleteGroupCommand(Index.fromZeroBased(1));

        //same values -> returns true
        DeleteGroupCommand deleteGroupCommand1Copy = new DeleteGroupCommand(Index.fromZeroBased(0));
        assertTrue(deleteGroupCommand1Copy.equals(deleteGroupCommand1));

        //same object -> returns true
        assertTrue(deleteGroupCommand1.equals(deleteGroupCommand1));

        // different types -> returns false
        assertFalse(deleteGroupCommand1.equals(1));

        // different groups -> returns false
        assertFalse(deleteGroupCommand1.equals(deleteGroupCommand2));

        // null object -> return false
        assertFalse(deleteGroupCommand1.equals(null));
    }

    private void showNoGroup(Model model) {
        model.updateFilteredGroupList(p -> false);

        assertTrue(model.getFilteredGroupList().isEmpty());
    }

}
