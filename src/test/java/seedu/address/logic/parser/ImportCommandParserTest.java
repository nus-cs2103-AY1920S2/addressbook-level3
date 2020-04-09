package seedu.address.logic.parser;

import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_CSV_FILEPATH;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

//@@author Exeexe93
class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    void parse_invalidArgs_throwsParseException() {
        String fileName = "invalidCsvFile.csv";
        assertParseFailure(parser, fileName,
                MESSAGE_INVALID_CSV_FILEPATH + fileName);
    }

    @Test
    void parse_withoutCsvFileExtension_throwsParseException() {
        String fileName = "invalidCsvFile";
        assertParseFailure(parser, fileName,
                MESSAGE_USAGE);
    }
}
