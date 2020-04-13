package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.HAZELNUT;
import static seedu.address.testutil.TypicalIngredients.ICECREAM;
import static seedu.address.testutil.TypicalIngredients.getTypicalCart;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Cart;
import seedu.address.model.ReadOnlyCart;

public class JsonCartStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonIngredientListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCart_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCart(null));
    }

    private java.util.Optional<ReadOnlyCart> readCart(String filePath) throws Exception {
        return new JsonCartStorage(Paths.get(filePath)).readCart(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCart("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCart("notJsonFormatIngredientList.json"));
    }

    @Test
    public void readCart_invalidIngredientCart_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCart("invalidIngredientList.json"));
    }

    @Test
    public void readCart_invalidAndValidIngredientCart_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCart("invalidAndValidIngredientList.json"));
    }

    @Test
    public void readAndSaveCart_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCart.json");
        Cart original = getTypicalCart();
        JsonCartStorage jsonCartStorage = new JsonCartStorage(filePath);

        // Save in new file and read back
        jsonCartStorage.saveCart(original, filePath);
        ReadOnlyCart readBack = jsonCartStorage.readCart(filePath).get();
        assertEquals(original, new Cart(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addIngredient(HAZELNUT);
        original.removeIngredient(APPLE);
        jsonCartStorage.saveCart(original, filePath);
        readBack = jsonCartStorage.readCart(filePath).get();
        assertEquals(original, new Cart(readBack));

        // Save and read without specifying file path
        original.addIngredient(ICECREAM);
        jsonCartStorage.saveCart(original); // file path not specified
        readBack = jsonCartStorage.readCart().get(); // file path not specified
        assertEquals(original, new Cart(readBack));

    }

    @Test
    public void saveCart_nullCart_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCart(null, "SomeFile.json"));
    }

    /**
     * Saves {@code cart} at the specified {@code filePath}.
     */
    private void saveCart(ReadOnlyCart cart, String filePath) {
        try {
            new JsonCartStorage(Paths.get(filePath)).saveCart(cart, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCart_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCart(new Cart(), null));
    }
}
