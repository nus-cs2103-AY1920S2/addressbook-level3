package seedu.expensela.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.model.ReadOnlyExpenseLa;

/**
 * Represents a storage for {@link seedu.expensela.model.ExpenseLa}.
 */
public interface ExpenseLaStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExpenseLaFilePath();

    /**
     * Returns ExpenseLa data as a {@link ReadOnlyExpenseLa}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpenseLa> readExpenseLa() throws DataConversionException, IOException;

    /**
     * @see #getExpenseLaFilePath()
     */
    Optional<ReadOnlyExpenseLa> readExpenseLa(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExpenseLa} to the storage.
     * @param expenseLa cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExpenseLa(ReadOnlyExpenseLa expenseLa) throws IOException;

    /**
     * @see #saveExpenseLa(ReadOnlyExpenseLa)
     */
    void saveExpenseLa(ReadOnlyExpenseLa expenseLa, Path filePath) throws IOException;

}
