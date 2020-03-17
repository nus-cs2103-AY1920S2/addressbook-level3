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
import seedu.address.model.readOnlyExerciseList;

/**
 * A class to access ExerciseList data stored as a json file on the hard disk.
 */
public class JsonExerciseListStorage implements ExerciseListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExerciseListStorage.class);

    private Path filePath;

    public JsonExerciseListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExerciseListFilePath() {
        return filePath;
    }

    @Override
    public Optional<readOnlyExerciseList> readExerciseList() throws DataConversionException {
        return readExerciseList(filePath);
    }

    /**
     * Similar to {@link #readExerciseList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<readOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExerciseList> jsonExerciseList = JsonUtil.readJsonFile(
                filePath, JsonSerializableExerciseList.class);
        if (!jsonExerciseList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExerciseList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExerciseList(readOnlyExerciseList exerciseList) throws IOException {
        saveExerciseList(exerciseList, filePath);
    }

    /**
     * Similar to {@link #saveExerciseList(readOnlyExerciseList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExerciseList(readOnlyExerciseList exerciseList, Path filePath) throws IOException {
        requireNonNull(exerciseList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExerciseList(exerciseList), filePath);
    }

}
