package team.easytravel.logic.commands.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.logic.commands.CommandTestUtil.DESC_FINLAND;
import static team.easytravel.logic.commands.CommandTestUtil.DESC_JAPAN;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_ACTIVITY_DURATION_FINLAND;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE_FINLAND;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_LOCATION_FINLAND;
import static team.easytravel.logic.commands.CommandTestUtil.VALID_TAG_SIGHTSEEING;
import static team.easytravel.logic.commands.CommandTestUtil.assertActivityCommandFailure;
import static team.easytravel.logic.commands.CommandTestUtil.assertActivityCommandSuccess;
import static team.easytravel.logic.commands.CommandTestUtil.showActivityAtIndex;
import static team.easytravel.testutil.TypicalIndexes.INDEX_FIRST;
import static team.easytravel.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.activity.EditActivityCommand.EditActivityDescriptor;
import team.easytravel.model.Model;
import team.easytravel.model.ModelManager;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;
import team.easytravel.testutil.activity.ActivityBuilder;
import team.easytravel.testutil.activity.EditActivityDescriptorBuilder;
import team.easytravel.testutil.activity.TypicalActivity;
import team.easytravel.testutil.trip.TripBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditActivityCommand.
 */
public class EditActivityCommandTest {

    private Model model;
    private TripManager tripManagerSet;

    @BeforeEach
    public void setUp() {
        Trip newTrip = new TripBuilder().build();
        tripManagerSet = new TripManager();
        tripManagerSet.setTrip(newTrip);
        model = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), TypicalActivity.getTypicalActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Activity editedActivity = new ActivityBuilder().build();
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();
        EditActivityCommand editCommand = new EditActivityCommand(INDEX_FIRST, descriptor);

        String expectedMessage =
                String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), model.getActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());

        expectedModel.addActivity(editedActivity);

        assertActivityCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastActivity = Index.fromOneBased(model.getFilteredActivityList().size());
        Activity lastActivity = model.getFilteredActivityList().get(indexLastActivity.getZeroBased());

        ActivityBuilder activityInList = new ActivityBuilder(lastActivity);
        Activity editedActivity = activityInList.withTitle(VALID_ACTIVITY_TITLE_FINLAND)
                .withDuration(VALID_ACTIVITY_DURATION_FINLAND)
                .withLocation(VALID_LOCATION_FINLAND)
                .withTags(VALID_TAG_SIGHTSEEING).build();

        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE_FINLAND)
                .withDuration(VALID_ACTIVITY_DURATION_FINLAND)
                .withLocation(VALID_LOCATION_FINLAND)
                .withTags(VALID_TAG_SIGHTSEEING).build();

        EditActivityCommand editCommand = new EditActivityCommand(indexLastActivity, descriptor);

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), model.getActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());

        expectedModel.setActivity(lastActivity, editedActivity);

        assertActivityCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditActivityCommand editCommand = new EditActivityCommand(INDEX_FIRST, new EditActivityDescriptor());
        Activity editedActivity = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), model.getActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());

        assertActivityCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showActivityAtIndex(model, INDEX_FIRST);

        Activity activityInFilteredList = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        Activity editedActivity = new ActivityBuilder(activityInFilteredList).withTitle(VALID_ACTIVITY_TITLE_FINLAND)
                .build();
        EditActivityCommand editCommand = new EditActivityCommand(INDEX_FIRST,
                new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE_FINLAND).build());

        String expectedMessage = String.format(EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), model.getActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());

        expectedModel.addActivity(editedActivity);

        assertActivityCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateActivityUnfilteredList_failure() {
        Activity firstActivity = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(firstActivity).build();
        EditActivityCommand editCommand = new EditActivityCommand(INDEX_SECOND, descriptor);

        assertActivityCommandFailure(editCommand, model, EditActivityCommand.MESSAGE_DUPLICATE_ACTIVITY);
    }

    @Test
    public void execute_duplicateActivityFilteredList_failure() {
        showActivityAtIndex(model, INDEX_FIRST);

        // edit activity in filtered list into a duplicate in activity manager
        Activity activityInList = model.getActivityManager().getActivityList().get(INDEX_SECOND.getZeroBased());
        EditActivityCommand editCommand = new EditActivityCommand(INDEX_FIRST,
                new EditActivityDescriptorBuilder(activityInList).build());

        assertActivityCommandFailure(editCommand, model, EditActivityCommand.MESSAGE_DUPLICATE_ACTIVITY);
    }

    @Test
    public void execute_invalidActivityIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE_FINLAND)
                .build();
        EditActivityCommand editCommand = new EditActivityCommand(outOfBoundIndex, descriptor);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity");

        assertActivityCommandFailure(editCommand, model, expectedMessage);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of activity Manager
     */
    @Test
    public void execute_invalidActivityIndexFilteredList_failure() {
        showActivityAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of activity list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getActivityManager().getActivityList().size());

        EditActivityCommand editCommand = new EditActivityCommand(outOfBoundIndex,
                new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE_FINLAND).build());

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity");

        assertActivityCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final EditActivityCommand standardCommand = new EditActivityCommand(INDEX_FIRST, DESC_JAPAN);

        // same values -> returns true
        EditActivityDescriptor copyDescriptor = new EditActivityDescriptor(DESC_JAPAN);
        EditActivityCommand commandWithSameValues = new EditActivityCommand(INDEX_FIRST, copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearActivityCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_SECOND, DESC_JAPAN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_FIRST, DESC_FINLAND)));
    }
}

