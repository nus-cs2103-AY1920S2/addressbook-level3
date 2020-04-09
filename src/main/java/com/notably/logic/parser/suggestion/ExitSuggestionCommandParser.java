package com.notably.logic.parser.suggestion;

import java.util.Optional;

import com.notably.logic.commands.suggestion.SuggestionCommand;
import com.notably.model.Model;

/**
 * Represents a parser for the command word "edit".
 */
public class ExitSuggestionCommandParser implements SuggestionCommandParser<SuggestionCommand> {
    public static final String COMMAND_WORD = "exit";

    private static final String RESPONSE_MESSAGE = "Exit the application";

    private Model model;

    public ExitSuggestionCommandParser(Model model) {
        this.model = model;
    }

    @Override
    public Optional<SuggestionCommand> parse(String userInput) {
        model.setResponseText(RESPONSE_MESSAGE);
        return Optional.empty();
    }
}
