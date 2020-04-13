package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCart;

/**
 * Represents a storage for {@link seedu.address.model.Cart}.
 */
public interface CartStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCartFilePath();

    /**
     * Returns Cart data as a {@link ReadOnlyCart}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCart> readCart() throws DataConversionException, IOException;

    /**
     * @see #getCartFilePath()
     */
    Optional<ReadOnlyCart> readCart(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCart} to the storage.
     * @param cart cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCart(ReadOnlyCart cart) throws IOException;

    /**
     * @see #saveCart(ReadOnlyCart)
     */
    void saveCart(ReadOnlyCart cart, Path filePath) throws IOException;

}
