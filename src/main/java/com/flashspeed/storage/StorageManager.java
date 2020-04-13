package com.flashspeed.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.flashspeed.commons.core.LogsCenter;
import com.flashspeed.commons.exceptions.DataConversionException;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.ReadOnlyUserPrefs;
import com.flashspeed.model.UserPrefs;

/**
 * Manages storage of library data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LibraryStorage libraryStorage;
    private UserPrefsStorage userPrefsStorage;
    public StorageManager(LibraryStorage libraryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.libraryStorage = libraryStorage;
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


    // ================ Library methods ==============================

    @Override
    public Path getLibraryFilePath() {
        return libraryStorage.getLibraryFilePath();
    }

    @Override
    public Optional<ReadOnlyLibrary> readLibrary() throws DataConversionException, IOException {
        return readLibrary(libraryStorage.getLibraryFilePath());
    }

    @Override
    public Optional<ReadOnlyLibrary> readLibrary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return libraryStorage.readLibrary(filePath);
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library) throws IOException {
        saveLibrary(library, libraryStorage.getLibraryFilePath());
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        libraryStorage.saveLibrary(library, filePath);
    }
}
