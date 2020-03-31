package com.notably.logic.commands.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

public class ErrorSuggestionCommandTest {
    private static Model model;
    private static AbsolutePath toRoot;

    @BeforeAll
    public static void setUp() {
        // Set up paths
        toRoot = AbsolutePath.fromString("/");

        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);
    }

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ErrorSuggestionCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteSuggestionCommand deleteSuggestionCommand =
            new DeleteSuggestionCommand(toRoot, toRoot.getStringRepresentation());
        assertThrows(NullPointerException.class, () -> deleteSuggestionCommand.execute(null));
    }

    @Test
    public void execute() {
        //instantiate command
        ErrorSuggestionCommand errorSuggestionCommand = new ErrorSuggestionCommand("Invalid Command");

        errorSuggestionCommand.execute(model);

        // Expected result
        String expectedResponseText = "Invalid Command";

        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }
}
