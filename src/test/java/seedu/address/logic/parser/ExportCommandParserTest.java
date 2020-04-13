package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private static final String TEST_LIFE_EXPORT_CSV = "lifeTEST.csv";
    private static final String TEST_GROUP_EXPORT_CSV = "groupTEST.csv";
    private static final String TEST_EVENT_EXPORT_CSV = "eventTEST.csv";

    private ExportCommandParser parser = new ExportCommandParser();


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(
                parser, "some invalid string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {
        // no leading and trailing whitespaces
        ExportCommand expectedExportCommand =
                new ExportCommand("lifeTEST.csv", "groupTEST.csv", "eventTEST.csv");
        assertParseSuccess(parser, " l/lifeTEST.csv"
                + " g/groupTEST.csv"
                + " e/eventTEST.csv", expectedExportCommand);

        //multiple whitespaces between keywords
        assertParseSuccess(parser, " \n l/"
                + TEST_LIFE_EXPORT_CSV
                + " \n \t g/" + TEST_GROUP_EXPORT_CSV
                + " \n \t e/" + TEST_EVENT_EXPORT_CSV
                + "\t", expectedExportCommand);
    }

    @Test
    public void parse_emptyFilePath_throwsParseException() {
        assertParseFailure(
                parser, " l/ " + " g/ " + " e/ ",
                String.format("Empty file name to export provided." + "\n" + ExportCommand.MESSAGE_USAGE));
    }

}
