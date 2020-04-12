package com.notably.logic.suggestion.handler;

import java.util.Optional;
import java.util.logging.Logger;

import com.notably.commons.LogsCenter;
import com.notably.logic.suggestion.generator.SuggestionGenerator;
import com.notably.model.Model;

/**
 * Represents a Handler for the command word "exit".
 */
public class ExitSuggestionHandler implements SuggestionHandler<SuggestionGenerator> {
    public static final String COMMAND_WORD = "exit";

    private static final String RESPONSE_MESSAGE = "Exit the application";

    private static final Logger logger = LogsCenter.getLogger(ExitSuggestionHandler.class);

    private Model model;

    public ExitSuggestionHandler(Model model) {
        this.model = model;
    }

    @Override
    public Optional<SuggestionGenerator> handle() {
        logger.info("Starting handle method inside ExitSuggestionHandler");
        model.setResponseText(RESPONSE_MESSAGE);
        return Optional.empty();
    }
}
