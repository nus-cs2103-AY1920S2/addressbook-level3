package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.HOON;
import static seedu.address.testutil.TypicalStudents.IDA;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.StudentTAble;

public class JsonStudentTAbleStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonStudentTAbleStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readStudentTAble_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudentTAble(null));
    }

    private java.util.Optional<ReadOnlyStudent> readStudentTAble(String filePath) throws Exception {
        return new JsonStudentStorage(Paths.get(filePath)).readStudentTAble(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStudentTAble("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudentTAble("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidStudentAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudentTAble("invalidStudentAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidStudentAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudentTAble("invalidAndValidStudentAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        StudentTAble original = getTypicalStudentTAble();
        JsonStudentStorage jsonAddressBookStorage = new JsonStudentStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveStudentTAble(original, filePath);
        ReadOnlyStudent readBack = jsonAddressBookStorage.readStudentTAble(filePath).get();
        assertEquals(original, new StudentTAble(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        jsonAddressBookStorage.saveStudentTAble(original, filePath);
        readBack = jsonAddressBookStorage.readStudentTAble(filePath).get();
        assertEquals(original, new StudentTAble(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonAddressBookStorage.saveStudentTAble(original); // file path not specified
        readBack = jsonAddressBookStorage.readStudentTAble().get(); // file path not specified
        assertEquals(original, new StudentTAble(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudentTAble(null, "SomeFile.json"));
    }

    /**
     * Saves {@code studentTAble} at the specified {@code filePath}.
     */
    private void saveStudentTAble(ReadOnlyStudent studentTAble, String filePath) {
        try {
            new JsonStudentStorage(Paths.get(filePath))
                    .saveStudentTAble(studentTAble, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudentTAble(new StudentTAble(), null));
    }
}
