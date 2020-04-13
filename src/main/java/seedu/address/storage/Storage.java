package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CookbookStorage, InventoryStorage, CartStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCookbookFilePath();

    @Override
    Path getInventoryFilePath();

    @Override
    Path getCartFilePath();

    @Override
    Optional<ReadOnlyCookbook> readCookbook() throws DataConversionException, IOException;

    @Override
    void saveCookbook(ReadOnlyCookbook cookbook) throws IOException;

    @Override
    Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException;

    @Override
    void saveInventory(ReadOnlyInventory inventory) throws IOException;

    @Override
    Optional<ReadOnlyCart> readCart() throws DataConversionException, IOException;

    @Override
    void saveCart(ReadOnlyCart cart) throws IOException;

}
