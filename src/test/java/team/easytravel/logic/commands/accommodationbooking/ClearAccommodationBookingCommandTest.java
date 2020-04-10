package team.easytravel.logic.commands.accommodationbooking;

import static team.easytravel.logic.commands.CommandTestUtil.assertAccommodationBookingCommandSuccess;

import org.junit.jupiter.api.Test;

import team.easytravel.model.Model;
import team.easytravel.model.ModelManager;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;
import team.easytravel.testutil.TypicalAccommodation;
import team.easytravel.testutil.trip.TripBuilder;

class ClearAccommodationBookingCommandTest {

    @Test
    public void execute_emptyAccommodationBookingManager_success() {
        Trip newTrip = new TripBuilder().build();
        TripManager tripManagerSet = new TripManager();
        tripManagerSet.setTrip(newTrip);

        Model model = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());
        Model expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                new AccommodationBookingManager(), tripManagerSet,
                new UserPrefs());


        assertAccommodationBookingCommandSuccess(new ClearAccommodationBookingCommand(), model,
                ClearAccommodationBookingCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAccommodationBookingManager_success() {
        Trip newTrip = new TripBuilder().build();
        TripManager tripManagerSet = new TripManager();
        tripManagerSet.setTrip(newTrip);

        Model model = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                TypicalAccommodation.getTypicalAccommodationManager(), tripManagerSet, new UserPrefs());

        Model expectedModel = new ModelManager(new TransportBookingManager(),
                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(),
                TypicalAccommodation.getTypicalAccommodationManager(), tripManagerSet, new UserPrefs());

        expectedModel.setAccommodationBookingManager(new AccommodationBookingManager());

        assertAccommodationBookingCommandSuccess(new ClearAccommodationBookingCommand(), model,
                ClearAccommodationBookingCommand.MESSAGE_SUCCESS, expectedModel);
    }


}
