package seedu.eylah.expensesplitter.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;
import static seedu.eylah.expensesplitter.testutil.TypicalReceipt.getTypicalReceiptBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.storage.JsonUserPrefsStorage;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.ReceiptBook;

public class SplitterStorageManagerTest {

    @TempDir
    public Path testFolder;

    private SplitterStorageManager storageManager;


    @BeforeEach
    public void setUp() {
        JsonPersonAmountBookStorage personAmountBookStorage =
            new JsonPersonAmountBookStorage(getTempFilePath("pABook"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonReceiptBookStorage receiptBookStorage = new JsonReceiptBookStorage(getTempFilePath("rBook"));
        storageManager = new SplitterStorageManager(personAmountBookStorage, receiptBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void personAmountBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPersonAmountBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPersonAmountBookStorageTest} class.
         */
        PersonAmountBook original = getTypicalPersonAmountBook();
        storageManager.savePersonAmountBook(original);
        ReadOnlyPersonAmountBook retrieved = storageManager.readPersonAmountBook().get();
        assertEquals(original, new PersonAmountBook(retrieved));
    }

    @Test
    public void getPersonAmountBookFilePath() {
        assertNotNull(storageManager.getPersonAmountBookFilePath());
    }

    @Test
    public void receiptBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonReceiptBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonReceiptBookStorageTest} class.
         */
        ReceiptBook original = getTypicalReceiptBook();
        storageManager.saveReceiptBook(original);
        ReadOnlyReceiptBook retrieved = storageManager.readReceiptBook().get();
        assertEquals(original, new ReceiptBook(retrieved));
    }

    @Test
    public void getReceiptBookFilePath() {
        assertNotNull(storageManager.getReceiptBookFilePath());
    }

}
