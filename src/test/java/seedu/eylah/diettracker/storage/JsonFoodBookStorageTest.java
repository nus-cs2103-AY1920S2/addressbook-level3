package seedu.eylah.diettracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.eylah.diettracker.testutil.TypicalFood.BAGUETTE;
import static seedu.eylah.diettracker.testutil.TypicalFood.MILKSHAKE;
import static seedu.eylah.diettracker.testutil.TypicalFood.PASTA;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;

public class JsonFoodBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFoodBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFoodBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFoodBook(null));
    }

    private java.util.Optional<ReadOnlyFoodBook> readFoodBook(String filePath) throws Exception {
        return new JsonFoodBookStorage(Paths.get(filePath)).readFoodBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFoodBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFoodBook("notJsonFormatFoodBook.json"));
    }

    @Test
    public void readFoodBook_invalidFoodFoodBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFoodBook("invalidFoodFoodBook.json"));
    }

    @Test
    public void readFoodBook_invalidAndValidFoodFoodBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFoodBook("invalidAndValidFoodFoodBook.json"));
    }

    @Test
    public void readAndSaveFoodBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempFoodBook.json");
        FoodBook original = getTypicalFoodBook();
        JsonFoodBookStorage jsonFoodBookStorage = new JsonFoodBookStorage(filePath);

        // Save in new file and read back
        jsonFoodBookStorage.saveFoodBook(original, filePath);
        ReadOnlyFoodBook readBack = jsonFoodBookStorage.readFoodBook(filePath).get();
        assertEquals(original, new FoodBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFood(MILKSHAKE);
        original.removeFood(PASTA);
        jsonFoodBookStorage.saveFoodBook(original, filePath);
        readBack = jsonFoodBookStorage.readFoodBook(filePath).get();
        assertEquals(original, new FoodBook(readBack));

        // Save and read without specifying file path
        original.addFood(BAGUETTE);
        jsonFoodBookStorage.saveFoodBook(original); // file path not specified
        readBack = jsonFoodBookStorage.readFoodBook().get(); // file path not specified
        assertEquals(original, new FoodBook(readBack));

    }

    @Test
    public void saveFoodBook_nullFoodBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFoodBook(null, "SomeFile.json"));
    }

    /**
    * Saves {@code addressBook} at the specified {@code filePath}.
    */
    private void saveFoodBook(ReadOnlyFoodBook addressBook, String filePath) {
        try {
            new JsonFoodBookStorage(Paths.get(filePath))
                .saveFoodBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFoodBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFoodBook(new FoodBook(), null));
    }
}
