package team.easytravel.storage.accommodationbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalAccommodation.getTypicalAccommodationManager;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.commons.util.JsonUtil;
import team.easytravel.model.listmanagers.AccommodationBookingManager;

class JsonSerializableAccommodationBookingManagerTest {

    public static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAccommodationBookingManagerTest");

    public static final Path TYPICAL_ACCOMMODATION_BOOKING_FILE = TEST_DATA_FOLDER
            .resolve("typicalAccommodationBookingAccommodationBookingManager.json");
    public static final Path INVALID_ACCOMMODATION_BOOKING_FILE = TEST_DATA_FOLDER
            .resolve("invalidAccommodationBookingAccommodationBookingManager.json");
    public static final Path DUPLICATE_ACCOMMODATION_BOOKING_FILE = TEST_DATA_FOLDER
            .resolve("duplicateAccommodationBookingAccommodationBookingManager.json");

    @Test
    public void toModelType_typicalAccommodationBookingFile_success() throws Exception {
        JsonSerializableAccommodationBookingManager dataFromFile = JsonUtil
                .readJsonFile(TYPICAL_ACCOMMODATION_BOOKING_FILE,
                JsonSerializableAccommodationBookingManager.class).get();
        AccommodationBookingManager accommodationBookingManagerFromFile = dataFromFile.toModelType();
        AccommodationBookingManager typicalAccommodationBookingAccommodationBookingManager =
                getTypicalAccommodationManager();
        assertEquals(accommodationBookingManagerFromFile, typicalAccommodationBookingAccommodationBookingManager);
    }

    @Test
    public void toModelType_invalidAccommodationFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAccommodationBookingManager dataFromFile = JsonUtil
                .readJsonFile(INVALID_ACCOMMODATION_BOOKING_FILE,
                JsonSerializableAccommodationBookingManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAccommodations_throwsIllegalValueException() throws Exception {
        JsonSerializableAccommodationBookingManager dataFromFile = JsonUtil
                .readJsonFile(DUPLICATE_ACCOMMODATION_BOOKING_FILE,
                JsonSerializableAccommodationBookingManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAccommodationBookingManager
                        .MESSAGE_DUPLICATE_ACCOMMODATION_BOOKING,
                dataFromFile::toModelType);
    }

}
