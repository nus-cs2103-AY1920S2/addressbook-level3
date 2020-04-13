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
import seedu.address.model.student.ReadOnlyStudent;

/**
 * A class to access StudentTAble data stored as a json file on the hard disk.
 */
public class JsonStudentStorage implements StudentStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudentStorage.class);

    private Path filePath;

    public JsonStudentStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStudentTAbleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStudent> readStudentTAble() throws DataConversionException {
        return readStudentTAble(filePath);
    }

    /**
     * Similar to {@link #readStudentTAble()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStudent> readStudentTAble(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableStudentStorage> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudentStorage.class);
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
    public void saveStudentTAble(ReadOnlyStudent studentTAble) throws IOException {
        saveStudentTAble(studentTAble, filePath);
    }

    /**
     * Similar to {@link #saveStudentTAble(ReadOnlyStudent)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStudentTAble(ReadOnlyStudent studentTAble, Path filePath) throws IOException {
        requireNonNull(studentTAble);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentStorage(studentTAble), filePath);
    }

}
