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
import seedu.address.model.event.tutorial.ReadOnlyTutorial;

/**
 * A class to access Tutorials data stored as a JSON file on the hard disk.
 */
public class JsonTutorialStorage implements TutorialStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTutorialStorage.class);

    private Path filePath;

    public JsonTutorialStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTutorialsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTutorial> readTutorials() throws DataConversionException {
        return readTutorials(filePath);
    }

    /**
     * Similar to {@link #readTutorials()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTutorial> readTutorials(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTutorials> jsonTutorials = JsonUtil.readJsonFile(
                filePath, JsonSerializableTutorials.class);
        if (!jsonTutorials.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTutorials.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTutorials(ReadOnlyTutorial tutorials) throws IOException {
        saveTutorials(tutorials, filePath);
    }

    /**
     * Similar to {@link #saveTutorials(ReadOnlyTutorial)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTutorials(ReadOnlyTutorial tutorials, Path filePath) throws IOException {
        requireNonNull(tutorials);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTutorials(tutorials), filePath);
    }
}
