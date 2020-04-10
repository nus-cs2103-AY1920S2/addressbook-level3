package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.ModelStub;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.trip.exception.IllegalOperationException;
import team.easytravel.testutil.trip.TripBuilder;

class SetTripCommandTest {

    @Test
    public void constructor_nullTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetTripCommand(null));
    }

    @Test
    public void execute_tripAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTripAdded modelStub = new ModelStubAcceptingTripAdded();
        Trip validTrip = new TripBuilder().build();

        CommandResult commandResult = new SetTripCommand(validTrip).execute(modelStub);

        assertEquals(String.format(SetTripCommand.MESSAGE_SUCCESS, validTrip),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTrip), modelStub.tripAdded);
    }

    @Test
    public void execute_duplicateTrip_throwsCommandException() {
        Trip validTrip = new TripBuilder().build();
        SetTripCommand setTripCommand = new SetTripCommand(validTrip);
        ModelStub modelStub = new ModelStubWithTrip(validTrip);

        assertThrows(CommandException.class, () -> setTripCommand.execute(modelStub));
    }

    @Test
    public void execute_setMoreTrip_throwsCommandException() {
        Trip validTrip = new TripBuilder().build();
        ModelStub modelStub = new ModelStubWithTrip(validTrip);

        assertThrows(IllegalOperationException.class, ()-> modelStub.setTrip(validTrip));
    }

    /**
     * A Model stub that contains a single trip.
     */
    private class ModelStubWithTrip extends ModelStub {
        private final Trip trip;
        private final TripManager tm = new TripManager();

        ModelStubWithTrip(Trip trip) {
            requireNonNull(trip);
            this.trip = trip;
            tm.setTrip(trip);
        }

        @Override
        public boolean hasTrip() {
            return true;
        }

        @Override
        public void setTrip(Trip trip) {
            tm.setTrip(trip);
        }

        @Override
        public TripManager getTripManager() {
            return tm;
        }
    }

    /**
     * A Model stub that always accept the Trip being added.
     */
    private class ModelStubAcceptingTripAdded extends ModelStub {
        final ArrayList<Trip> tripAdded = new ArrayList<>();

        @Override
        public boolean hasTrip() {
            return tripAdded.size() > 0;
        }


        @Override
        public void setTrip(Trip trip) {
            requireNonNull(trip);
            tripAdded.add(trip);
        }

    }


    @Test
    void execute() {
    }

    @Test
    void testEquals() {
    }
}
