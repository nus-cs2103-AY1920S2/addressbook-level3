package seedu.address.storage.admin;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.admin.ReadOnlyAdmin;

/**
 * Represents a storage for {@link seedu.address.model.admin.Admin}.
 */
public interface AdminStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAdminFilePath();

    /**
     * Returns Admin data as a {@link ReadOnlyAdmin}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAdmin> readAdmin() throws DataConversionException, IOException;

    /**
     * @see #getAdminFilePath() ()
     */
    Optional<ReadOnlyAdmin> readAdmin(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAdmin} to the storage.
     * @param admin cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAdmin(ReadOnlyAdmin admin) throws IOException;

    /**
     * @see #saveAdmin(ReadOnlyAdmin)
     */
    void saveAdmin(ReadOnlyAdmin admin, Path filePath) throws IOException;
}
