package seedu.recipe.storage.achievement;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyQuoteBook;
/**
 * Represents a storage for {@link seedu.recipe.model.achievement.QuoteBook}.
 */
public interface QuoteBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getQuoteBookFilePath();

    /**
     * Returns QuoteBook data as a {@link ReadOnlyQuoteBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyQuoteBook> readQuoteBook() throws DataConversionException, IOException;

    /**
     * @see #getQuoteBookFilePath()
     */
    Optional<ReadOnlyQuoteBook> readQuoteBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyQuoteBook} to the storage.
     * @param quoteBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveQuoteBook(ReadOnlyQuoteBook quoteBook) throws IOException;

    /**
     * @see #saveQuoteBook(ReadOnlyQuoteBook)
     */
    void saveQuoteBook(ReadOnlyQuoteBook quoteBook, Path filePath) throws IOException;
}
