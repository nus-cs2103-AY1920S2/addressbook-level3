package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FitBiz;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link FitBiz}.
 */
public interface FitBizStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFitBizFilePath();

    /**
     * Returns FitBiz data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readFitBiz() throws DataConversionException, IOException;

    /**
     * @see #getFitBizFilePath()
     */
    Optional<ReadOnlyAddressBook> readFitBiz(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param fitBiz cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFitBiz(ReadOnlyAddressBook fitBiz) throws IOException;

    /**
     * @see #saveFitBiz(ReadOnlyAddressBook)
     */
    void saveFitBiz(ReadOnlyAddressBook fitBiz, Path filePath) throws IOException;

}
