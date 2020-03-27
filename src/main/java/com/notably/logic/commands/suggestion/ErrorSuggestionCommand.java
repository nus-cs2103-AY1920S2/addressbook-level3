package com.notably.logic.commands.suggestion;

import java.util.Objects;

import com.notably.model.Model;

public class ErrorSuggestionCommand implements SuggestionCommand {
    private String errorMessage;

    public ErrorSuggestionCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void execute(Model model) {
        // Nullity check
        Objects.requireNonNull(model);

        // Set response text
        model.setResponseText(errorMessage);
    }
}
