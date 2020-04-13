package seedu.zerotoone.storage.exercise;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.commons.util.FileUtil;
import seedu.zerotoone.commons.util.JsonUtil;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.storage.exercise.util.JacksonExerciseList;

/**
 * A class to access ExerciseList data stored as a json file on the hard disk.
 */
public class ExerciseListStorageManager implements ExerciseListStorage {

    private static final Logger logger = LogsCenter.getLogger(ExerciseListStorageManager.class);

    private Path filePath;

    public ExerciseListStorageManager(Path filePath) {
        logger.fine("Initializing manager with " + filePath);
        this.filePath = filePath;
    }

    public Path getExerciseListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExerciseList> readExerciseList() throws DataConversionException {
        return readExerciseList(filePath);
    }

    /**
     * Similar to {@link #readExerciseList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        logger.fine("Reading Exercise List from " + filePath);

        Optional<JacksonExerciseList> jsonExerciseList = JsonUtil.readJsonFile(
                filePath, JacksonExerciseList.class);
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
    public void saveExerciseList(ReadOnlyExerciseList exerciseList) throws IOException {
        saveExerciseList(exerciseList, filePath);
    }

    /**
     * Similar to {@link #saveExerciseList(ReadOnlyExerciseList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExerciseList(ReadOnlyExerciseList exerciseList, Path filePath) throws IOException {
        requireNonNull(exerciseList);
        requireNonNull(filePath);

        logger.fine("Saving Exercise List " + exerciseList + " in " + filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JacksonExerciseList(exerciseList), filePath);
    }

}
