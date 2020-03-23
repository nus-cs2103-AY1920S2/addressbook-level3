package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;


class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalidCsvFile.csv",
                ParserUtil.MESSAGE_INVALID_CSV_FILEPATH);
    }
}