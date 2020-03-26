package seedu.address.storage;

import seedu.address.storage.accommodationbooking.AccommodationBookingStorage;
import seedu.address.storage.activity.ActivityManagerStorage;
import seedu.address.storage.fixedexpense.FixedExpenseStorage;
import seedu.address.storage.packinglist.PackingListStorage;
import seedu.address.storage.transportbooking.TransportBookingStorage;
import seedu.address.storage.trip.TripManagerStorage;

/**
 * API of the Storage component
 */
public interface Storage extends TransportBookingStorage, FixedExpenseStorage,
        ActivityManagerStorage, AccommodationBookingStorage, PackingListStorage, TripManagerStorage, UserPrefsStorage {
}
