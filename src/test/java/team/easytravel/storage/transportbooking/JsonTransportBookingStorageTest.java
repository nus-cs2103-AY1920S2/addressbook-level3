package team.easytravel.storage.transportbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_BOAT;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_PLANE;
import static team.easytravel.testutil.TypicalTransportBooking.TRANSPORT_BOOKING_SAMPAN;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.storage.activity.JsonActivityStorage;
import team.easytravel.testutil.TypicalTransportBooking;

class JsonTransportBookingStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonTransportBookingStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTransportBooking_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTransportBookingManager(null));
    }

    private Optional<ReadOnlyActivityManager> readTransportBookingManager(String filePath) throws Exception {
        return new JsonActivityStorage(Paths.get(filePath)).readActivityManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTransportBookingManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTransportBookingManager(
                "notJsonFormatTransportManager.json"));
    }

    @Test
    public void readActivityManager_invalidTransportBookingManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTransportBookingManager(
                "invalidTransportManager.json"));
    }

    @Test
    public void readActivityManager_invalidAndValidTransportBookingManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTransportBookingManager(
                "invalidAndValidTransportManager.json"));
    }

    @Test
    public void readAndSaveTransportBookingManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempActivityManager.json");
        TransportBookingManager original = TypicalTransportBooking.getTypicalTransportBookingManager();
        JsonTransportBookingStorage jsonTransportBookingStorage = new JsonTransportBookingStorage(filePath);

        //Save in new file and read back
        jsonTransportBookingStorage.saveTransportBookings(original, filePath);
        ReadOnlyTransportBookingManager readBack = jsonTransportBookingStorage.readTransportBookings(filePath).get();
        assertEquals(original, new TransportBookingManager(readBack));

        //Modify data, overwrite exiting file and read back
        original.addTransportBooking(TRANSPORT_BOOKING_SAMPAN);
        original.removeTransportBooking(TRANSPORT_BOOKING_PLANE);
        jsonTransportBookingStorage.saveTransportBookings(original, filePath);
        readBack = jsonTransportBookingStorage.readTransportBookings(filePath).get();
        assertEquals(original, new TransportBookingManager(readBack));

        // Save and read without specifying file path
        original.addTransportBooking(TRANSPORT_BOOKING_BOAT);
        jsonTransportBookingStorage.saveTransportBookings(original);
        readBack = jsonTransportBookingStorage.readTransportBookings().get();
        assertEquals(original, new TransportBookingManager(readBack));
    }

    @Test
    public void saveActivityManager_nullTransportBookingManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTransportBookingManager(
                null, "SomeFile.json"));
    }

    /**
     * Saves {@code transportBookingManager} at specified {@code filePath}.
     */
    private void saveTransportBookingManager(ReadOnlyTransportBookingManager transportBookingManager, String filePath) {
        try {
            new JsonTransportBookingStorage(Paths.get(filePath))
                    .saveTransportBookings(transportBookingManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file ", ioe);
        }
    }

    @Test
    public void saveTransportBookingManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTransportBookingManager(
                new TransportBookingManager(), null));
    }


}
