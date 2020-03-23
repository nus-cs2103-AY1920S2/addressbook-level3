package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class CsvProcessorTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvFileTest");
    private static final Path INVALID_CSV_FILE = TEST_DATA_FOLDER.resolve("invalidCsvFile.csv");
    private static final Path INVALID_FILEPATH = TEST_DATA_FOLDER.resolve("invalidFilePath.csv");
    private static final Path VALID_CSV_FILE = TEST_DATA_FOLDER.resolve("validCsvFile.csv");

    @Test
    public void invalidFilePath_retrieveData_throwParseException() throws Exception {
        assertThrows(ParseException.class, CsvProcessor.MESSAGE_READ_FAILED, ()->
            CsvProcessor.retrieveData(INVALID_FILEPATH));
    }

    @Test
    public void invalidCsvFile_retrieveData_throwsParseException() throws Exception {
        assertThrows(ParseException.class, CsvProcessor.MESSAGE_INVALID_FORMAT, ()->
            CsvProcessor.retrieveData(INVALID_CSV_FILE));
    }

    @Test
    public void validCsvFile_retrieveData_returnCorrectData() throws Exception {
        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("order,tid/1023456789,n/Amwos Cheong,a/Blk 572 Hougang st 51 #11-37 S530572,p/90010019,"
            + "dts/2020-03-10 1650,w/Marsiling,cod/$9.50,c/Leave it at the riser,type/glass");
        assertEquals(CsvProcessor.retrieveData(VALID_CSV_FILE), expectedResult);
    }
}
