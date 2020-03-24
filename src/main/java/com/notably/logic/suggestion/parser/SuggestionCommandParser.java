package com.notably.logic.suggestion.parser;

import com.notably.logic.parser.exceptions.ParseException;
import com.notably.logic.suggestion.SuggestionCommand;

/**
 * Represents a Suggestion Command Parser that is able to parse user input into a SuggestionCommand.
 * @param <T> Generic type of SuggestionCommand.
 */
public interface SuggestionCommandParser<T extends SuggestionCommand> {
    /**
     * Parses user input into a suggestion command and returns it.
     * @param userInput The user input.
     * @return The suggestion command.
     * @throws ParseException if {@code userInput} does not conform to the expected format.
     */
    T parse(String userInput) throws ParseException;
}
