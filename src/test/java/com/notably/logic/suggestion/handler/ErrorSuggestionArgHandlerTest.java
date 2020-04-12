package com.notably.logic.suggestion.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.logic.suggestion.SuggestionTestUtil;
import com.notably.logic.suggestion.generator.SuggestionGenerator;
import com.notably.model.Model;

public class ErrorSuggestionArgHandlerTest {
    private static ErrorSuggestionArgHandler errorSuggestionArgHandler;
    private static Model model;

    private static final String ERROR_MESSAGE = "\"%s\" is an invalid command format. "
            + "To see the list of available commands, type: help";

    @BeforeAll
    public static void setUp() {
        model = SuggestionTestUtil.getModel();

        errorSuggestionArgHandler = new ErrorSuggestionArgHandler(model);
    }

    @Test
    public void parse() {
        String arg = "opensesame";

        Optional<? extends SuggestionGenerator> command = errorSuggestionArgHandler.handleArg(arg);

        assertEquals(Optional.of(String.format(ERROR_MESSAGE, arg)),
                model.responseTextProperty().getValue());
    }
}
