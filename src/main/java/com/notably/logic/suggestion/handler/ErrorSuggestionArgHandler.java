package com.notably.logic.suggestion.handler;

import java.util.Optional;

import com.notably.logic.suggestion.generator.SuggestionGenerator;
import com.notably.model.Model;

/**
 * Represents a Handler for the command word "error".
 */
public class ErrorSuggestionArgHandler implements SuggestionArgHandler<SuggestionGenerator> {
    private static final String ERROR_MESSAGE_INVALID_COMMAND = "\"%s\" is an invalid command format. "
            + "To see the list of available commands, type: help";

    private Model model;

    public ErrorSuggestionArgHandler(Model model) {
        this.model = model;
    }

    @Override
    public Optional<SuggestionGenerator> handleArg(String userInput) {
        model.setResponseText(String.format(ERROR_MESSAGE_INVALID_COMMAND, userInput));
        return Optional.empty();
    }
}
