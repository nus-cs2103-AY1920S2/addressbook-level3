package com.notably.logic.suggestion.handler;

import java.util.Optional;
import java.util.logging.Logger;

import com.notably.commons.LogsCenter;
import com.notably.logic.suggestion.generator.SuggestionGenerator;
import com.notably.model.Model;

/**
 * Represents a Handler for the command word "help".
 */
public class HelpSuggestionHandler implements SuggestionHandler<SuggestionGenerator> {
    public static final String COMMAND_WORD = "help";

    private static final String RESPONSE_MESSAGE = "Display a list of available commands";

    private static final Logger logger = LogsCenter.getLogger(HelpSuggestionHandler.class);

    private Model model;

    public HelpSuggestionHandler(Model model) {
        this.model = model;
    }

    @Override
    public Optional<SuggestionGenerator> handle() {
        logger.info("Starting handle method inside HelpSuggestionHandler");
        model.setResponseText(RESPONSE_MESSAGE);
        return Optional.empty();
    }
}
