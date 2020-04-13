package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProfileList;

//@@author chanckben
/**
 * Represents a storage for {@link seedu.address.model.ProfileList}.
 */
public interface ProfileListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProfileListFilePath();

    /**
     * Returns ProfileList data as a {@link ProfileList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ProfileList> readProfileList() throws DataConversionException, IOException;

    /**
     * @see #getProfileListFilePath()
     */
    Optional<ProfileList> readProfileList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ProfileList} to the storage.
     * @param profileList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProfileList(ProfileList profileList) throws IOException;

    /**
     * @see #saveProfileList(ProfileList)
     */
    void saveProfileList(ProfileList profileList, Path filePath) throws IOException;

}
