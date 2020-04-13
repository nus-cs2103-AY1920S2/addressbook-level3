package com.flashspeed.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.flashspeed.commons.exceptions.DataConversionException;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.ReadOnlyUserPrefs;
import com.flashspeed.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends LibraryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getLibraryFilePath();

    @Override
    Optional<ReadOnlyLibrary> readLibrary() throws DataConversionException, IOException;

    @Override
    void saveLibrary(ReadOnlyLibrary library) throws IOException;
}
