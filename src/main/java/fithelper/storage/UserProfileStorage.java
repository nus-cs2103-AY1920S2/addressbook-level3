package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyUserProfile;

/**
 * Represents a storage for {@link fithelper.model.UserProfile}.
 */
public interface UserProfileStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUserProfilePath();

    /**
     * Returns UserProfile data as a {@link ReadOnlyUserProfile}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException;

    /**
     * @see #getUserProfilePath()
     */
    Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUserProfile} to the storage.
     * @param userProfile cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserProfile(ReadOnlyUserProfile userProfile) throws IOException;

    /**
     * @see #saveUserProfile(ReadOnlyUserProfile)
     */
    void saveUserProfile(ReadOnlyUserProfile userProfile, Path filePath) throws IOException;

}
