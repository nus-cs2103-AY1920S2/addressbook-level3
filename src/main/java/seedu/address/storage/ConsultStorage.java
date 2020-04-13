package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.event.consult.ReadOnlyConsult;

/**
 * Represents a storage for {@link seedu.address.model.event.consult.Consult}.
 */
public interface ConsultStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getConsultsFilePath();

    /**
     * Returns Consult data as a {@link ReadOnlyConsult}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyConsult> readConsults() throws DataConversionException, IOException;

    /**
     * @see #getConsultsFilePath()
     */
    Optional<ReadOnlyConsult> readConsults(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyConsult} to the storage.
     * @param consults cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveConsults(ReadOnlyConsult consults) throws IOException;

    /**
     * @see #saveConsults(ReadOnlyConsult)
     */
    void saveConsults(ReadOnlyConsult consults, Path filePath) throws IOException;
}
