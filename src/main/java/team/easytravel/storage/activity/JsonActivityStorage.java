package team.easytravel.storage.activity;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.easytravel.commons.core.LogsCenter;
import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.commons.util.FileUtil;
import team.easytravel.commons.util.JsonUtil;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;

/**
 * A class to access ActivityManager data stored as a json file on the hard disk.
 */
public class JsonActivityStorage implements ActivityStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonActivityStorage.class);

    private Path filePath;

    public JsonActivityStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getActivityManagerStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyActivityManager> readActivityManager() throws DataConversionException {
        return readActivityManager(filePath);
    }

    /**
     * Similar to {@link #readActivityManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyActivityManager> readActivityManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableActivityManager> jsonActivityManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableActivityManager.class);
        if (jsonActivityManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonActivityManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveActivityManager(ReadOnlyActivityManager activityManager) throws IOException {
        saveActivityManager(activityManager, filePath);
    }

    /**
     * Similar to {@link #saveActivityManager(ReadOnlyActivityManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveActivityManager(ReadOnlyActivityManager activityManager, Path filePath) throws IOException {
        requireNonNull(activityManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableActivityManager(activityManager), filePath);
    }

}
