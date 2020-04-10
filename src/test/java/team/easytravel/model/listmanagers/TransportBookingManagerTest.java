package team.easytravel.model.listmanagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_PLANE;
import static team.easytravel.testutil.TypicalTransportBooking.getTypicalTransportBookingManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.util.uniquelist.exceptions.DuplicateElementException;

class TransportBookingManagerTest {

    private final TransportBookingManager transportBookingManager = new TransportBookingManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), transportBookingManager.getTransportBookings());
    }

    @Test
    public void resetDataNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transportBookingManager.resetData(null));
    }

    @Test
    public void resetDataWithValidReadOnlyTransportBookingManagerReplacesData() {
        TransportBookingManager newData = getTypicalTransportBookingManager();
        transportBookingManager.resetData(newData);
        assertEquals(newData, transportBookingManager);
    }

    @Test
    public void resetDataWithDuplicateTransportBookingThrowsDuplicateElementException() {
        List<TransportBooking> newTransportBookings = Arrays.asList(TRANSPORT_BOOKING_PLANE, TRANSPORT_BOOKING_PLANE);
        TransportBookingManagerStub newData = new TransportBookingManagerStub(newTransportBookings);
        assertThrows(DuplicateElementException.class, () -> transportBookingManager.resetData(newData));
    }

    @Test
    public void hasTransportBookingNullTransportBookingThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transportBookingManager
                .hasTransportBooking(null));
    }

    @Test
    public void hasTransportBookingNotInTransportBookingManagerReturnsFalse() {
        assertFalse(transportBookingManager.hasTransportBooking(TRANSPORT_BOOKING_PLANE));
    }

    @Test
    public void hasTransportBookingInTransportBookingManagerReturnsTrue() {
        transportBookingManager.addTransportBooking(TRANSPORT_BOOKING_PLANE);
        assertTrue(transportBookingManager.hasTransportBooking(TRANSPORT_BOOKING_PLANE));
    }

    @Test
    public void getTransportBookingModifyListThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> transportBookingManager
                .getTransportBookings().remove(0));
    }

    /**
     * A stub ReadOnlyTransportBookingManager whose accommodation list can violate interface constraints.
     */
    private static class TransportBookingManagerStub implements ReadOnlyTransportBookingManager {
        private final ObservableList<TransportBooking> transportBookings =
                FXCollections.observableArrayList();

        TransportBookingManagerStub(Collection<TransportBooking> transportBookings) {
            this.transportBookings.setAll(transportBookings);
        }

        @Override
        public ObservableList<TransportBooking> getTransportBookings() {
            return transportBookings;
        }
    }
}
