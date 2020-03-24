package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyReturnOrderBook;

/**
 * A class to access ReturnOrderBook data stored as a json file on the hard disk.
 */
public class JsonReturnOrderBookStorage implements ReturnOrderBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonReturnOrderBookStorage.class);

    private Path filePath;

    public JsonReturnOrderBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getReturnOrderBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReturnOrderBook> readReturnOrderBook() throws DataConversionException {
        return readReturnOrderBook(filePath);
    }

    /**
     * Similar to {@link #readReturnOrderBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyReturnOrderBook> readReturnOrderBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableReturnOrderBook> jsonReturnOrderBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableReturnOrderBook.class);
        if (jsonReturnOrderBook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonReturnOrderBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook) throws IOException {
        saveReturnOrderBook(returnOrderBook, filePath);
    }

    /**
     * Similar to {@link #saveReturnOrderBook(ReadOnlyReturnOrderBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook, Path filePath) throws IOException {
        requireNonNull(returnOrderBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableReturnOrderBook(returnOrderBook), filePath);
    }

}
