package team.easytravel.logic.commands.accommodationbooking;

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
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.testutil.accommodationbooking.AccommodationBookingBuilder;

class AddAccommodationBookingCommandTest {

    @Test
    public void constructor_nullAccommodationBooking_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAccommodationBookingCommand(null));
    }

    @Test
    public void execute_accommodationBookingAcceptedByModel_addSuccessful() throws Exception {

        ModelStubAcceptingAccommodationBookingAdded modelStub = new ModelStubAcceptingAccommodationBookingAdded();
        AccommodationBooking validAccommodationBooking = new AccommodationBookingBuilder().build();

        CommandResult commandResult = new AddAccommodationBookingCommand(validAccommodationBooking).execute(modelStub);

        assertEquals(String.format(AddAccommodationBookingCommand.MESSAGE_SUCCESS, validAccommodationBooking),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAccommodationBooking), modelStub.accommodationBookingAdded);

    }

    @Test
    public void execute_duplicateAccommodationBooking_throwsCommandException() {
        AccommodationBooking validAccommodationBooking = new AccommodationBookingBuilder().build();
        AddAccommodationBookingCommand addAccommodationBookingCommand =
                new AddAccommodationBookingCommand(validAccommodationBooking);
        ModelStub modelStub = new ModelStubWithAccommodationBooking(validAccommodationBooking);

        assertThrows(CommandException.class, () -> addAccommodationBookingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AccommodationBooking accommodationBooking1 = new AccommodationBookingBuilder()
                .withAccommodationName("Ritz Carlton").build();
        AccommodationBooking accommodationBooking2 = new AccommodationBookingBuilder()
                .withAccommodationName("Marriott").build();
        AddAccommodationBookingCommand addAccommodationBooking1Command =
                new AddAccommodationBookingCommand(accommodationBooking1);
        AddAccommodationBookingCommand addAccommodationBooking2Command =
                new AddAccommodationBookingCommand(accommodationBooking2);

        // same object -> returns true
        assertTrue(addAccommodationBooking1Command.equals(addAccommodationBooking1Command));

        // same values -> returns true
        AddAccommodationBookingCommand addAccommodationBooking1CommandCopy =
                new AddAccommodationBookingCommand(accommodationBooking1);
        assertTrue(addAccommodationBooking1Command.equals(addAccommodationBooking1CommandCopy));

        // different types -> returns false
        assertFalse(addAccommodationBooking1Command.equals(1));

        // null -> returns false
        assertFalse(addAccommodationBooking1Command.equals(null));

        // different person -> returns false
        assertFalse(addAccommodationBooking1Command.equals(addAccommodationBooking2Command));
    }

    /**
     * A Model stub that contains a single person.
     */
    private static class ModelStubWithAccommodationBooking extends ModelStub {
        private final AccommodationBooking accommodationBooking;

        ModelStubWithAccommodationBooking(AccommodationBooking accommodationBooking) {
            requireNonNull(accommodationBooking);
            this.accommodationBooking = accommodationBooking;
        }

        @Override
        public boolean hasTrip() {
            return true;
        }

        @Override
        public int getTripNumDays() {
            return 10;
        }

        @Override
        public boolean isOverlappingWithOthers(AccommodationBooking accommodationBooking) {
            return false;
        }

        @Override
        public boolean hasAccommodationBooking(AccommodationBooking accommodationBooking) {
            requireNonNull(accommodationBooking);
            return this.accommodationBooking.isSame(accommodationBooking);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private static class ModelStubAcceptingAccommodationBookingAdded extends ModelStub {
        final ArrayList<AccommodationBooking> accommodationBookingAdded = new ArrayList<>();

        @Override
        public boolean hasTrip() {
            return true;
        }

        @Override
        public int getTripNumDays() {
            return 10;
        }

        @Override
        public boolean isOverlappingWithOthers(AccommodationBooking accommodationBooking) {
            return false;
        }

        @Override
        public boolean hasAccommodationBooking(AccommodationBooking accommodationBooking) {
            requireNonNull(accommodationBooking);
            return accommodationBookingAdded.stream().anyMatch(accommodationBooking::isSame);
        }

        @Override
        public void addAccommodationBooking(AccommodationBooking accommodationBooking) {
            requireNonNull(accommodationBooking);
            accommodationBookingAdded.add(accommodationBooking);
        }

        @Override
        public ReadOnlyAccommodationBookingManager getAccommodationBookingManager() {
            return new AccommodationBookingManager();
        }
    }

}
