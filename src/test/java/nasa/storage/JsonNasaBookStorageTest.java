package nasa.storage;

import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalModules.CS2103T;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nasa.commons.exceptions.DataConversionException;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyNasaBook;

public class JsonNasaBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonNasaBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readNasaBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readNasaBook(null));
    }

    private java.util.Optional<ReadOnlyNasaBook> readNasaBook(String filePath) throws Exception {
        return new JsonNasaBookStorage(Paths.get(filePath),
                Paths.get(filePath), Paths.get(filePath)).readNasaBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readNasaBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readNasaBook("notJsonFormatNasaBook.json"));
    }

    @Test
    public void readNasaBook_invalidModuleNasaBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNasaBook("invalidModuleNasaBook.json"));
    }

    @Test
    public void readNasaBook_invalidAndValidModuleNasaBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNasaBook("invalidAndValidModuleNasaBook.json"));
    }

    @Test
    public void readAndSaveNasaBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempNasaBook.json");
        Path filePathTwo = testFolder.resolve("TempHistoryBook.json");
        Path filePathThree = testFolder.resolve("TempUiHistoryBook.json");
        NasaBook original = getTypicalNasaBook();
        JsonNasaBookStorage jsonNasaBookStorage = new JsonNasaBookStorage(filePath, filePathTwo, filePathThree);

        // Save in new file and read back
        jsonNasaBookStorage.saveNasaBook(original, filePath);
        ReadOnlyNasaBook readBack = jsonNasaBookStorage.readNasaBook(filePath).get();
        assertEquals(original, new NasaBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.removeModule(CS2103T.getModuleCode());
        jsonNasaBookStorage.saveNasaBook(original, filePath);
        readBack = jsonNasaBookStorage.readNasaBook(filePath).get();
        assertEquals(original, new NasaBook(readBack));

        // Save and read without specifying file path
        original.addModule(CS2103T);
        jsonNasaBookStorage.saveNasaBook(original); // file path not specified
        readBack = jsonNasaBookStorage.readNasaBook().get(); // file path not specified
        assertEquals(original, new NasaBook(readBack));

    }

    @Test
    public void saveNasaBook_nullNasaBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNasaBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code NasaBook} at the specified {@code filePath}.
     */
    private void saveNasaBook(ReadOnlyNasaBook nasaBook, String filePath) {
        try {
            new JsonNasaBookStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                    .saveNasaBook(nasaBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveNasaBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNasaBook(new NasaBook(), null));
    }
}
