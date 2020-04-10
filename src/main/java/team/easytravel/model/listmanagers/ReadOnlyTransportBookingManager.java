package team.easytravel.model.listmanagers;

import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;

/**
 * Unmodifiable view of a TransportBookingManager.
 */
public interface ReadOnlyTransportBookingManager {

    /**
     * Returns an unmodifiable view of the transport bookings list.
     * This list will not contain any duplicate transport bookings.
     */
    ObservableList<TransportBooking> getTransportBookings();
}
