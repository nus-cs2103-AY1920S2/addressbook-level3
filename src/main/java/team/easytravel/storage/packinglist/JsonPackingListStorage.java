package team.easytravel.storage.packinglist;

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
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;

/**
 * A class to access JsonPackingListManager data stored as a json file on the hard disk.
 */
public class JsonPackingListStorage implements PackingListStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPackingListStorage.class);

    private Path filePath;

    public JsonPackingListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPackingListStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPackingListManager> readPackingList() throws DataConversionException {
        return readPackingList(filePath);
    }

    /**
     * Similar to {@link #readPackingList()} ()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyPackingListManager> readPackingList(
            Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePackingListManager> jsonPackingListManager = JsonUtil.readJsonFile(
                filePath, JsonSerializablePackingListManager.class);
        if (jsonPackingListManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPackingListManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePackingList(ReadOnlyPackingListManager packingListManager) throws IOException {
        savePackingList(packingListManager, filePath);
    }

    /**
     * Similar to {@link #savePackingList(ReadOnlyPackingListManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void savePackingList(
            ReadOnlyPackingListManager packingListManager, Path filePath) throws IOException {
        requireNonNull(packingListManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePackingListManager(packingListManager), filePath);
    }
}
