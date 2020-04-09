package com.notably.logic.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.exceptions.InvalidPathException;
import com.notably.model.Model;

public class SuggestionEngineImplTest {
    private static Model model;
    private static SuggestionEngine suggestionEngine;

    private static final String EDIT_RESPONSE_TEXT = "Edit this note";
    private static final String ERROR_RESPONSE_TEXT = "\"%s\" is an invalid command format. "
            + "To see the list of available commands, type: help";
    private static final String EXIT_RESPONSE_TEXT = "Exit the application";
    private static final String HELP_RESPONSE_TEXT = "Display a list of available commands";

    @BeforeAll
    public static void setUp() throws InvalidPathException {
        model = SuggestionTestUtil.getModel();

        // Set up SuggestionEngine
        suggestionEngine = new SuggestionEngineImpl(model);
    }

    @AfterEach
    public void clearResponseText() {
        model.clearResponseText();
    }

    @Test
    public void suggest_inputLengthTooShort_suggestionsAndResponseTextCleared() {
        suggestionEngine.suggest("");
        assertTrue(model.getSuggestions().isEmpty());
        assertTrue(model.responseTextProperty().getValue().isEmpty());

        suggestionEngine.suggest("o");
        assertTrue(model.getSuggestions().isEmpty());
        assertTrue(model.responseTextProperty().getValue().isEmpty());
    }



    @Test
    public void suggest_correctedEditCommand() {
        suggestionEngine.suggest("edt");

        assertEquals(Optional.of(EDIT_RESPONSE_TEXT), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedHelpCommand() {
        suggestionEngine.suggest("hAlp");

        assertEquals(Optional.of(HELP_RESPONSE_TEXT), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedExitCommand() {
        suggestionEngine.suggest("ex");

        assertEquals(Optional.of(EXIT_RESPONSE_TEXT), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_invalidCommand_displaysErrorMessage() {
        String userInput = "randomCmd";
        suggestionEngine.suggest(userInput);

        assertEquals(Optional.of(String.format(ERROR_RESPONSE_TEXT, userInput)),
                model.responseTextProperty().getValue());
    }
}
