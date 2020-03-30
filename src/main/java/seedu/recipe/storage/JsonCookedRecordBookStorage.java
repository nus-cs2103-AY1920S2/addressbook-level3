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

    public Path getCookedRecordFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCookedRecordBook> readCookedRecord() throws DataConversionException {
        return readCookedRecord(filePath);
    }

    /**
     * Similar to {@link #readCookedRecord()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCookedRecordBook> readCookedRecord(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCookedRecordBook> jsonCookedRecord = JsonUtil.readJsonFile(
                filePath, JsonSerializableCookedRecordBook.class);
        if (!jsonCookedRecord.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCookedRecord.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCookedRecord(ReadOnlyCookedRecordBook cookedRecord) throws IOException {
        saveCookedRecord(cookedRecord, filePath);
    }

    /**
     * Similar to {@link #saveCookedRecord(ReadOnlyCookedRecordBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCookedRecord(ReadOnlyCookedRecordBook cookedRecord, Path filePath) throws IOException {
        requireNonNull(cookedRecord);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        //JsonUtil.saveJsonFile(new JsonSerializableCookedRecordBook(cookedRecord), filePath);
    }

}