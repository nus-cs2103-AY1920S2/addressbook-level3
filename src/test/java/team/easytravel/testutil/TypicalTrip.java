package team.easytravel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.model.trip.Budget;
import team.easytravel.model.trip.ExchangeRate;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.attributes.Title;

/**
 * A utility class containing a list of {@code Trip} objects to be used in tests.
 */
public class TypicalTrip {

    public static final Trip TRIP_HONGKONG =
            new Trip(new Title("Graduation Trip"), DateTime.fromString("20-01-2020 00:00").getDate(),
                    DateTime.fromString("30-01-2020 00:00").getDate(),
                   new Budget(3000), new ExchangeRate(1.03));

    public static final Trip TRIP_MOSCOW =
            new Trip(new Title("Graduation Trip"), DateTime.fromString("20-02-2020 00:00").getDate(),
                    DateTime.fromString("28-02-2020 00:00").getDate(),
                    new Budget(5000), new ExchangeRate(1.50));

    public TypicalTrip() {
    }

    /**
     * Returns an {@code TypicalTrip} with all the typical Trip.
     */

    public static TripManager getTypicalTripManager() {
        TripManager am = new TripManager();
        for (Trip trip : getTypicalTrip()) {
            am.setTrip(trip);
        }
        return am;
    }

    public static List<Trip> getTypicalTrip() {
        return new ArrayList<>(Arrays.asList(TRIP_HONGKONG));
    }



}
