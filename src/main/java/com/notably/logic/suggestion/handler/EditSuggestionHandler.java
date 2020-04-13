package com.notably.logic.suggestion.handler;

import java.util.Optional;
import java.util.logging.Logger;

import com.notably.commons.LogsCenter;
import com.notably.logic.suggestion.generator.SuggestionGenerator;
import com.notably.model.Model;

/**
 * Represents a Handler for the command word "edit".
 */
public class EditSuggestionHandler implements SuggestionHandler<SuggestionGenerator> {
    public static final String COMMAND_WORD = "edit";

    private static final String RESPONSE_MESSAGE = "Edit this note";

    private static final Logger logger = LogsCenter.getLogger(EditSuggestionHandler.class);

    private Model model;

    public EditSuggestionHandler(Model model) {
        this.model = model;
    }

    @Override
    public Optional<SuggestionGenerator> handle() {
        logger.info("Starting handle method inside EditSuggestionHandler");
        model.setResponseText(RESPONSE_MESSAGE);
        return Optional.empty();
    }
}
