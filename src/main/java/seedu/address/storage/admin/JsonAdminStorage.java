package seedu.address.storage.admin;

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
import seedu.address.model.admin.ReadOnlyAdmin;

/**
 * A class to access Admin data stored as a json file on the hard disk.
 */
public class JsonAdminStorage implements AdminStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAdminStorage.class);

    private Path filePath;

    public JsonAdminStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAdminFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAdmin> readAdmin() throws DataConversionException {
        return readAdmin(filePath);
    }

    /**
     * Similar to {@link #readAdmin()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAdmin> readAdmin(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAdmin> jsonAdmin = JsonUtil.readJsonFile(
                filePath, JsonSerializableAdmin.class);
        if (!jsonAdmin.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAdmin.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAdmin(ReadOnlyAdmin admin) throws IOException {
        saveAdmin(admin, filePath);
    }

    /**
     * Similar to {@link #saveAdmin(ReadOnlyAdmin)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAdmin(ReadOnlyAdmin admin, Path filePath) throws IOException {
        requireNonNull(admin);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAdmin(admin), filePath);
    }
}
