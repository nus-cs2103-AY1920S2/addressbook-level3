package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWallet;

/**
 * Represents a storage for {@link seedu.address.model.Wallet}.
 */
public interface WalletStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWalletFilePath();

    /**
     * Returns Wallet data as a {@link ReadOnlyWallet}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWallet> readWallet() throws DataConversionException, IOException;

    /**
     * @see #getWalletFilePath()
     */
    Optional<ReadOnlyWallet> readWallet(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWallet} to the storage.
     *
     * @param wallet cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWallet(ReadOnlyWallet wallet) throws IOException;

    /**
     * @see #saveWallet(ReadOnlyWallet)
     */
    void saveWallet(ReadOnlyWallet wallet, Path filePath) throws IOException;

}
