package com.notably.logic.suggestion;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.suggestion.ErrorSuggestionCommand;
import com.notably.logic.commands.suggestion.ExitSuggestionCommand;
import com.notably.logic.commands.suggestion.HelpSuggestionCommand;
import com.notably.logic.commands.suggestion.SuggestionCommand;
import com.notably.logic.correction.AbsolutePathCorrectionEngine;
import com.notably.logic.correction.CorrectionEngine;
import com.notably.logic.correction.CorrectionResult;
import com.notably.logic.correction.CorrectionStatus;
import com.notably.logic.correction.StringCorrectionEngine;
import com.notably.logic.parser.suggestion.DeleteSuggestionCommandParser;
import com.notably.logic.parser.suggestion.NewSuggestionCommandParser;
import com.notably.logic.parser.suggestion.OpenSuggestionCommandParser;
import com.notably.model.Model;

import javafx.beans.property.StringProperty;

/**
 * An implementation class of SuggestionEngine.
 */
public class SuggestionEngineImpl implements SuggestionEngine {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final List<String> COMMAND_LIST = List.of("new", "edit", "delete", "open", "help", "exit");
    private static final int CORRECTION_THRESHOLD = 2;
    private static final boolean USE_PATH_FORWARD_MATCHING = true;

    private Model model;
    private CorrectionEngine<String> commandCorrectionEngine;
    private CorrectionEngine<AbsolutePath> pathCorrectionEngine;

    public SuggestionEngineImpl(Model model) {
        this.model = model;
        commandCorrectionEngine = new StringCorrectionEngine(COMMAND_LIST, CORRECTION_THRESHOLD);
        pathCorrectionEngine = new AbsolutePathCorrectionEngine(model, CORRECTION_THRESHOLD,
                USE_PATH_FORWARD_MATCHING);

        autoUpdateInput(model.inputProperty());
    }

    @Override
    public void suggest(String userInput) {
        if (userInput.length() >= 2) {
            Optional<SuggestionCommand> suggestionCommand = parseCommand(userInput);
            suggestionCommand.ifPresent(s -> s.execute(model));
        }
    }

    /**
     * Parses the user input.
     * @param userInput The user's input.
     * @return The corresponding SuggestionCommand.
     */
    private Optional<SuggestionCommand> parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return Optional.of(new ErrorSuggestionCommand(
                    "Invalid command format. To see the list of available commands, type: help"));
        }

        String commandWord = matcher.group("commandWord");
        CorrectionResult<String> correctionResult = commandCorrectionEngine.correct(commandWord);
        if (correctionResult.getCorrectionStatus() == CorrectionStatus.FAILED) {
            return Optional.of(new ErrorSuggestionCommand(
                    "Invalid command. To see the list of available commands, type: help"));
        }
        commandWord = correctionResult.getCorrectedItems().get(0);

        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case "open":
            return new OpenSuggestionCommandParser(model, pathCorrectionEngine).parse(arguments);

        case "delete":
            return new DeleteSuggestionCommandParser(model, pathCorrectionEngine).parse(arguments);

        /*case SearchSuggestionCommand.COMMAND_WORD:
            return new SearchSuggestionCommandParser(model).parse(arguments);*/

        case "new":
            return new NewSuggestionCommandParser(model).parse(arguments);

        case "edit":
            model.setResponseText("Edit this note");
            return Optional.empty();

        case "help":
            return Optional.of(new HelpSuggestionCommand());

        case "exit":
            return Optional.of(new ExitSuggestionCommand());

        default:
            return Optional.of(new ErrorSuggestionCommand(
                    "Invalid command. To see the list of available commands, type: help"));
        }
    }

    /**
     * Generates new suggestions whenever the command input line changes.
     * @param inputProperty The user's input.
     */
    private void autoUpdateInput(StringProperty inputProperty) {
        inputProperty.addListener((observable, oldValue, newValue) -> {
            model.clearSuggestions();
            model.clearResponseText();
            suggest(newValue);
        });
    }
}
