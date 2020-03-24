package com.notably.logic.suggestion.commands;

import java.util.Objects;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.logic.suggestion.SuggestionCommand;
import com.notably.model.Model;

/**
 * Represents a suggestion command object to display a list of available commands.
 */
public class HelpSuggestionCommand implements SuggestionCommand {
    private static final String COMMAND_WORD = "help";
    private static final String RESPONSE_MESSAGE = "Display a list of available commands";

    @Override
    public void execute(Model model) {
        // Nullity check
        Objects.requireNonNull(model);

        // Set response text
        model.setResponseText(RESPONSE_MESSAGE);
    }
}
