package team.easytravel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.transportbooking.Mode;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.util.attributes.Location;

/**
 * A utility class containing a list of {@code TransportBooking} objects to be used in tests.
 */
public class TypicalTransportBooking {

    public static final TransportBooking TRANSPORT_BOOKING_TRAIN = new TransportBooking(
            new Mode("train"), new Location("Singapore"), new Location("Malaysia"),
            DateTime.fromString("20-01-2020 22:00"), DateTime.fromString("20-01-2020 23:00")
    );

    public static final TransportBooking TRANSPORT_BOOKING_PLANE = new TransportBooking(
            new Mode("plane"), new Location("Singapore"), new Location("Hong Kong"),
            DateTime.fromString("23-03-2020 00:15"), DateTime.fromString("23-03-2020 05:15")
    );

    public static final TransportBooking TRANSPORT_BOOKING_BUS = new TransportBooking(
            new Mode("bus"), new Location("Hong Kong"), new Location("Macau"),
            DateTime.fromString("24-03-2020 19:00"), DateTime.fromString("24-03-2020 20:50")
    );

    //Dont delete, used for storage tests
    public static final TransportBooking TRANSPORT_BOOKING_BOAT = new TransportBooking(
            new Mode("train"), new Location("Hong Kong"), new Location("Macau"),
            DateTime.fromString("24-05-2020 19:00"), DateTime.fromString("24-05-2020 20:50")
    );

    public static final TransportBooking TRANSPORT_BOOKING_SAMPAN = new TransportBooking(
            new Mode("others"), new Location("China"), new Location("Moscow"),
            DateTime.fromString("24-06-2020 19:00"), DateTime.fromString("24-06-2020 20:50")
    );

    private TypicalTransportBooking() {}

    /**
     * Returns an {@code TypicalTransportBooking} with all the typical TransportBooking.
     */

    public static TransportBookingManager getTypicalTransportBookingManager() {
        TransportBookingManager tb = new TransportBookingManager();
        for (TransportBooking transportBooking: getTypicalTransportBookings()) {
            tb.addTransportBooking(transportBooking);
        }
        return tb;
    }

    public static List<TransportBooking> getTypicalTransportBookings() {
        return new ArrayList<>(Arrays.asList(TRANSPORT_BOOKING_TRAIN, TRANSPORT_BOOKING_PLANE,
                TRANSPORT_BOOKING_BUS));
    }

}
