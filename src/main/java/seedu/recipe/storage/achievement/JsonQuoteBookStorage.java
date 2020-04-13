package seedu.recipe.storage.achievement;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.commons.util.FileUtil;
import seedu.recipe.commons.util.JsonUtil;
import seedu.recipe.model.ReadOnlyQuoteBook;

/**
 * A class to access QuoteBook data stored as a json file on the hard disk.
 */
public class JsonQuoteBookStorage implements QuoteBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonQuoteBookStorage.class);

    private Path filePath;

    public JsonQuoteBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getQuoteBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyQuoteBook> readQuoteBook() throws DataConversionException {
        return readQuoteBook(filePath);
    }

    /**
     * Similar to {@link #readQuoteBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyQuoteBook> readQuoteBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuoteBook> jsonQuoteBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableQuoteBook.class);
        if (!jsonQuoteBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuoteBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveQuoteBook(ReadOnlyQuoteBook quoteBook) throws IOException {
        saveQuoteBook(quoteBook, filePath);
    }

    /**
     * Similar to {@link #saveQuoteBook(ReadOnlyQuoteBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveQuoteBook(ReadOnlyQuoteBook quoteBook, Path filePath) throws IOException {
        requireNonNull(quoteBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuoteBook(quoteBook), filePath);
    }

}
