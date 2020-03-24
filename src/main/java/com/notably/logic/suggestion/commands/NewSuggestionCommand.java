package com.notably.logic.suggestion.commands;

import java.util.Objects;

import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.model.Model;

/**
 * Represents a suggestion command object to create a new note.
 */
public class NewSuggestionCommand implements SuggestionCommand {
    private static final String COMMAND_WORD = "new";
    private static final String RESPONSE_MESSAGE = "Create a new note";

    @Override
    public void execute(Model model) {
        // Nullity check
        Objects.requireNonNull(model);

        // Set response text
        model.setResponseText(RESPONSE_MESSAGE);
    }
}
