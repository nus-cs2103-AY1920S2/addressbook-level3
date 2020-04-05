package seedu.address.storage.event;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.ReadOnlyEvents;

/**
 * Represents a storage for {@link seedu.address.model.event.EventHistory}
 */
public interface EventStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEventHistoryFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException;

    /**
     * @see #getEventHistoryFilePath()
     */
    Optional<ReadOnlyEvents> readEvents(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param readOnlyEvents cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEvents(ReadOnlyEvents readOnlyEvents) throws IOException;

    /**
     * @see #saveEvents(ReadOnlyEvents, Path)
     */
    void saveEvents(ReadOnlyEvents readOnlyEvents, Path filePath) throws IOException;

}
