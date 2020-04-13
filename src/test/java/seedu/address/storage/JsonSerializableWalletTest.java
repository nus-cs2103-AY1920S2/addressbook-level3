package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Wallet;
import seedu.address.model.transaction.Budget;

import seedu.address.testutil.BudgetBuilder;
import seedu.address.testutil.TypicalWallet;

public class JsonSerializableWalletTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableWalletTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve("typicalWallet.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER.resolve("invalidTransactionWallet.json");

    @Test
    public void toModelType_typicalWalletFile_success() throws DataConversionException, IllegalValueException {
        JsonSerializableWallet dataFromFile = JsonUtil
                .readJsonFile(TYPICAL_TRANSACTIONS_FILE, JsonSerializableWallet.class).get();
        Wallet walletFromFile = dataFromFile.toModelType();
        Wallet typicalWallet = TypicalWallet.getTypicalWallet();
        assertEquals(walletFromFile, typicalWallet);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException()
            throws DataConversionException, IllegalValueException {
        JsonSerializableWallet dataFromFile = JsonUtil
                .readJsonFile(INVALID_TRANSACTION_FILE, JsonSerializableWallet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void defaultBudget_works() throws DataConversionException, IllegalValueException {
        JsonSerializableWallet dataFromFile = JsonUtil
                .readJsonFile(TYPICAL_TRANSACTIONS_FILE, JsonSerializableWallet.class).get();
        Wallet walletFromFile = dataFromFile.toModelType();
        Budget defaultBudget = walletFromFile.getDefaultBudget();
        Budget expectedDefaultBudget = new BudgetBuilder()
                .withAmount("1000")
                .withMonth("3")
                .withYear("2020")
                .setAsDefault()
                .buildBudget();
        assertEquals(expectedDefaultBudget, defaultBudget);
    }
}
