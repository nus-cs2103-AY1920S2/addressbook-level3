package seedu.zerotoone.storage.workout;

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
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.storage.workout.util.JacksonWorkoutList;

/**
 * A class to access WorkoutList data stored as a json file on the hard disk.
 */
public class WorkoutListStorageManager implements WorkoutListStorage {

    private static final Logger logger = LogsCenter.getLogger(WorkoutListStorageManager.class);

    private Path filePath;

    public WorkoutListStorageManager(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWorkoutListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWorkoutList> readWorkoutList() throws DataConversionException {
        return readWorkoutList(filePath);
    }

    /**
     * Similar to {@link #readWorkoutList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWorkoutList> readWorkoutList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JacksonWorkoutList> jsonWorkoutList = JsonUtil.readJsonFile(
                filePath, JacksonWorkoutList.class);
        if (!jsonWorkoutList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWorkoutList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWorkoutList(ReadOnlyWorkoutList workoutList) throws IOException {
        saveWorkoutList(workoutList, filePath);
    }

    /**
     * Similar to {@link #saveWorkoutList(ReadOnlyWorkoutList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWorkoutList(ReadOnlyWorkoutList workoutList, Path filePath) throws IOException {
        requireNonNull(workoutList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JacksonWorkoutList(workoutList), filePath);
    }

}
