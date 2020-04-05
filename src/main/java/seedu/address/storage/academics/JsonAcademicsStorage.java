package seedu.address.storage.academics;

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
import seedu.address.model.academics.ReadOnlyAcademics;

/**
 * A class to access Academics data stored as a json file on the hard disk.
 */
public class JsonAcademicsStorage implements AcademicsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAcademicsStorage.class);

    private Path filePath;

    public JsonAcademicsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAcademicsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAcademics> readAcademics() throws DataConversionException {
        return readAcademics(filePath);
    }

    /**
     * Similar to {@link #readAcademics()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAcademics> readAcademics(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAcademics> jsonAcademics = JsonUtil.readJsonFile(
                filePath, JsonSerializableAcademics.class);
        if (!jsonAcademics.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAcademics.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAcademics(ReadOnlyAcademics academics) throws IOException {
        saveAcademics(academics, filePath);
    }

    /**
     * Similar to {@link #saveAcademics(ReadOnlyAcademics)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAcademics(ReadOnlyAcademics academics, Path filePath) throws IOException {
        requireNonNull(academics);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAcademics(academics), filePath);
    }

}
