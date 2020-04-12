package seedu.address.storage.storageProgress;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelProgress.Progress;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.modelProgress.ProgressAddressBook}.
 */
public interface ProgressAddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProgressAddressBookFilePath();

    /**
     * Returns ProgressAddressBook data as a {@link ReadOnlyAddressBookGeneric<Progress>}. Returns {@code
     * Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBookGeneric<Progress>> readProgressAddressBook()
            throws DataConversionException, IOException;

    /**
     * @see #getProgressAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBookGeneric<Progress>> readProgressAddressBook(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBookGeneric<Progress>} to the storage.
     *
     * @param ProgressAddressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> ProgressAddressBook) throws IOException;

    /**
     * @see #saveProgressAddressBook(ReadOnlyAddressBookGeneric<Progress>)
     */
    void saveProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> ProgressAddressBook, Path filePath)
            throws IOException;

}
