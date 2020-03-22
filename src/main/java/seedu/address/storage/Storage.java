package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends OrderBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //==================== Order Book methods ========================================

    @Override
    Path getOrderBookFilePath();

    @Override
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    @Override
    void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException;

    //==================== Return Order Book methods ========================================

    Path getReturnOrderBookFilePath();

    Optional<ReadOnlyOrderBook> readReturnOrderBook() throws DataConversionException, IOException;

    Optional<ReadOnlyOrderBook> readReturnOrderBook(Path filePath) throws DataConversionException, IOException;

    void saveReturnOrderBook(ReadOnlyOrderBook returnOrderBook) throws IOException;

    void saveReturnOrderBook(ReadOnlyOrderBook returnOrderBook, Path filePath) throws IOException;
}
