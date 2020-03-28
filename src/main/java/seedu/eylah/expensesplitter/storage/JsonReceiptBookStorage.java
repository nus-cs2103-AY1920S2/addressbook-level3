package seedu.eylah.expensesplitter.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.JsonUtil;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;

public class JsonReceiptBookStorage implements ReceiptStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonReceiptBookStorage);

    private Path filePath;

    public JsonReceiptBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getReceiptBook() throws DataConversionException {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReceiptBook> readReceiptBook() throws DataConversionException {
        return readReceiptBook(filePath);
    }

    /**
     * Similar to {@link #readPersonAmountBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyReceiptBook> readReceiptBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePersonAmountBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePersonAmountBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
}
