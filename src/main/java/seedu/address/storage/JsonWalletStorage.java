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
import seedu.address.model.ReadOnlyWallet;

/**
 * A class to access Wallet data stored as a json file on the hard disk.
 */
public class JsonWalletStorage implements WalletStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWalletStorage.class);

    private Path filePath;

    public JsonWalletStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWalletFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWallet> readWallet() throws DataConversionException {
        return readWallet(filePath);
    }

    /**
     * Similar to {@link #readWallet()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWallet> readWallet(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWallet> jsonWallet = JsonUtil.readJsonFile(
                filePath, JsonSerializableWallet.class);
        if (!jsonWallet.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWallet.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveWallet(ReadOnlyWallet wallet) throws IOException {
        saveWallet(wallet, filePath);
    }

    /**
     * Similar to {@link #saveWallet(ReadOnlyWallet)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWallet(ReadOnlyWallet wallet, Path filePath) throws IOException {
        requireNonNull(wallet);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWallet(wallet), filePath);
    }

}
