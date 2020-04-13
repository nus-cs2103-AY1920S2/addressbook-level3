package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalIngredients.HAZELNUT;
import static seedu.address.testutil.TypicalIngredients.ICECREAM;
import static seedu.address.testutil.TypicalIngredients.getTypicalInventory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;

public class JsonInventoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonIngredientListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInventory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readInventory(null));
    }

    private java.util.Optional<ReadOnlyInventory> readInventory(String filePath) throws Exception {
        return new JsonInventoryStorage(Paths.get(filePath)).readInventory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInventory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readInventory("notJsonFormatIngredientList.json"));
    }

    @Test
    public void readInventory_invalidIngredientInventory_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readInventory("invalidIngredientList.json"));
    }

    @Test
    public void readInventory_invalidAndValidIngredientInventory_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readInventory("invalidAndValidIngredientList.json"));
    }

    @Test
    public void readAndSaveInventory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempInventory.json");
        Inventory original = getTypicalInventory();
        JsonInventoryStorage jsonInventoryStorage = new JsonInventoryStorage(filePath);

        // Save in new file and read back
        jsonInventoryStorage.saveInventory(original, filePath);
        ReadOnlyInventory readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addIngredient(HAZELNUT);
        original.removeIngredient(APPLE);
        jsonInventoryStorage.saveInventory(original, filePath);
        readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Save and read without specifying file path
        original.addIngredient(ICECREAM);
        jsonInventoryStorage.saveInventory(original); // file path not specified
        readBack = jsonInventoryStorage.readInventory().get(); // file path not specified
        assertEquals(original, new Inventory(readBack));

    }

    @Test
    public void saveInventory_nullInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInventory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code inventory} at the specified {@code filePath}.
     */
    private void saveInventory(ReadOnlyInventory inventory, String filePath) {
        try {
            new JsonInventoryStorage(Paths.get(filePath))
                    .saveInventory(inventory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInventory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveInventory(new Inventory(), null));
    }
}
