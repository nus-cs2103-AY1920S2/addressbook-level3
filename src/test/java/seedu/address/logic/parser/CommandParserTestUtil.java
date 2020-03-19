package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.AddCommandTest;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.*;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput, null);
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
            parser.parse(userInput, null);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
