package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWallet;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook and Wallet data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserDataStorage userDataStorage;
    private UserPrefsStorage userPrefsStorage;
    private WalletStorage walletStorage;

    public StorageManager(AddressBookStorage addressBookStorage, UserDataStorage userDataStorage,
            UserPrefsStorage userPrefsStorage, WalletStorage walletStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userDataStorage = userDataStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.walletStorage = walletStorage;
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

    // ================ UserData methods ==============================

    @Override
    public Path getUserDataFilePath() {
        return userDataStorage.getUserDataFilePath();
    }

    @Override
    public Optional<ReadOnlyUserData> readUserData() throws DataConversionException, IOException {
        return readUserData(userDataStorage.getUserDataFilePath());
    }

    @Override
    public Optional<ReadOnlyUserData> readUserData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userDataStorage.readUserData(filePath);
    }

    @Override
    public void saveUserData(ReadOnlyUserData userData) throws IOException {
        saveUserData(userData, userDataStorage.getUserDataFilePath());
    }

    @Override
    public void saveUserData(ReadOnlyUserData userData, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userDataStorage.saveUserData(userData, filePath);
    }

    // ================ Wallet methods ==============================

    @Override
    public Path getWalletFilePath() {
        return walletStorage.getWalletFilePath();
    }

    @Override
    public Optional<ReadOnlyWallet> readWallet() throws DataConversionException, IOException {
        return readWallet(getWalletFilePath());
    }

    @Override
    public Optional<ReadOnlyWallet> readWallet(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return walletStorage.readWallet(filePath);
    }

    @Override
    public void saveWallet(ReadOnlyWallet wallet) throws IOException {
        saveWallet(wallet, getWalletFilePath());
    }

    @Override
    public void saveWallet(ReadOnlyWallet wallet, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        walletStorage.saveWallet(wallet, filePath);
    }

}
