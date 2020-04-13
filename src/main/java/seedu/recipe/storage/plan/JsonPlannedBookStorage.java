package seedu.recipe.storage.plan;

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
import seedu.recipe.model.ReadOnlyPlannedBook;

/**
 * A class to access PlannedBook data stored as a json file on the hard disk.
 */
public class JsonPlannedBookStorage implements PlannedBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPlannedBookStorage.class);

    private Path filePath;

    public JsonPlannedBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getPlannedBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPlannedBook> readPlannedBook() throws DataConversionException, IOException {
        return readPlannedBook(filePath);
    }

    /**
     * Similar to {@link #readPlannedBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyPlannedBook> readPlannedBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePlannedBook> jsonPlannedBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePlannedBook.class);
        if (!jsonPlannedBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPlannedBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePlannedBook(ReadOnlyPlannedBook plannedBook) throws IOException {
        savePlannedBook(plannedBook, filePath);
    }

    /**
     * Similar to {@link #savePlannedBook(ReadOnlyPlannedBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void savePlannedBook(ReadOnlyPlannedBook plannedBook, Path filePath) throws IOException {
        requireNonNull(plannedBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePlannedBook(plannedBook), filePath);
    }
}
