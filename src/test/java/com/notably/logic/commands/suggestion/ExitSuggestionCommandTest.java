package com.notably.logic.commands.suggestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

public class ExitSuggestionCommandTest {
    private static Model model;

    @BeforeAll
    public static void setUp() {
        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);
    }

    @Test
    public void execute() {
        // Instantiate command
        ExitSuggestionCommand exitSuggestionCommand = new ExitSuggestionCommand();

        exitSuggestionCommand.execute(model);

        // Expected result
        String expectedResponseText = "Exit the application";

        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }
}
