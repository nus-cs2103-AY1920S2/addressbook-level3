package seedu.eylah.expensesplitter.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.FileUtil;
import seedu.eylah.commons.util.JsonUtil;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;

/**
 * A class to access ReceiptBook data stored as a json file on the hard disk.
 */
public class JsonReceiptBookStorage implements ReceiptStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonReceiptBookStorage.class);

    private Path filePath;

    public JsonReceiptBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getReceiptBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReceiptBook> readReceiptBook() throws DataConversionException {
        return readReceiptBook(filePath);
    }

    /**
     * Similar to {@link #readReceiptBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyReceiptBook> readReceiptBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableReceiptBook> jsonReceiptBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableReceiptBook.class);
        if (!jsonReceiptBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonReceiptBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveReceiptBook(ReadOnlyReceiptBook receiptBook) throws IOException {
        saveReceiptBook(receiptBook, filePath);
    }

    /**
     * Saves a receipt book.
     *
     * @param receiptBook ReadOnlyReceiptBook
     * @param filePath location of data cannot be null.
     * @throws IOException
     */
    public void saveReceiptBook(ReadOnlyReceiptBook receiptBook, Path filePath) throws IOException {
        requireNonNull(receiptBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableReceiptBook(receiptBook), filePath);
    }
}
