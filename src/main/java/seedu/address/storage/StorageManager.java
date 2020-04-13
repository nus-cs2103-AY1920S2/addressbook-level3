package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Cooking Papa data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CookbookStorage cookbookStorage;
    private InventoryStorage inventoryStorage;
    private CartStorage cartStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CookbookStorage cookbookStorage,
            InventoryStorage inventoryStorage, CartStorage cartStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.cookbookStorage = cookbookStorage;
        this.inventoryStorage = inventoryStorage;
        this.cartStorage = cartStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Cookbook methods ==============================

    @Override
    public Path getCookbookFilePath() {
        return cookbookStorage.getCookbookFilePath();
    }

    @Override
    public Optional<ReadOnlyCookbook> readCookbook() throws DataConversionException, IOException {
        return readCookbook(cookbookStorage.getCookbookFilePath());
    }

    @Override
    public Optional<ReadOnlyCookbook> readCookbook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cookbookStorage.readCookbook(filePath);
    }

    @Override
    public void saveCookbook(ReadOnlyCookbook cookbook) throws IOException {
        saveCookbook(cookbook, cookbookStorage.getCookbookFilePath());
    }

    @Override
    public void saveCookbook(ReadOnlyCookbook cookbook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cookbookStorage.saveCookbook(cookbook, filePath);
    }


    // ================ Inventory methods ==============================

    @Override
    public Path getInventoryFilePath() {
        return inventoryStorage.getInventoryFilePath();
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException {
        return readInventory(inventoryStorage.getInventoryFilePath());
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return inventoryStorage.readInventory(filePath);
    }

    @Override
    public void saveInventory(ReadOnlyInventory inventory) throws IOException {
        saveInventory(inventory, inventoryStorage.getInventoryFilePath());
    }

    @Override
    public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        inventoryStorage.saveInventory(inventory, filePath);
    }


    // ================ Cart methods ==============================

    @Override
    public Path getCartFilePath() {
        return cartStorage.getCartFilePath();
    }

    @Override
    public Optional<ReadOnlyCart> readCart() throws DataConversionException, IOException {
        return readCart(cartStorage.getCartFilePath());
    }

    @Override
    public Optional<ReadOnlyCart> readCart(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cartStorage.readCart(filePath);
    }

    @Override
    public void saveCart(ReadOnlyCart cart) throws IOException {
        saveCart(cart, cartStorage.getCartFilePath());
    }

    @Override
    public void saveCart(ReadOnlyCart cart, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cartStorage.saveCart(cart, filePath);
    }

}
