package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProfileList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

//@@author chanckben

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProfileListStorage profileListStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(ProfileListStorage profileListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.profileListStorage = profileListStorage;
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

    // ================ ProfileList methods ==============================

    @Override
    public Path getProfileListFilePath() {
        return profileListStorage.getProfileListFilePath();
    }

    @Override
    public Optional<ProfileList> readProfileList() throws DataConversionException, IOException {
        return readProfileList(profileListStorage.getProfileListFilePath());
    }

    @Override
    public Optional<ProfileList> readProfileList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return profileListStorage.readProfileList(filePath);
    }

    @Override
    public void saveProfileList(ProfileList profileList) throws IOException {
        saveProfileList(profileList, profileListStorage.getProfileListFilePath());
    }

    @Override
    public void saveProfileList(ProfileList profileList, Path filePath) throws IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        profileListStorage.saveProfileList(profileList, filePath);
    }

}
