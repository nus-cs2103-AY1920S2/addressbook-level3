package team.easytravel.testutil.trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.easytravel.commons.core.time.Date;
import team.easytravel.model.trip.Budget;
import team.easytravel.model.trip.ExchangeRate;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.util.attributes.Title;

/**
 * A utility class containing a list of {@code Trip} objects to be used in tests.
 */
public class TypicalTrip {

    public static final Trip TRIP_CHEESE = new Trip(new Title("Cheese Land"),
            Date.fromString("28-09-2020"), Date.fromString("05-10-2020"),
            new Budget(1000), new ExchangeRate(1.03));

    public static final Trip TRIP_GRAD = new Trip(new Title("Graduation Trip"),
            Date.fromString("20-03-2020"), Date.fromString("20-03-2020"),
            new Budget(5000), new ExchangeRate(3.0));

    public static final Trip TRIP_MOON = new Trip(new Title("Cheese Land"),
            Date.fromString("28-02-2021"), Date.fromString("05-03-2021"),
            new Budget(100), new ExchangeRate(12.0));

    private TypicalTrip() {
    }


    /**
     * Returns an {@code TypicalTrip} with all the typical Trip.
     */

    public static TripManager getTypicalTripManager() {
        TripManager tm = new TripManager();
        tm.setTrip(TRIP_CHEESE);
        return tm;
    }

    public static List<Trip> getTypicalTrip() {
        return new ArrayList<>(Arrays.asList(TRIP_CHEESE, TRIP_GRAD,
                TRIP_MOON));
    }

}
