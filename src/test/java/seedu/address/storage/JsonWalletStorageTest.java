package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWallet.ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.BUDGET_APRIL_2020;
import static seedu.address.testutil.TypicalWallet.DUCK_RICE;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWallet;
import seedu.address.model.Wallet;

public class JsonWalletStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonWalletStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWallet_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWallet(null));
    }

    private java.util.Optional<ReadOnlyWallet> readWallet(String filePath) throws Exception {
        return new JsonWalletStorage(Paths.get(filePath)).readWallet(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWallet("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readWallet("notJsonFormatWallet.json"));
    }

    @Test
    public void readWallet_invalidTransactionWallet_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWallet("invalidTransactionWallet.json"));
    }

    @Test
    public void readWallet_invalidAndValidTransactionWallet_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWallet("invalidAndValidTransactionWallet.json"));
    }

    @Test
    public void readWallet_invalidBudgetWallet_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWallet("invalidBudgetWallet.json"));
    }

    @Test
    public void readAndSaveWallet_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempWallet.json");
        Wallet original = getTypicalWallet();
        JsonWalletStorage jsonWalletStorage = new JsonWalletStorage(filePath);

        // Save in new file and read back
        jsonWalletStorage.saveWallet(original, filePath);
        ReadOnlyWallet readBack = jsonWalletStorage.readWallet(filePath).get();
        assertEquals(original, new Wallet(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addIncome(ALLOWANCE);
        original.deleteExpense(DUCK_RICE);
        original.setBudget(BUDGET_APRIL_2020);
        jsonWalletStorage.saveWallet(original, filePath);
        readBack = jsonWalletStorage.readWallet(filePath).get();
        assertEquals(original, new Wallet(readBack));

        // Save and read without specifying file path
        original.addExpense(DUCK_RICE);
        jsonWalletStorage.saveWallet(original); // file path not specified
        readBack = jsonWalletStorage.readWallet().get(); // file path not specified
        assertEquals(original, new Wallet(readBack));

    }

    @Test
    public void saveWallet_nullWallet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWallet(null, "SomeFile.json"));
    }

    /**
     * Saves {@code wallet} at the specified {@code filePath}.
     */
    private void saveWallet(ReadOnlyWallet wallet, String filePath) {
        try {
            new JsonWalletStorage(Paths.get(filePath))
                    .saveWallet(wallet, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveWallet_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWallet(new Wallet(), null));
    }
}
