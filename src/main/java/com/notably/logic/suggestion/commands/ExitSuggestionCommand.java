package com.notably.logic.suggestion.commands;

import java.util.Objects;

import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.model.Model;

/**
 * Represents a suggestion command object to exit the Notably app.
 */
public class ExitSuggestionCommand implements SuggestionCommand {
    private static final String COMMAND_WORD = "exit";
    private static final String RESPONSE_MESSAGE = "Exits Notably app";

    @Override
    public void execute(Model model) {
        // Nullity check
        Objects.requireNonNull(model);

        // Set response text
        model.setResponseText(RESPONSE_MESSAGE);
    }
}
