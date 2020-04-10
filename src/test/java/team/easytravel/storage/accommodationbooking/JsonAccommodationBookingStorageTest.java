package team.easytravel.storage.accommodationbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_BACKPACKER;
import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_CAMP;
import static team.easytravel.testutil.TypicalAccommodation.ACCOMMODATION_BOOKING_HDB;
import static team.easytravel.testutil.TypicalAccommodation.getTypicalAccommodationManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;

class JsonAccommodationBookingStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonAccommodationBookingStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAccommodationBookingManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAccommodationBookingManager(null));
    }

    private java.util.Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookingManager(
            String filePath) throws Exception {
        return new JsonAccommodationBookingStorage(Paths.get(filePath)).readAccommodationBookings(
                addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAccommodationBookingManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAccommodationBookingManager(
                "notJsonFormatAccommodationBookingManager.json"));
    }

    @Test
    public void readAccommodationBookingManager_invalidAccommodationBookingManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAccommodationBookingManager
                ("invalidAccommodationAccommodationManager.json"));
    }

    @Test
    public void readAccommodationBooking_invalidAndValidAccommodationBookingManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAccommodationBookingManager
                ("invalidAndValidAccommodationAccommodationBookingManager.json"));
    }

    @Test
    public void readAndSaveAccommodationBooking_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAccommodationBooking.json");
        AccommodationBookingManager origin = getTypicalAccommodationManager();
        JsonAccommodationBookingStorage jsonAccommodationBookingStorage = new JsonAccommodationBookingStorage(filePath);

        jsonAccommodationBookingStorage.saveAccommodationBookings(origin, filePath);
        ReadOnlyAccommodationBookingManager readBack = jsonAccommodationBookingStorage
                .readAccommodationBookings(filePath).get();
        assertEquals(origin, new AccommodationBookingManager(readBack));

        origin.addAccommodationBooking(ACCOMMODATION_BOOKING_HDB);
        origin.removeAccommodationBooking(ACCOMMODATION_BOOKING_BACKPACKER);
        jsonAccommodationBookingStorage.saveAccommodationBookings(origin, filePath);
        readBack = jsonAccommodationBookingStorage.readAccommodationBookings(filePath).get();
        assertEquals(origin, new AccommodationBookingManager(readBack));

        origin.addAccommodationBooking(ACCOMMODATION_BOOKING_CAMP);
        jsonAccommodationBookingStorage.saveAccommodationBookings(origin);
        readBack = jsonAccommodationBookingStorage.readAccommodationBookings().get();
        assertEquals(origin, new AccommodationBookingManager(readBack));



    }

    @Test
    public void saveAccommodationBookingManager_nullAccommodationBookingManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAccommodationBookings(
                null, "SomeFile.json"));
    }

    /**
     * Saves {@code accommodationBookingManager} at the specified {@code filePath}.
     */
    private void saveAccommodationBookings(ReadOnlyAccommodationBookingManager addressBook, String filePath) {
        try {
            new JsonAccommodationBookingStorage(Paths.get(filePath))
                    .saveAccommodationBookings(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAccommodationBookingManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAccommodationBookings(new AccommodationBookingManager(),
                null));
    }

}
