package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModuleBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * API for module book storage
 */
public interface ModuleBookStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getModuleBookFilePath();

    /**
     * Returns ModuleBook data as a {@link ModuleBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ModuleBook> readModuleBook() throws DataConversionException, IOException;

    /**
     * @see #getModuleBookFilePath()
     */
    Optional<ModuleBook> readModuleBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param moduleBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveModuleBook(ModuleBook moduleBook) throws IOException;

    /**
     * @see #saveModuleBook(ModuleBook)
     */
    void saveModuleBook(ModuleBook moduleBook, Path filePath) throws IOException;

}
