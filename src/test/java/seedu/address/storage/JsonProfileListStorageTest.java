package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProfileList;

//@@author chanckben
public class JsonProfileListStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonProfileListStorageTest");

    @Test
    public void readProfileList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readProfileList(null));
    }

    private java.util.Optional<ProfileList> readProfileList(String filePath) throws Exception {
        return new JsonProfileListStorage(Paths.get(filePath)).readProfileList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_throwsDataConversionException() throws Exception {
        assertFalse(readProfileList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readProfileList("notJsonFormatProfileList.json"));
    }

    @Test
    public void readModuleList_invalidProfileProfileList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readProfileList("invalidProfileProfileList.json"));
    }

    @Test
    public void readModuleList_invalidAndValidProfileProfileList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readProfileList("invalidAndValidProfileProfileList.json"));
    }

    @Test
    public void saveProfileList_nullProfileList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProfileList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code profileList} at the specified {@code filePath}.
     */
    private void saveProfileList(ProfileList profileList, String filePath) {
        try {
            new JsonProfileListStorage(Paths.get(filePath))
                    .saveProfileList(profileList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProfileList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProfileList(new ProfileList(), null));
    }
}
