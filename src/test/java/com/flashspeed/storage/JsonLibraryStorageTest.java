package com.flashspeed.storage;

import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.flashspeed.commons.exceptions.DataConversionException;
import com.flashspeed.model.Library;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.testutil.DeckUtils;

public class JsonLibraryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonLibraryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readLibrary_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLibrary(null));
    }

    private java.util.Optional<ReadOnlyLibrary> readLibrary(String filePath) throws Exception {
        return new JsonLibraryStorage(Paths.get(filePath)).readLibrary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLibrary("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readLibrary("notJsonFormatLibrary.json"));
    }

    @Test
    public void readLibrary_invalidPersonLibrary_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLibrary("invalidDeckLibrary.json"));
    }

    @Test
    public void readLibrary_invalidAndValidPersonLibrary_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLibrary("invalidAndValidPersonLibrary.json"));
    }

    @Test
    public void readAndSaveLibrary_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempLibrary.json");
        Library original = DeckUtils.getTypicalLibrary();
        JsonLibraryStorage jsonLibraryStorage = new JsonLibraryStorage(filePath);

        // Save in new file and read back
        jsonLibraryStorage.saveLibrary(original, filePath);
        ReadOnlyLibrary readBack = jsonLibraryStorage.readLibrary(filePath).get();
        assertEquals(original, new Library(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDeck(DeckUtils.NEW_EMPTY_DECK);
        original.deleteDeck(DeckUtils.NEW_EMPTY_DECK);
        jsonLibraryStorage.saveLibrary(original, filePath);
        readBack = jsonLibraryStorage.readLibrary(filePath).get();
        assertEquals(original, new Library(readBack));

        // Save and read without specifying file path
        original.addDeck(DeckUtils.NEW_EMPTY_DECK);
        jsonLibraryStorage.saveLibrary(original); // file path not specified
        readBack = jsonLibraryStorage.readLibrary().get(); // file path not specified
        assertEquals(original, new Library(readBack));

    }

    @Test
    public void saveLibrary_nullLibrary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLibrary(null, "SomeFile.json"));
    }

    /**
     * Saves {@code library} at the specified {@code filePath}.
     */
    private void saveLibrary(ReadOnlyLibrary library, String filePath) {
        try {
            new JsonLibraryStorage(Paths.get(filePath))
                    .saveLibrary(library, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLibrary_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLibrary(new Library(), null));
    }
}
