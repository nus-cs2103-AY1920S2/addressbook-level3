package team.easytravel.storage.transportbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.commons.util.JsonUtil;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.testutil.TypicalTransportBooking;

class JsonSerializableTransportBookingManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableTransportBookingManagerTest");
    private static final Path TYPICAL_TRANSPORT_FILE = TEST_DATA_FOLDER.resolve("typicalTransportBookingManager.json");
    private static final Path INVALID_TRANSPORT_FILE = TEST_DATA_FOLDER.resolve("invalidTransportBookingManager.json");
    private static final Path DUPLICATE_TRANSPORT_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateTransportBookingManager.json");


    @Test
    public void toModelType_typicalTransportBookingFile_success() throws Exception {
        JsonSerializableTransportBookingManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSPORT_FILE,
               JsonSerializableTransportBookingManager.class).get();
        TransportBookingManager transportBookingManagerFromFile = dataFromFile.toModelType();
        TransportBookingManager typicalTransportBookingManager = TypicalTransportBooking
                .getTypicalTransportBookingManager();
        assertEquals(transportBookingManagerFromFile, typicalTransportBookingManager);
    }

    @Test
    public void toModelType_invalidTransportBookingFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTransportBookingManager dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSPORT_FILE,
                JsonSerializableTransportBookingManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTransportBookings_throwsIllegalValueException() throws Exception {
        JsonSerializableTransportBookingManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSPORT_FILE,
               JsonSerializableTransportBookingManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTransportBookingManager
                        .MESSAGE_DUPLICATE_TRANSPORT_BOOKING,
                dataFromFile::toModelType);
    }

}
