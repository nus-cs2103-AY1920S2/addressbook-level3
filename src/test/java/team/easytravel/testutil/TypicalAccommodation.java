package team.easytravel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.util.attributes.Location;

/**
 * A utility class containing a list of {@code Accommodations} objects to be used in tests.
 */
public class TypicalAccommodation {

    public static final AccommodationBooking ACCOMMODATION_BOOKING_HOTEL = new AccommodationBooking(
            new AccommodationName("Hotel"), new Location("Singapore"),
            new Day(1), new Day(3), new Remark("2 nights stay"));

    public static final AccommodationBooking ACCOMMODATION_BOOKING_HOSTEL = new AccommodationBooking(
            new AccommodationName("Hostel"), new Location("Singapore"),
            new Day(3), new Day(4), new Remark("1 night stay"));

    public static final AccommodationBooking ACCOMMODATION_BOOKING_BACKPACKER = new AccommodationBooking(
            new AccommodationName("Backpacker"), new Location("Singapore"),
            new Day(5), new Day(7), new Remark("2 nights stay"));

    // Manually Added -- DONT USE THESE TWO, only for storage tests!!
    public static final AccommodationBooking ACCOMMODATION_BOOKING_CAMP = new AccommodationBooking(
            new AccommodationName("CAMP"), new Location("Singapore"),
            new Day(9), new Day(11), new Remark("2 nights stay"));

    public static final AccommodationBooking ACCOMMODATION_BOOKING_HDB = new AccommodationBooking(
            new AccommodationName("HDB"), new Location("Singapore"),
            new Day(7), new Day(9), new Remark("2 nights stay"));

    private TypicalAccommodation() {
    }


    /**
     * Returns an {@code TypicalAccommodations} with all the typical Accommodations.
     */

    public static AccommodationBookingManager getTypicalAccommodationManager() {
        AccommodationBookingManager am = new AccommodationBookingManager();
        for (AccommodationBooking ab : getTypicalAccommodations()) {
            am.addAccommodationBooking(ab);
        }
        return am;
    }

    public static List<AccommodationBooking> getTypicalAccommodations() {
        return new ArrayList<>(Arrays.asList(ACCOMMODATION_BOOKING_HOTEL,
                ACCOMMODATION_BOOKING_BACKPACKER,
                ACCOMMODATION_BOOKING_HOSTEL));
    }

}
