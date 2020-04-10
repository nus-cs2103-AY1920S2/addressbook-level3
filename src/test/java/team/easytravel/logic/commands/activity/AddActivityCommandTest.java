package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.ModelStub;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.testutil.activity.ActivityBuilder;

class AddActivityCommandTest {

    @Test
    public void constructor_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddActivityCommand(null));
    }

    @Test
    public void execute_activityAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validActivity = new ActivityBuilder().build();

        CommandResult commandResult = new AddActivityCommand(validActivity).execute(modelStub);

        assertEquals(String.format(AddActivityCommand.MESSAGE_SUCCESS, validActivity),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validActivity), modelStub.activityAdded);
    }

    @Test
    public void execute_duplicateActivity_throwsCommandException() {
        Activity validActivity = new ActivityBuilder().build();
        AddActivityCommand addActivityCommand = new AddActivityCommand(validActivity);
        ModelStub modelStub = new ModelStubWithActivity(validActivity);

        assertThrows(CommandException.class, () -> addActivityCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Activity japan = new ActivityBuilder().withTitle("Shopping").build();
        Activity finland = new ActivityBuilder().withTitle("Snow").build();
        AddActivityCommand addJapanCommand = new AddActivityCommand(japan);
        AddActivityCommand addFinCommand = new AddActivityCommand(finland);

        // same object -> returns true
        assertTrue(addFinCommand.equals(addFinCommand));

        // same values -> returns true
        AddActivityCommand addFinCommandCopy = new AddActivityCommand(finland);
        assertTrue(addFinCommand.equals(addFinCommandCopy));

        // different types -> returns false
        assertFalse(addFinCommand.equals(1));

        // null -> returns false
        assertFalse(addFinCommand.equals(null));

        // different person -> returns false
        assertFalse(addFinCommand.equals(addJapanCommand));
    }


    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithActivity extends ModelStub {
        private final Activity activity;

        ModelStubWithActivity(Activity activity) {
            requireNonNull(activity);
            this.activity = activity;
        }

        @Override
        public boolean hasTrip() {
            return true;
        }

        @Override
        public boolean hasActivity(Activity activity) {
            requireNonNull(activity);
            return this.activity.isSame(activity);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingActivityAdded extends ModelStub {
        final ArrayList<Activity> activityAdded = new ArrayList<>();

        @Override
        public boolean hasTrip() {
            return true;
        }

        @Override
        public boolean hasActivity(Activity activity) {
            requireNonNull(activity);
            return activityAdded.stream().anyMatch(activity::isSame);
        }

        @Override
        public void addActivity(Activity activity) {
            requireNonNull(activity);
            activityAdded.add(activity);
        }

        @Override
        public ReadOnlyActivityManager getActivityManager() {
            return new ActivityManager();
        }
    }

    @Test
    void execute() {
    }

    @Test
    void testEquals() {
    }
}
