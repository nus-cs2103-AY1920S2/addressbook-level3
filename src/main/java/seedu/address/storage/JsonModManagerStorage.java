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
import seedu.address.model.ReadOnlyModManager;

/**
 * A class to access ModManager data stored as a json file on the hard disk.
 */
public class JsonModManagerStorage implements ModManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModManagerStorage.class);

    private Path filePath;

    public JsonModManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyModManager> readModManager() throws DataConversionException {
        return readModManager(filePath);
    }

    /**
     * Similar to {@link #readModManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyModManager> readModManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModManager> jsonModManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableModManager.class);
        if (!jsonModManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonModManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveModManager(ReadOnlyModManager modManager) throws IOException {
        saveModManager(modManager, filePath);
    }

    /**
     * Similar to {@link #saveModManager(ReadOnlyModManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveModManager(ReadOnlyModManager modManager, Path filePath) throws IOException {
        requireNonNull(modManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModManager(modManager), filePath);
    }

}
