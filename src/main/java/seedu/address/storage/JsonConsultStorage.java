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
import seedu.address.model.event.consult.ReadOnlyConsult;

/**
 * A class to access Consults data stored as a JSON file on the hard disk.
 */
public class JsonConsultStorage implements ConsultStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonConsultStorage.class);

    private Path filePath;

    public JsonConsultStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getConsultsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyConsult> readConsults() throws DataConversionException {
        return readConsults(filePath);
    }

    /**
     * Similar to {@link #readConsults()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyConsult> readConsults(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableConsults> jsonConsults = JsonUtil.readJsonFile(
                filePath, JsonSerializableConsults.class);
        if (!jsonConsults.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonConsults.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveConsults(ReadOnlyConsult consults) throws IOException {
        saveConsults(consults, filePath);
    }

    /**
     * Similar to {@link #saveConsults(ReadOnlyConsult)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveConsults(ReadOnlyConsult consults, Path filePath) throws IOException {
        requireNonNull(consults);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableConsults(consults), filePath);
    }

}
