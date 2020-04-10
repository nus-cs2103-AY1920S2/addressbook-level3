package team.easytravel.model.listmanagers;

import java.nio.file.Path;

import team.easytravel.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    //Path getAddressBookFilePath();

    Path getTransportBookingStorageFilePath();

    Path getActivityStorageFilePath();

    Path getFixedExpenseStorageFilePath();

    Path getAccommodationBookingStorageFilePath();

    Path getPackingListStorageFilePath();

    Path getTripStorageFilePath();
}
