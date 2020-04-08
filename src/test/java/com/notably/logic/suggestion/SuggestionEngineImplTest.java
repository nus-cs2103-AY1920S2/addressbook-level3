package com.notably.logic.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.exceptions.InvalidPathException;
import com.notably.model.Model;

public class SuggestionEngineImplTest {
    private static Model model;
    private static SuggestionEngine suggestionEngine;

    @BeforeAll
    public static void setUp() throws InvalidPathException {
        model = SuggestionTestUtil.getModel();

        // Set up SuggestionEngine
        suggestionEngine = new SuggestionEngineImpl(model);
    }

    @Test
    public void suggest_inputLengthTooShort_suggestionsAndResponseTextCleared() {
        model.setInput("");
        assertTrue(model.getSuggestions().isEmpty());
        assertTrue(model.responseTextProperty().getValue().isEmpty());

        model.setInput("o");
        assertTrue(model.getSuggestions().isEmpty());
        assertTrue(model.responseTextProperty().getValue().isEmpty());
    }

    @Test
    public void suggest_correctedEditCommand() {
        model.setInput("edt");

        // Expected result
        String expectedResponseText = "Edit this note";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedHelpCommand() {
        model.setInput("hAlp");

        // Expected result
        String expectedResponseText = "Display a list of available commands";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }

    @Test
    public void suggest_correctedExitCommand() {
        model.setInput("ex");

        // Expected result
        String expectedResponseText = "Exit the application";
        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }
}
