package com.notably.logic.suggestion.handler;

import java.util.Optional;

import com.notably.logic.suggestion.generator.SuggestionGenerator;

/**
 * Represents a Suggestion Handler.
 *
 * @param <T> Generic type of SuggestionGenerator.
 */
public interface SuggestionHandler<T extends SuggestionGenerator> {
    /**
     * Handles user input.
     *
     * @return The optional generator command.
     */
    Optional<T> handle();
}
