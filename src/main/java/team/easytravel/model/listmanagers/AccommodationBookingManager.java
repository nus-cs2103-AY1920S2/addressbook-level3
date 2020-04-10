package team.easytravel.model.listmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.util.uniquelist.UniqueList;

/**
 * Wraps all data at the AccommodationBookingManager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AccommodationBookingManager implements ReadOnlyAccommodationBookingManager {
    private final UniqueList<AccommodationBooking> accommodationBookings;

    /**
     * Instantiates a new AccommodationBookingManager.
     */
    public AccommodationBookingManager() {
        accommodationBookings = new UniqueList<>();
    }

    /**
     * Creates an AccommodationBookingManager using the AccommodationBookings in the {@code toBeCopied}
     */
    public AccommodationBookingManager(ReadOnlyAccommodationBookingManager toBeCopied) {
        accommodationBookings = new UniqueList<>();
        resetData(toBeCopied);
    }

    // List overwrite operations

    /**
     * Replaces the contents of the accommodation booking list with {@code accommodation bookings}.
     * {@code accommodationBookings} must not contain duplicate accommodation bookings.
     */
    public void setAccommodationBookings(List<AccommodationBooking> accommodationBookings) {
        this.accommodationBookings.setElements(accommodationBookings);
    }

    /**
     * Resets the existing data of this {@code AccommodationBookingManager} with {@code newData}.
     */
    public void resetData(ReadOnlyAccommodationBookingManager newData) {
        requireNonNull(newData);
        setAccommodationBookings(newData.getAccommodationBookingList());
    }

    // AccommodationBooking-level operations

    /**
     * Returns true if a accommodation booking with the same identity as {@code accommodationBooking} exists in the
     * AccommodationBookingManager.
     */
    public boolean hasAccommodationBooking(AccommodationBooking accommodationBooking) {
        requireNonNull(accommodationBooking);
        return accommodationBookings.contains(accommodationBooking);
    }

    /**
     * Adds a accommodation booking to the AccommodationBookingManager.
     * The accommodation booking must not already exist in the AccommodationBookingManager.
     */
    public void addAccommodationBooking(AccommodationBooking accommodationBooking) {
        accommodationBookings.add(accommodationBooking);
    }

    /**
     * Replaces the given accommodation booking {@code target} in the list with {@code editedAccommodationBooking}.
     * {@code target} must exist in the AccommodationBookingManager.
     * The accommodation booking identity of {@code editedAccommodationBooking} must not be the same as another existing
     * accommodation booking in the AccommodationBookingManager.
     */
    public void setAccommodationBooking(AccommodationBooking target, AccommodationBooking editedAccommodationBooking) {
        requireNonNull(editedAccommodationBooking);

        accommodationBookings.setElement(target, editedAccommodationBooking);
    }

    /**
     * Sorts the accommodation bookings based on the start day.
     */
    public void sortAccommodationBookings() {
        accommodationBookings.sort(Comparator.comparingInt(x -> x.getStartDay().value));
    }

    /**
     * Sorts the fixed expense list with the specified comparator.
     */
    public void sortAccommodationBookings(Comparator<AccommodationBooking> cmp) {
        accommodationBookings.sort(cmp);
    }

    /**
     * Checks the accommodation booking if there are any missing days that have no accommodation bookings.
     * @param endDay the number of days of the trip
     * @return the status for accommodation booking
     */
    public String getStatus(int endDay) {
        StringBuilder result = new StringBuilder("[❌] Accommodation for night");
        boolean accommodationBookingIsOk = true;
        boolean[] accommodationDayCheck = new boolean[endDay - 1];

        getAccommodationBookingList()
                .forEach(x -> IntStream.range(x.getStartDay().value, x.getEndDay().value)
                        .forEach(y -> accommodationDayCheck[y - 1] = true));

        for (int i = 0; i < endDay - 1; i++) {
            if (!accommodationDayCheck[i]) {
                accommodationBookingIsOk = false;
                result.append(" ").append(i + 1).append(",");
            }
        }

        if (accommodationBookingIsOk) {
            return "[✔] Accommodation Booking is completed for all nights.";
        }

        return result.append(" is/are missing").toString();
    }

    /**
     * Removes {@code key} from this {@code AccommodationBookingManager}.
     * {@code key} must exist in the AccommodationBookingManager.
     */
    public void removeAccommodationBooking(AccommodationBooking key) {
        accommodationBookings.remove(key);
    }

    // Util methods

    @Override
    public String toString() {
        return accommodationBookings.asUnmodifiableObservableList().size() + " accommodation bookings";
        // TODO: refine later
    }

    @Override
    public ObservableList<AccommodationBooking> getAccommodationBookingList() {
        return accommodationBookings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccommodationBookingManager // instanceof handles nulls
                && accommodationBookings.equals(((AccommodationBookingManager) other).accommodationBookings));
    }

    @Override
    public int hashCode() {
        return accommodationBookings.hashCode();
    }
}
