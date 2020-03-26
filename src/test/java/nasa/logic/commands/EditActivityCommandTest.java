package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.DESC_EXAM;
import static nasa.logic.commands.CommandTestUtil.DESC_HWK;
import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_EXAM;
import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static nasa.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;
import static nasa.testutil.TypicalNasaBook.NASABOOK_TYPE_1;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.commons.core.Messages;
import nasa.commons.core.index.Index;
import nasa.logic.commands.EditActivityCommand.EditActivityDescriptor;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;
import nasa.model.activity.Activity;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.testutil.DeadlineBuilder;
import nasa.testutil.EditActivityDescriptorBuilder;
import nasa.testutil.ModuleBuilder;

// TODO: Implement {@code getFilteredActivityList} by accepting moduleCode as parameter in {@interface Model}
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditActivityCommand.
 */
public class EditActivityCommandTest {

    private Model model = new ModelManager(NASABOOK_TYPE_1.deepCopyNasaBook(), new HistoryBook<>(), new UserPrefs());
    private Module module = new ModuleBuilder().build(); // module to contain activity
    private int activityListSize = module.getActivities().getActivityList().size(); // num of activity in module
    private Index indexLastActivity = Index.fromOneBased(activityListSize);
    private ModuleCode moduleCode = module.getModuleCode(); // module code of activity-containing module
    private Index moduleIndex = Index.fromZeroBased(model.getNasaBook().getModuleList().indexOf(module));


    @Test
    public void execute_allFieldsSpecified_success() {
        Activity editedActivity = new DeadlineBuilder().build();
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();

        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode, descriptor);

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), model.getHistoryBook(),
                new UserPrefs());
        expectedModel.setActivityByIndex(moduleCode, INDEX_FIRST_ACTIVITY, editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Activity editedActivity = new DeadlineBuilder().withName(VALID_ACTIVITY_NAME_EXAM).build();
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();

        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode, descriptor);

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), model.getHistoryBook(),
                new UserPrefs());
        expectedModel.setActivityByIndex(moduleCode, INDEX_FIRST_ACTIVITY, editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode,
                new EditActivityDescriptor());
        Activity editedActivity = model.getFilteredActivityList(moduleIndex).get(INDEX_FIRST_ACTIVITY.getZeroBased());

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), model.getHistoryBook(),
                new UserPrefs());

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateActivityFilteredList_failure() {
        // edit activity in filtered list into a duplicate in nasa book
        Activity activityInList = model.getFilteredActivityList(moduleIndex).get(INDEX_SECOND_ACTIVITY.getZeroBased());
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode,
                new EditActivityDescriptorBuilder(activityInList).build());

        assertCommandFailure(editActivityCommand, model, EditActivityCommand.MESSAGE_DUPLICATE_ACTIVITY);

    }

    @Test
    public void execute_invalidActivityIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList(moduleIndex).size() + 1);
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(outOfBoundIndex, moduleCode, descriptor);

        assertCommandFailure(editActivityCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditActivityCommand standardCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode,
                DESC_EXAM);

        // same values -> returns true
        EditActivityDescriptor copyDescriptor = new EditActivityDescriptor(DESC_EXAM);
        EditActivityCommand commandWithSameValues = new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_SECOND_ACTIVITY, moduleCode, DESC_EXAM)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode, DESC_HWK)));

    }

}

