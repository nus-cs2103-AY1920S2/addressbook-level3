package com.notably.logic.suggestion.commands;

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

public class NewSuggestionCommandTest {
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
        NewSuggestionCommand newSuggestionCommand = new NewSuggestionCommand();

        newSuggestionCommand.execute(model);

        // Expected result
        String expectedResponseText = "Create a new note";

        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }
}
