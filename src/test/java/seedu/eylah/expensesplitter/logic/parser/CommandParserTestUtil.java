package seedu.eylah.expensesplitter.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command<SplitterModel> expectedCommand) {
        try {
            Command<SplitterModel> command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }


    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
