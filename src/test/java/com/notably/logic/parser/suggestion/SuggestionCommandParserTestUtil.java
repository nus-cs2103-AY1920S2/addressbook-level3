package com.notably.logic.parser.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.notably.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing suggestion command parsers.
 */
public class SuggestionCommandParserTestUtil {
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(SuggestionCommandParser parser, String userInput, String expectedMessage) {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
