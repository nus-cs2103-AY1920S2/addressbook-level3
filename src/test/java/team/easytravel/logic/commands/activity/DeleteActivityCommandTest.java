package team.easytravel.logic.commands.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.logic.commands.CommandTestUtil.assertActivityCommandFailure;
import static team.easytravel.logic.commands.CommandTestUtil.assertActivityCommandSuccess;
import static team.easytravel.logic.commands.CommandTestUtil.showActivityAtIndex;
import static team.easytravel.testutil.TypicalIndexes.INDEX_FIRST;
import static team.easytravel.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
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
import team.easytravel.testutil.activity.TypicalActivity;
import team.easytravel.testutil.trip.TripBuilder;
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteActivityCommandTest {

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
    public void execute_validIndexUnfilteredList_success() {

        Activity activityToDelete = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteActivityCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), model.getActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);

        assertActivityCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(outOfBoundIndex);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity");

        assertActivityCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showActivityAtIndex(model, INDEX_FIRST);

        Activity activityToDelete = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteActivityCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), model.getActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());

        expectedModel.deleteActivity(activityToDelete);
        showNoActivity(expectedModel);

        assertActivityCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showActivityAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getActivityManager().getActivityList().size());

        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(outOfBoundIndex);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity");

        assertActivityCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteActivityCommand deleteFirstCommand = new DeleteActivityCommand(INDEX_FIRST);
        DeleteActivityCommand deleteSecondCommand = new DeleteActivityCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteActivityCommand deleteFirstCommandCopy = new DeleteActivityCommand(INDEX_FIRST);
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
    private void showNoActivity(Model model) {
        model.updateFilteredActivityList(p -> false);

        assertTrue(model.getFilteredActivityList().isEmpty());
    }
}
