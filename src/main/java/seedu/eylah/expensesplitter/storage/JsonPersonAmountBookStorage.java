package seedu.eylah.expensesplitter.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.storage.JsonPersonAmountBookStorage;
import seedu.eylah.expensesplitter.storage.JsonSerializablePersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.FileUtil;
import seedu.eylah.commons.util.JsonUtil;

/**
 * A class to access PersonAmountBook data stored as a json file on the hard disk.
 */
public class JsonPersonAmountBookStorage implements PersonAmountStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPersonAmountBookStorage.class);

    private Path filePath;

    public JsonPersonAmountBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPersonAmountBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPersonAmountBook> readPersonAmountBook() throws DataConversionException {
        return readPersonAmountBook(filePath);
    }

    /**
     * Similar to {@link #readPersonAmountBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPersonAmountBook> readPersonAmountBook(Path filePath) throws DataConversionException {
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

    @Override
    public void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook) throws IOException {
        savePersonAmountBook(personAmountBook, filePath);
    }

    public void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook, Path filePath) throws IOException {
        requireNonNull(personAmountBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePersonAmountBook(personAmountBook), filePath);
    }






}
