package team.easytravel.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.easytravel.commons.core.LogsCenter;
import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyFixedExpenseManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyUserPrefs;
import team.easytravel.model.listmanagers.UserPrefs;
import team.easytravel.model.trip.TripManager;
import team.easytravel.storage.accommodationbooking.AccommodationBookingStorage;
import team.easytravel.storage.activity.ActivityStorage;
import team.easytravel.storage.fixedexpense.FixedExpenseStorage;
import team.easytravel.storage.packinglist.PackingListStorage;
import team.easytravel.storage.transportbooking.TransportBookingStorage;
import team.easytravel.storage.trip.TripManagerStorage;

/**
 * Manages storage of Easy Travel's data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TransportBookingStorage transportBookingStorage;
    private FixedExpenseStorage fixedExpenseStorage;
    private AccommodationBookingStorage accommodationBookingStorage;
    private UserPrefsStorage userPrefsStorage;
    private ActivityStorage activityStorage;
    private PackingListStorage packingListStorage;
    private TripManagerStorage tripManagerStorage;

    /**
     * Instantiates a new Storage manager.
     *
     * @param transportBookingStorage     the transport booking storage
     * @param fixedExpenseStorage         the fixed expense storage
     * @param activityStorage      the activity manager storage
     * @param accommodationBookingStorage the accommodation booking storage
     * @param packingListStorage          the packing list storage
     * @param userPrefsStorage            the user prefs storage
     */
    public StorageManager(TransportBookingStorage transportBookingStorage,
                          FixedExpenseStorage fixedExpenseStorage,
                          ActivityStorage activityStorage,
                          AccommodationBookingStorage accommodationBookingStorage,
                          PackingListStorage packingListStorage,
                          TripManagerStorage tripManagerStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.transportBookingStorage = transportBookingStorage;
        this.fixedExpenseStorage = fixedExpenseStorage;
        this.activityStorage = activityStorage;
        this.accommodationBookingStorage = accommodationBookingStorage;
        this.packingListStorage = packingListStorage;
        this.tripManagerStorage = tripManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ TransportBookingManager methods ==============================

    @Override
    public Path getTransportBookingStorageFilePath() {
        return transportBookingStorage.getTransportBookingStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyTransportBookingManager> readTransportBookings() throws DataConversionException,
            IOException {
        return readTransportBookings(transportBookingStorage.getTransportBookingStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyTransportBookingManager> readTransportBookings(
            Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return transportBookingStorage.readTransportBookings(filePath);
    }

    @Override
    public void saveTransportBookings(ReadOnlyTransportBookingManager transportBookingManager) throws IOException {
        saveTransportBookings(transportBookingManager, transportBookingStorage.getTransportBookingStorageFilePath());
    }

    @Override
    public void saveTransportBookings(
            ReadOnlyTransportBookingManager transportBookingManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        transportBookingStorage.saveTransportBookings(transportBookingManager, filePath);
    }

    // ================ FixedExpenseManager methods ==============================

    @Override
    public Path getFixedExpenseStorageFilePath() {
        return fixedExpenseStorage.getFixedExpenseStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyFixedExpenseManager> readFixedExpenses() throws DataConversionException,
            IOException {
        return readFixedExpenses(fixedExpenseStorage.getFixedExpenseStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyFixedExpenseManager> readFixedExpenses(
            Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return fixedExpenseStorage.readFixedExpenses(filePath);
    }

    @Override
    public void saveFixedExpenses(ReadOnlyFixedExpenseManager fixedExpenseManager) throws IOException {
        saveFixedExpenses(fixedExpenseManager, fixedExpenseStorage.getFixedExpenseStorageFilePath());
    }

    @Override
    public void saveFixedExpenses(
            ReadOnlyFixedExpenseManager fixedExpenseManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        fixedExpenseStorage.saveFixedExpenses(fixedExpenseManager, filePath);
    }

    // ================ ActivityManager methods ==============================

    @Override
    public Path getActivityManagerStorageFilePath() {
        return activityStorage.getActivityManagerStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyActivityManager> readActivityManager() throws DataConversionException,
            IOException {
        return readActivityManager(activityStorage.getActivityManagerStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyActivityManager> readActivityManager(
            Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return activityStorage.readActivityManager(filePath);
    }

    @Override
    public void saveActivityManager(ReadOnlyActivityManager activityManager) throws IOException {
        saveActivityManager(activityManager, activityStorage.getActivityManagerStorageFilePath());
    }

    @Override
    public void saveActivityManager(
            ReadOnlyActivityManager activityManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        activityStorage.saveActivityManager(activityManager, filePath);
    }

    // ================ AccommodationBookingManager methods ==============================

    @Override
    public Path getAccommodationBookingStorageFilePath() {
        return accommodationBookingStorage.getAccommodationBookingStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookings() throws DataConversionException,
            IOException {
        return readAccommodationBookings(accommodationBookingStorage.getAccommodationBookingStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookings(
            Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return accommodationBookingStorage.readAccommodationBookings(filePath);
    }

    @Override
    public void saveAccommodationBookings(ReadOnlyAccommodationBookingManager accommodationBookingManager)
            throws IOException {
        saveAccommodationBookings(accommodationBookingManager,
                accommodationBookingStorage.getAccommodationBookingStorageFilePath());
    }

    @Override
    public void saveAccommodationBookings(
            ReadOnlyAccommodationBookingManager accommodationBookingManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        accommodationBookingStorage.saveAccommodationBookings(accommodationBookingManager, filePath);
    }

    //================ Packing List methods ==============================
    @Override
    public Path getPackingListStorageFilePath() {
        return packingListStorage.getPackingListStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyPackingListManager> readPackingList() throws DataConversionException, IOException {
        return readPackingList(packingListStorage.getPackingListStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyPackingListManager> readPackingList(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return packingListStorage.readPackingList(filePath);
    }

    @Override
    public void savePackingList(ReadOnlyPackingListManager packingList) throws IOException {
        savePackingList(packingList, packingListStorage.getPackingListStorageFilePath());
    }

    @Override
    public void savePackingList(ReadOnlyPackingListManager packingList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        packingListStorage.savePackingList(packingList, filePath);
    }

    //================ Packing List methods ==============================

    @Override
    public Path getTripStorageFilePath() {
        return tripManagerStorage.getTripStorageFilePath();
    }

    @Override
    public Optional<TripManager> readTripManager() throws DataConversionException, IOException {
        return readTripManager(tripManagerStorage.getTripStorageFilePath());
    }

    @Override
    public Optional<TripManager> readTripManager(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tripManagerStorage.readTripManager(filePath);
    }

    @Override
    public void saveTripManager(TripManager tripManager) throws IOException {
        saveTripManager(tripManager, tripManagerStorage.getTripStorageFilePath());
    }

    @Override
    public void saveTripManager(TripManager tripManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tripManagerStorage.saveTripManager(tripManager, filePath);
    }
}
