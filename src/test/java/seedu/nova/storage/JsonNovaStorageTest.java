package seedu.nova.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.nova.testutil.Assert.assertThrows;
//import static seedu.nova.testutil.TypicalPersons.ALICE;
//import static seedu.nova.testutil.TypicalPersons.HOON;
//import static seedu.nova.testutil.TypicalPersons.IDA;
//import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.nova.testutil.TypicalPersons.getTypicalVersionedAddressBook;
import static seedu.nova.testutil.TypicalPtTasks.getTypicalProgressTracker;
//import static seedu.nova.testutil.TypicalEvents.getTypicalEvents;
//import static seedu.nova.testutil.TypicalEvents.getTypicalSchedule;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.nova.commons.exceptions.DataConversionException;

import seedu.nova.model.Nova;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.model.progresstracker.ProgressTracker;


public class JsonNovaStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonNovaStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readNova(null));
    }

    private java.util.Optional<Nova> readNova(String filePath) throws Exception {
        return new JsonNovaStorage(Paths.get(filePath)).readNova(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readNova("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readNova("notJsonFormatNova.json"));
    }


    @Test
    public void readNova_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNova("invalidPersonAddressBook.json"));
    }

    @Test
    public void readNova_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNova("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readNova_invalidAndValidPtTasks_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNova("invalidAndValidPtTask.json"));
    }

    @Test
    public void readNova_invalidPtTasks_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readNova("invalidPtTask.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        VersionedAddressBook originalAb = getTypicalVersionedAddressBook();
        ProgressTracker originalPt = getTypicalProgressTracker();
        JsonNovaStorage jsonNovaStorage = new JsonNovaStorage(filePath);
        Nova nova = new Nova();

        //Note from SY: Unable to proceed further with this test case as I'm unsure how event/schedule work together
        // Save in new file and read back
        nova.setAddressBookNova(originalAb);
        nova.setProgressTrackerNova(originalPt);
        //jsonNovaStorage.saveNova(nova, filePath);
        //Nova readBack = jsonNovaStorage.readNova(filePath).get();
        //assertEquals(originalAb, new AddressBook(readBack.getAddressBookNova()));

        /*
        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readNova(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readNova().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));
         */
    }

    @Test
    public void saveNova_nullNova_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNova(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */

    private void saveNova(Nova nova, String filePath) {
        try {
            new JsonNovaStorage(Paths.get(filePath))
                    .saveNova(nova, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveNova(new Nova(), null));
    }
}
