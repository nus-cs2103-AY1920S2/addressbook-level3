package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static nasa.logic.commands.CommandTestUtil.showActivityAtIndex;
import static nasa.testutil.TypicalNasaBook.NASABOOK_TYPE_1;

import nasa.model.activity.Deadline;
import nasa.model.module.ModuleCode;
import nasa.testutil.EditActivityDescriptorBuilder;
import org.junit.jupiter.api.Test;

import nasa.commons.core.Messages;
import nasa.commons.core.index.Index;
import nasa.logic.commands.ClearCommand;
import nasa.logic.commands.EditActivityCommand;
import nasa.logic.commands.EditActivityCommand.EditActivityDescriptor;
import nasa.model.NasaBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.activity.Activity;
import nasa.testutil.EditActivityDescriptorBuilder;
import nasa.testutil.DeadlineBuilder;

// TODO: Implement {@code getFilteredActivityList} by accepting moduleCode as parameter in {@interface Model}
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditActivityCommand.
 */
public class EditActivityCommandTest {

     private Model model = new ModelManager(NASABOOK_TYPE_1, new UserPrefs());
     private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CS2030);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        /*
        Activity editedActivity = new DeadlineBuilder().build();
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_PERSON, moduleCode, descriptor);

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());
        expectedModel.setActivity(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
        */
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        /*
        Index indexLastActivity = Index.fromOneBased(model.getFilteredActivityList().size());
        Activity lastActivity = model.getFilteredActivityList().get(indexLastActivity.getZeroBased());

        ActivityBuilder activityInList = new ActivityBuilder(lastActivity);
        Activity editedActivity = activityInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(indexLastActivity, descriptor);

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());
        expectedModel.setActivity(lastActivity, editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);

         */
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        /*
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_PERSON, new EditActivityDescriptor());
        Activity editedActivity = model.getFilteredActivityList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);
        */
    }

    @Test
    public void execute_filteredList_success() {
        /*
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);

        Activity activityInFilteredList = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        Activity editedActivity = new ActivityBuilder(activityInFilteredList).withName(VALID_NAME_BOB).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY,
                new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());
        expectedModel.setActivity(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel);

         */
    }

    @Test
    public void execute_duplicateActivityUnfilteredList_failure() {
        /*
        Activity firstActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(firstActivity).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_SECOND_ACTIVITY, descriptor);

        assertCommandFailure(editActivityCommand, model, EditActivityCommand.MESSAGE_DUPLICATE_ACTIVITY);

         */
    }

    @Test
    public void execute_duplicateActivityFilteredList_failure() {
        /*
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);

        // edit activity in filtered list into a duplicate in nasa book
        Activity activityInList = model.getNasaBook().getActivityList().get(INDEX_SECOND_ACTIVITY.getZeroBased());
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY,
                new EditActivityDescriptorBuilder(activityInList).build());

        assertCommandFailure(editActivityCommand, model, EditActivityCommand.MESSAGE_DUPLICATE_ACTIVITY);

         */
    }

    @Test
    public void execute_invalidActivityIndexUnfilteredList_failure() {
        /*
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editActivityCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);

         */
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nasa book
     */
    @Test
    public void execute_invalidActivityIndexFilteredList_failure() {
        /*
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);
        Index outOfBoundIndex = INDEX_SECOND_ACTIVITY;
        // ensures that outOfBoundIndex is still in bounds of nasa book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getNasaBook().getActivityList().size());

        EditActivityCommand editActivityCommand = new EditActivityCommand(outOfBoundIndex,
                new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editActivityCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);

         */
    }

    @Test
    public void equals() {
        /*
        final EditActivityCommand standardCommand = new EditActivityCommand(INDEX_FIRST_ACTIVITY, DESC_AMY);

        // same values -> returns true
        EditActivityDescriptor copyDescriptor = new EditActivityDescriptor(DESC_AMY);
        EditActivityCommand commandWithSameValues = new EditActivityCommand(INDEX_FIRST_ACTIVITY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_SECOND_ACTIVITY, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_FIRST_ACTIVITY, DESC_BOB)));

         */
    }

}

