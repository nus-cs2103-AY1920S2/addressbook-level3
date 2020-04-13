package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ResumeBook;
import seedu.address.model.item.Internship;
import seedu.address.model.item.Project;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.ResumeBookBuilder;
import seedu.address.testutil.TypicalInternship;
import seedu.address.testutil.TypicalProject;
import seedu.address.testutil.TypicalResumeBook;

public class JsonResumeBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonResumeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readResumeBook(null));
    }

    private Optional<ReadOnlyResumeBook> readResumeBook(String filePath) throws Exception {
        return new JsonResumeBookStorage(Paths.get(filePath)).readResumeBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readResumeBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readResumeBook("notJsonFormatResumeBook.json"));
    }

    @Test
    public void readResumeBook_invalidResumeBook_throwsDataConversionException() {
        // With an invalid user only
        assertThrows(DataConversionException.class, () -> readResumeBook("invalidUserResumeBook.json"));

        // With an invalid internship only
        assertThrows(DataConversionException.class, () -> readResumeBook("invalidInternshipResumeBook.json"));

        // With an invalid resume only
        assertThrows(DataConversionException.class, () -> readResumeBook("invalidResumeResumeBook.json"));

        // With multiple invalid items
        assertThrows(DataConversionException.class, () -> readResumeBook("invalidMultiResumeBook.json"));
    }



    @Test
    public void readAndSaveResumeBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempResumeBook.json");
        ResumeBook original = new ResumeBookBuilder(TypicalResumeBook.TYPICAL_WITHOUT_GOGGLE).build();
        JsonResumeBookStorage jsonResumeBookStorage = new JsonResumeBookStorage(filePath);

        // Save in new file and read back
        jsonResumeBookStorage.saveResumeBook(original, filePath);
        ReadOnlyResumeBook readBack = jsonResumeBookStorage.readResumeBook(filePath).get();
        assertEquals(original, new ResumeBook(readBack));

        // Save and read back
        Internship google = new InternshipBuilder(TypicalInternship.GOOGLE).withTags("one", "two").build();
        original.addInternship(google);
        Project toDeleteProject = new ProjectBuilder(TypicalProject.ORBITAL).build();
        original.deleteProject(toDeleteProject);
        original.sortInternships(Comparator.comparing(Internship::getFrom));

        jsonResumeBookStorage.saveResumeBook(original, filePath);
        readBack = jsonResumeBookStorage.readResumeBook(filePath).get();
        assertEquals(original, new ResumeBook(readBack));


        // Save and read without specifying file path
        original.deleteInternship(google);
        jsonResumeBookStorage.saveResumeBook(original); // file path not specified
        readBack = jsonResumeBookStorage.readResumeBook().get(); // file path not specified
        assertEquals(original, new ResumeBook(readBack));

    }

    @Test
    public void saveAddressBook_nullResumeBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveResumeBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveResumeBook(ReadOnlyResumeBook resumeBook, String filePath) {
        try {
            new JsonResumeBookStorage(Paths.get(filePath))
                    .saveResumeBook(resumeBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveResumeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveResumeBook(new ResumeBook(), null));
    }
}
