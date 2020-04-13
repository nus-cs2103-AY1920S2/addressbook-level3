package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ResumeBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ResumeBookBuilder;
import seedu.address.testutil.TypicalResumeBook;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonResumeBookStorage resumeBookStorage = new JsonResumeBookStorage(getTempFilePath("rb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(resumeBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void resumeBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonResumeBookStorage} class
         * More extensive testing of UserPref saving/reading is done in {@link JsonResumeBookStorageTest} class.
         */
        ResumeBook original = new ResumeBookBuilder(TypicalResumeBook.TYPICAL).build();
        storageManager.saveResumeBook(original);
        ReadOnlyResumeBook retrieved = storageManager.readResumeBook().get();
        assertEquals(original, new ResumeBook(retrieved));
    }

    @Test
    public void getResumeBookFilePath() {
        assertNotNull(storageManager.getResumeBookFilePath());
    }

}
