package seedu.eylah.diettracker.storage;

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
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;

/**
 * A class to access FoodBook data stored as a json file on the hard disk.
 */
public class JsonFoodBookStorage implements FoodBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFoodBookStorage.class);

    private Path filePath;

    public JsonFoodBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFoodBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException {
        return readFoodBook(filePath);
    }

    /**
     * Similar to {@link #readFoodBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFoodBook> readFoodBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFoodBook> jsonFoodBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableFoodBook.class);
        if (!jsonFoodBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFoodBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException {
        saveFoodBook(foodBook, filePath);
    }

    /**
     * Similar to {@link #saveFoodBook(ReadOnlyFoodBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFoodBook(ReadOnlyFoodBook foodBook, Path filePath) throws IOException {
        requireNonNull(foodBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFoodBook(foodBook), filePath);
    }

}
