package seedu.recipe.storage;

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
import seedu.recipe.model.ReadOnlyCookedRecordBook;

/**
 * A class to access RecipeBook data stored as a json file on the hard disk.
 */
public class JsonCookedRecordBookStorage implements CookedRecordBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRecipeBookStorage.class);

    private Path filePath;

    public JsonCookedRecordBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCookedRecordBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCookedRecordBook> readCookedRecordBook() throws DataConversionException {
        return readCookedRecordBook(filePath);
    }

    /**
     * Similar to {@link #readCookedRecordBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCookedRecordBook> readCookedRecordBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCookedRecordBook> jsonCookedRecordBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableCookedRecordBook.class);
        if (!jsonCookedRecordBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCookedRecordBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook) throws IOException {
        saveCookedRecordBook(cookedRecordBook, filePath);
    }

    /**
     * Similar to {@link #saveCookedRecordBook(ReadOnlyCookedRecordBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook, Path filePath) throws IOException {
        requireNonNull(cookedRecordBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCookedRecordBook(cookedRecordBook), filePath);
    }
}
