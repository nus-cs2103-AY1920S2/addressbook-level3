package seedu.expensela.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.commons.util.FileUtil;
import seedu.expensela.commons.util.JsonUtil;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.ReadOnlyGlobalData;

/**
 * A class to access GlobalData data stored as a json file on the hard disk.
 */
public class JsonGlobalDataStorage implements GlobalDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGlobalDataStorage.class);

    private Path filePath;

    public JsonGlobalDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGlobalDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<GlobalData> readGlobalData() throws DataConversionException {
        return readGlobalData(filePath);
    }

    /**
     * Similar to {@link #readGlobalData()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<GlobalData> readGlobalData(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGlobalData> jsonGlobalData = JsonUtil.readJsonFile(
                filePath, JsonSerializableGlobalData.class);
        if (!jsonGlobalData.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGlobalData.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGlobalData(ReadOnlyGlobalData globalData) throws IOException {
        saveGlobalData(globalData, filePath);
    }

    /**
     * Similar to {@link #saveGlobalData(ReadOnlyGlobalData)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGlobalData(ReadOnlyGlobalData globalData, Path filePath) throws IOException {
        requireNonNull(globalData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGlobalData(globalData), filePath);
    }

}
