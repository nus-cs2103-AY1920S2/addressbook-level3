package com.notably.logic.parser.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
