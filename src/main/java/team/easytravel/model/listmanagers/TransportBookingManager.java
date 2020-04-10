package team.easytravel.model.listmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import team.easytravel.commons.core.time.Date;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.util.uniquelist.UniqueList;

/**
 * Wraps all data at the TransportBookingManager level
 * Duplicates are not allowed (by .isSame comparison)
 */
public class TransportBookingManager implements ReadOnlyTransportBookingManager {
    private final UniqueList<TransportBooking> transportBookings;

    /**
     * Instantiates a new TransportBookingManager.
     */
    public TransportBookingManager() {
        transportBookings = new UniqueList<>();
    }

    /**
     * Creates a TransportBookingManager using the TransportBookings in the {@code toBeCopied}
     */
    public TransportBookingManager(ReadOnlyTransportBookingManager toBeCopied) {
        transportBookings = new UniqueList<>();
        resetData(toBeCopied);
    }

    // List overwrite operations

    /**
     * Replaces the contents of the transport booking list with {@code transportBookings}.
     * {@code transportBookings} must not contain duplicate transport bookings.
     */
    public void setTransportBookings(List<TransportBooking> transportBookings) {
        this.transportBookings.setElements(transportBookings);
    }

    /**
     * Sorts the fixed expense list with the specified comparator.
     */
    public void sortTransportList(Comparator<TransportBooking> cmp) {
        transportBookings.sort(cmp);
    }

    /**
     * Resets the existing data of this {@code TransportBookingManager} with {@code newData}.
     */
    public void resetData(ReadOnlyTransportBookingManager newData) {
        requireNonNull(newData);
        setTransportBookings(newData.getTransportBookings());
    }

    /**
     * Clears all invalid transport booking with a start and end date.
     * A transport booking is invalid when it starts earlier than the given start date or end later than the given end
     * date.
     */
    public void removeInvalidTransportBookings(Date tripStartDate, Date tripEndDate) {
        setTransportBookings(getTransportBookings()
                .stream()
                .filter(transportBooking -> transportBooking.getStartDateTime().getDate().compareTo(tripStartDate) >= 0
                        && transportBooking.getEndDateTime().getDate().compareTo(tripEndDate) <= 0)
                .collect(Collectors.toList()));
    }

    // TransportBooking-level operations

    /**
     * Returns true if a transport booking with the same identity as {@code transportBooking} exists in the
     * TransportBookingManager.
     */
    public boolean hasTransportBooking(TransportBooking transportBooking) {
        requireNonNull(transportBooking);
        return transportBookings.contains(transportBooking);
    }

    /**
     * Adds a transport booking to the TransportBookingManager.
     * The transport booking must not already exist in the TransportBookingManager.
     */
    public void addTransportBooking(TransportBooking transportBooking) {
        transportBookings.add(transportBooking);
    }

    /**
     * Replaces the given transport booking {@code target} in the list with {@code editedTransportBooking}.
     * {@code target} must exist in the TransportBookingManager.
     * The transport booking identity of {@code editedTransportBooking} must not be the same as another existing
     * transport booking in the TransportBookingManager.
     */
    public void setTransportBooking(TransportBooking target, TransportBooking editedTransportBooking) {
        requireNonNull(editedTransportBooking);

        transportBookings.setElement(target, editedTransportBooking);
    }

    /**
     * Removes {@code key} from this {@code TransportBookingManager}.
     * {@code key} must exist in the TransportBookingManager.
     */
    public void removeTransportBooking(TransportBooking key) {
        transportBookings.remove(key);
    }

    // Util methods

    @Override
    public String toString() {
        return transportBookings.asUnmodifiableObservableList().size() + " transport bookings";
        // TODO: refine later
    }

    @Override
    public ObservableList<TransportBooking> getTransportBookings() {
        return transportBookings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransportBookingManager // instanceof handles nulls
                && transportBookings.equals(((TransportBookingManager) other).transportBookings));
    }

    @Override
    public int hashCode() {
        return transportBookings.hashCode();
    }
}
