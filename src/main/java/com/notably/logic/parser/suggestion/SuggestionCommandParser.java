package com.notably.logic.parser.suggestion;

import java.util.Optional;

import com.notably.logic.commands.suggestion.SuggestionCommand;

/**
 * Represents a Suggestion Command Parser that is able to parse user input into a SuggestionCommand.
 * @param <T> Generic type of SuggestionCommand.
 */
public interface SuggestionCommandParser<T extends SuggestionCommand> {
    /**
     * Parses user input into a suggestion command and returns it.
     *
     * @param userInput The user input.
     * @return The optional suggestion command.
     */
    Optional<T> parse(String userInput);
}
