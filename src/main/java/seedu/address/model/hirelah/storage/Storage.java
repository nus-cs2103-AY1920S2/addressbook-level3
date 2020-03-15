package seedu.address.model.hirelah.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;

/**
 * API of the Storage component
 */
public interface Storage {
    /**
     * Returns the file path of the data file.
     */
    Path getFilePath();

    /**
     * Returns Model data as a {@link Model}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Model> readModel() throws DataConversionException, IOException;

    /**
     * Saves the given {@link Model} to the storage.
     * @param model cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSession(Model model) throws IOException;
}
