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
import seedu.address.model.mod.ReadOnlyMod;

/**
 * A class to access Mods data stored as a JSON file on the hard disk.
 */
public class JsonModStorage implements ModStorage {

    private static final Logger logger = LogsCenter.getLogger(seedu.address.storage.JsonModStorage.class);

    private Path filePath;

    public JsonModStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMod> readMods() throws DataConversionException {
        return readMods(filePath);
    }

    /**
     * Similar to {@link #readMods()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMod> readMods(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMods> jsonMods = JsonUtil.readJsonFile(
            filePath, JsonSerializableMods.class);
        if (!jsonMods.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMods.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMods(ReadOnlyMod mods) throws IOException {
        saveMods(mods, filePath);
    }

    /**
     * Similar to {@link #saveMods(ReadOnlyMod)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMods(ReadOnlyMod mods, Path filePath) throws IOException {
        requireNonNull(mods);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMods(mods), filePath);
    }
}

