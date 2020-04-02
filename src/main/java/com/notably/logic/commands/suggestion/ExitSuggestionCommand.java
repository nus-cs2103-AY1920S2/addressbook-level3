package com.notably.logic.commands.suggestion;

import java.util.Objects;

import com.notably.model.Model;

/**
 * Represents a suggestion command object to exit the Notably app.
 */
public class ExitSuggestionCommand implements SuggestionCommand {
    public static final String COMMAND_WORD = "exit";
    private static final String RESPONSE_MESSAGE = "Exit the application";

    @Override
    public void execute(Model model) {
        // Nullity check
        Objects.requireNonNull(model);

        // Set response text
        model.setResponseText(RESPONSE_MESSAGE);
    }
}
