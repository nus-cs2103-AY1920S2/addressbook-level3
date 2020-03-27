package seedu.expensela.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.commons.util.JsonUtil;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.testutil.TypicalTransactions;

public class JsonSerializableExpenseLaTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableExpenseLaTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve("typicalTransactionsExpenseLa.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER.resolve("invalidTransactionExpenseLa.json");
    private static final Path DUPLICATE_TRANSACTION_FILE = TEST_DATA_FOLDER
            .resolve("duplicateTransactionExpenseLa.json");

    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableExpenseLa dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
                JsonSerializableExpenseLa.class).get();
        ExpenseLa expenseLaFromFile = dataFromFile.toModelType();
        ExpenseLa typicalTransactionsExpenseLa = TypicalTransactions.getTypicalExpenseLa();
        assertEquals(expenseLaFromFile, typicalTransactionsExpenseLa);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExpenseLa dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
                JsonSerializableExpenseLa.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTransactions_throwsIllegalValueException() throws Exception {
        JsonSerializableExpenseLa dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSACTION_FILE,
                JsonSerializableExpenseLa.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExpenseLa.MESSAGE_DUPLICATE_TRANSACTION,
                dataFromFile::toModelType);
    }

}
