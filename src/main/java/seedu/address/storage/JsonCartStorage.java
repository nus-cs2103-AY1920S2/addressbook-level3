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
import seedu.address.model.ReadOnlyCart;

/**
 * A class to access Cart data stored as a json file on the hard disk.
 */
public class JsonCartStorage implements CartStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCartStorage.class);

    private Path filePath;

    public JsonCartStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCartFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCart> readCart() throws DataConversionException {
        return readCart(filePath);
    }

    /**
     * Similar to {@link #readCart()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCart> readCart(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCart> jsonCart = JsonUtil.readJsonFile(
                filePath, JsonSerializableCart.class);
        if (!jsonCart.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCart.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCart(ReadOnlyCart cart) throws IOException {
        saveCart(cart, filePath);
    }

    /**
     * Similar to {@link #saveCart(ReadOnlyCart)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCart(ReadOnlyCart cart, Path filePath) throws IOException {
        requireNonNull(cart);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCart(cart), filePath);
    }

}
