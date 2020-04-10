package team.easytravel.storage;

import team.easytravel.storage.accommodationbooking.AccommodationBookingStorage;
import team.easytravel.storage.activity.ActivityStorage;
import team.easytravel.storage.fixedexpense.FixedExpenseStorage;
import team.easytravel.storage.packinglist.PackingListStorage;
import team.easytravel.storage.transportbooking.TransportBookingStorage;
import team.easytravel.storage.trip.TripManagerStorage;

/**
 * API of the Storage component
 */
public interface Storage extends TransportBookingStorage, FixedExpenseStorage,
        ActivityStorage, AccommodationBookingStorage, PackingListStorage, TripManagerStorage, UserPrefsStorage {
}
