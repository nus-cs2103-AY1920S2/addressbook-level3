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
import seedu.address.model.ReadOnlyInventorySystem;

/**
 * A class to access InventorySystem data stored as a json file on the hard disk.
 */
public class JsonInventorySystemStorage implements InventorySystemStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInventorySystemStorage.class);

    private Path filePath;

    public JsonInventorySystemStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventorySystemFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInventorySystem> readInventorySystem() throws DataConversionException {
        return readInventorySystem(filePath);
    }

    /**
     * Similar to {@link #readInventorySystem()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInventorySystem> readInventorySystem(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableInventorySystem> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableInventorySystem.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInventorySystem(ReadOnlyInventorySystem inventorySystem) throws IOException {
        saveInventorySystem(inventorySystem, filePath);
    }

    /**
     * Similar to {@link #saveInventorySystem(ReadOnlyInventorySystem)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInventorySystem(ReadOnlyInventorySystem inventorySystem, Path filePath) throws IOException {
        requireNonNull(inventorySystem);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInventorySystem(inventorySystem), filePath);
    }

}
