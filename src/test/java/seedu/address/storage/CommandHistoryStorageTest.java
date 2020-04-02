package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CommandHistoryStorageTest {

    private static final ArrayList<String> VALID_HISTORY_LIST = new ArrayList<>(List.of("1", "2", "3"));
    private static final ArrayList<String> EMPTY_HISTORY_LIST = new ArrayList<>();

    @TempDir
    public Path testFolder;

    private CommandHistoryStorage historyStorage;

    @BeforeEach
    public void setUp() {
        historyStorage = new CommandHistoryStorage(getTempFilePath("test.txt"));
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void readFromStorage_noErrorThrown() {
        assertDoesNotThrow(() -> historyStorage.readFromStorage());
    }

    @Test
    public void saveToStorage_nullHistory_errorThrown() {
        assertThrows(NullPointerException.class, () -> historyStorage.saveToStorage(null));
    }

    @Test
    public void readFromStorage_afterSavingToStorage_correctOutput() {
        historyStorage.saveToStorage(VALID_HISTORY_LIST);
        ArrayList<String> storageList = historyStorage.readFromStorage();
        assertEquals(VALID_HISTORY_LIST.toString(), storageList.toString());
    }

    @Test void saveToStorage_calledMultipleTimes_fileCorrectlyOverwritten() {
        historyStorage.saveToStorage(VALID_HISTORY_LIST);
        historyStorage.saveToStorage(VALID_HISTORY_LIST);
        historyStorage.saveToStorage(VALID_HISTORY_LIST);
        historyStorage.saveToStorage(VALID_HISTORY_LIST);
        ArrayList<String> storageList = historyStorage.readFromStorage();
        assertEquals(VALID_HISTORY_LIST.toString(), storageList.toString());
    }


    @Test
    public void clearStorage_nonEmptyStorage_correctOutcome() {
        historyStorage.saveToStorage(VALID_HISTORY_LIST);
        historyStorage.clearStorage();
        ArrayList<String> storageList = historyStorage.readFromStorage();
        assertEquals(EMPTY_HISTORY_LIST.toString(), storageList.toString());
    }
}
