package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModuleBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.calender.Task;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private ModuleBookStorage moduleBookStorage;
    private CalendarBookStorage calendarBookStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          ModuleBookStorage moduleBookStorage, CalendarBookStorage calendarBookStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.moduleBookStorage = moduleBookStorage;
        this.calendarBookStorage = calendarBookStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }
    // ================ ModuleBook methods ==============================

    @Override
    public Path getModuleBookFilePath() {
        return moduleBookStorage.getModuleBookFilePath();
    }

    @Override
    public Optional<ModuleBook> readModuleBook() throws DataConversionException, IOException {
        return readModuleBook(moduleBookStorage.getModuleBookFilePath());
    }

    @Override
    public Optional<ModuleBook> readModuleBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return moduleBookStorage.readModuleBook(filePath);
    }

    @Override
    public void saveModuleBook(ModuleBook moduleBook) throws IOException {
        saveModuleBook(moduleBook, moduleBookStorage.getModuleBookFilePath());
    }

    @Override
    public void saveModuleBook(ModuleBook moduleBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        moduleBookStorage.saveModuleBook(moduleBook, filePath);
    }


    // ================ Calendar methods ==============================
    @Override
    public Path getCalendarEntriesFilePath() {
        return calendarBookStorage.getCalendarEntriesFilePath();
    }

    @Override
    public Optional<ObservableList<Task>> readCalendar() throws DataConversionException, IOException {
        return readCalendar(calendarBookStorage.getCalendarEntriesFilePath());
    }

    @Override
    public Optional<ObservableList<Task>> readCalendar(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return calendarBookStorage.readCalendar(filePath);
    }

    @Override
    public void saveCalendar(ObservableList<Task> taskList) throws IOException {
        saveCalendar(taskList, calendarBookStorage.getCalendarEntriesFilePath());

    }

    @Override
    public void saveCalendar(ObservableList<Task> taskList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        calendarBookStorage.saveCalendar(taskList, filePath);

    }


}
