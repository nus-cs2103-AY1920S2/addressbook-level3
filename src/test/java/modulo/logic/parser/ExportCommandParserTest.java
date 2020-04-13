package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.ExportCommand;
import modulo.logic.parser.exceptions.ParseException;

/**
 * Test class to test the functionality of the ExportCommandParser.
 */
public class ExportCommandParserTest {

    private final ExportCommandParser exportCommandParser = new ExportCommandParser();


    @Test
    public void parse_validArgs_returnsExportCommand() {
        // no leading and trailing whitespaces
        ExportCommand expectedExportCommand =
                new ExportCommand(ParserUtil.parseDirectory("directory"));
        assertParseSuccess(exportCommandParser, " d/directory", expectedExportCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(exportCommandParser, "  d/directory     ", expectedExportCommand);
    }

    @Test
    public void parse_emptyArg_returnsExportCommand() {
        ExportCommand expectedExportCommand =
                new ExportCommand();
        assertParseSuccess(exportCommandParser, "", expectedExportCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(exportCommandParser, "      ", expectedExportCommand);
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> exportCommandParser.parse(" invalid_input"));
        assertParseFailure(exportCommandParser, " invalid_input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
