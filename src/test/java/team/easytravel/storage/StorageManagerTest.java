package team.easytravel.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.easytravel.commons.core.GuiSettings;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.FixedExpenseManager;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyFixedExpenseManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.storage.accommodationbooking.JsonAccommodationBookingStorage;
import team.easytravel.storage.activity.JsonActivityStorage;
import team.easytravel.storage.fixedexpense.JsonFixedExpenseStorage;
import team.easytravel.storage.packinglist.JsonPackingListStorage;
import team.easytravel.storage.transportbooking.JsonTransportBookingStorage;
import team.easytravel.storage.trip.JsonTripManagerStorage;
import team.easytravel.testutil.TypicalAccommodation;
import team.easytravel.testutil.TypicalFixedExpense;
import team.easytravel.testutil.TypicalPackingListItem;
import team.easytravel.testutil.TypicalTransportBooking;


public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonTransportBookingStorage transportBookingStorage =
                new JsonTransportBookingStorage(getTempFilePath("tb"));
        JsonAccommodationBookingStorage accommodationBookingStorage =
                new JsonAccommodationBookingStorage(getTempFilePath("acc"));
        JsonFixedExpenseStorage fixedExpenseStorage = new JsonFixedExpenseStorage(getTempFilePath("fe"));
        JsonPackingListStorage packingListStorage = new JsonPackingListStorage(getTempFilePath("pl"));
        JsonTripManagerStorage tripManagerStorage = new JsonTripManagerStorage(getTempFilePath("trip"));
        JsonActivityStorage activityStorage = new JsonActivityStorage(getTempFilePath("act"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));


        storageManager = new StorageManager(transportBookingStorage,
                fixedExpenseStorage,
                activityStorage,
                accommodationBookingStorage,
                packingListStorage,
                tripManagerStorage,
                userPrefsStorage);


    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }


    @Test
    public void packingListManagerReadSave() throws Exception {
        PackingListManager original = TypicalPackingListItem.getTypicalPackingListManager();
        storageManager.savePackingList(original);
        ReadOnlyPackingListManager retrievedPackingList = storageManager.readPackingList().get();
        assertEquals(original, new PackingListManager(retrievedPackingList));
    }

    @Test
    public void fixedExpenseManagerReadSave() throws Exception {
        FixedExpenseManager original = TypicalFixedExpense.getTypicalFixedExpenseManager();
        storageManager.saveFixedExpenses(original);
        ReadOnlyFixedExpenseManager retrievedFixedExpenseList = storageManager.readFixedExpenses().get();
        assertEquals(original, new FixedExpenseManager(retrievedFixedExpenseList));
    }

    @Test
    public void accommodationBookingReadSave() throws Exception {
        AccommodationBookingManager original = TypicalAccommodation.getTypicalAccommodationManager();
        storageManager.saveAccommodationBookings(original);
        ReadOnlyAccommodationBookingManager retrievedPackingList = storageManager.readAccommodationBookings().get();
        assertEquals(original, new AccommodationBookingManager(retrievedPackingList));
    }

    @Test
    public void transportationBookingManagerReadSave() throws Exception {
        TransportBookingManager original = TypicalTransportBooking.getTypicalTransportBookingManager();
        storageManager.saveTransportBookings(original);
        ReadOnlyTransportBookingManager retrievedTransportList = storageManager.readTransportBookings().get();
        assertEquals(original, new TransportBookingManager(retrievedTransportList));
    }

    @Test
    public void getPackingListManagerFilePath() {
        assertNotNull(storageManager.getPackingListStorageFilePath());
    }

    @Test
    public void getFixedExpenseManagerFilePath() {
        assertNotNull(storageManager.getFixedExpenseStorageFilePath());
    }

    @Test
    public void getAccommodationManagerFilePath() {
        assertNotNull(storageManager.getAccommodationBookingStorageFilePath());
    }

    @Test
    public void getTransportManagerFilePath() {
        assertNotNull(storageManager.getTransportBookingStorageFilePath());
    }

    @Test
    public void getTripManagerFilePath() {
        assertNotNull(storageManager.getTripStorageFilePath());
    }

}
