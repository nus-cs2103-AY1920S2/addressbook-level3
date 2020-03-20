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
import seedu.expensela.model.ReadOnlyExpenseLa;

/**
 * A class to access ExpenseLa data stored as a json file on the hard disk.
 */
public class JsonExpenseLaStorage implements ExpenseLaStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExpenseLaStorage.class);

    private Path filePath;

    public JsonExpenseLaStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpenseLaFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpenseLa> readExpenseLa() throws DataConversionException {
        return readExpenseLa(filePath);
    }

    /**
     * Similar to {@link #readExpenseLa()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpenseLa> readExpenseLa(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExpenseLa> jsonExpenseLa = JsonUtil.readJsonFile(
                filePath, JsonSerializableExpenseLa.class);
        if (!jsonExpenseLa.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExpenseLa.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpenseLa(ReadOnlyExpenseLa expenseLa) throws IOException {
        saveExpenseLa(expenseLa, filePath);
    }

    /**
     * Similar to {@link #saveExpenseLa(ReadOnlyExpenseLa)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExpenseLa(ReadOnlyExpenseLa expenseLa, Path filePath) throws IOException {
        requireNonNull(expenseLa);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExpenseLa(expenseLa), filePath);
    }

}
