package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.CsvProcessor;
import seedu.address.testutil.OrderBuilder;

class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvFileTest");
    private static final Path VALID_CSV_FILE = TEST_DATA_FOLDER.resolve("validCsvFile.csv");
    private Model model = new ModelManager(new OrderBook(), new UserPrefs());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImportCommand(null));
    }

    @Test
    public void constructor_emptyList_returnCorrectMessage() {
        String expectedMessage = "0 delivery order(s) and 0 return order(s) being imported.\n";
        Model expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        assertCommandSuccess(new ImportCommand(new ArrayList<>()), model, expectedMessage, expectedModel);
    }

//    @Test
//    public void execute_validOrderFormat_returnCorrectCounter() throws Exception {
//        ModelManager expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
//        int counter = 0;
//
//        List<String> dataRetrieved = new ArrayList<>();
//        for (String data : dataRetrieved) {
//            expectedModel.addOrder();
//            counter++;
//        }
//        String expectedMessage = counter + " delivery order(s) and 0 return order(s) being imported.\n";
//        ImportCommand importCommand = new ImportCommand(dataRetrieved);
//
//        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void equals() throws Exception {
        List<String> sentence = new ArrayList<>();
        sentence.add("order,tid/1023456789,n/Amwos Cheong,a/Blk 572 Hougang st 51 #11-37 S530572,p/90010019," +
                "dts/2020-03-10 1650,w/Marsiling,cod/$9.50,c/Leave it at the riser,type/glass");
        ImportCommand expectedCommand = new ImportCommand(sentence);
        assertTrue(new ImportCommand(CsvProcessor.retrieveData(VALID_CSV_FILE)).equals(expectedCommand));
    }
}