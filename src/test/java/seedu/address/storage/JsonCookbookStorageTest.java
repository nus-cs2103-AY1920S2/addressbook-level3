package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.getTypicalCookbook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Cookbook;
import seedu.address.model.ReadOnlyCookbook;

public class JsonCookbookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCookbookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCookbook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCookbook(null));
    }

    private java.util.Optional<ReadOnlyCookbook> readCookbook(String filePath) throws Exception {
        return new JsonCookbookStorage(Paths.get(filePath)).readCookbook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCookbook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCookbook("notJsonFormatCookbook.json"));
    }

    @Test
    public void readCookbook_invalidRecipeCookbook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCookbook("invalidRecipeCookbook.json"));
    }

    @Test
    public void readCookbook_invalidAndValidRecipeCookbook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCookbook("invalidAndValidRecipeCookbook.json"));
    }

    @Test
    public void readAndSaveCookbook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCookbook.json");
        Cookbook original = getTypicalCookbook();
        JsonCookbookStorage jsonCookbookStorage = new JsonCookbookStorage(filePath);

        // Save in new file and read back
        jsonCookbookStorage.saveCookbook(original, filePath);
        ReadOnlyCookbook readBack = jsonCookbookStorage.readCookbook(filePath).get();
        assertEquals(original, new Cookbook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.removeRecipe(AGLIO_OLIO);
        jsonCookbookStorage.saveCookbook(original, filePath);
        readBack = jsonCookbookStorage.readCookbook(filePath).get();
        assertEquals(original, new Cookbook(readBack));

        // Save and read without specifying file path
        jsonCookbookStorage.saveCookbook(original); // file path not specified
        readBack = jsonCookbookStorage.readCookbook().get(); // file path not specified
        assertEquals(original, new Cookbook(readBack));
    }

    @Test
    public void saveCookbook_nullCookbook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCookbook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code cookbook} at the specified {@code filePath}.
     */
    private void saveCookbook(ReadOnlyCookbook cookbook, String filePath) {
        try {
            new JsonCookbookStorage(Paths.get(filePath))
                    .saveCookbook(cookbook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCookbook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCookbook(new Cookbook(), null));
    }
}
