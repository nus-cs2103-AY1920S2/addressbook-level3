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
import seedu.address.model.ReadOnlyCookbook;

/**
 * A class to access Cookbook data stored as a json file on the hard disk.
 */
public class JsonCookbookStorage implements CookbookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCookbookStorage.class);

    private Path filePath;

    public JsonCookbookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCookbookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCookbook> readCookbook() throws DataConversionException {
        return readCookbook(filePath);
    }

    /**
     * Similar to {@link #readCookbook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCookbook> readCookbook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCookbook> jsonCookbook = JsonUtil.readJsonFile(
                filePath, JsonSerializableCookbook.class);

        if (jsonCookbook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCookbook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCookbook(ReadOnlyCookbook cookbook) throws IOException {
        saveCookbook(cookbook, filePath);
    }

    /**
     * Similar to {@link #saveCookbook(ReadOnlyCookbook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCookbook(ReadOnlyCookbook cookbook, Path filePath) throws IOException {
        requireNonNull(cookbook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCookbook(cookbook), filePath);
    }

}
