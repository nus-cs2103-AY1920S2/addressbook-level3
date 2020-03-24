package com.notably.logic.suggestion.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

public class NewSuggestionCommandTest {
    private static AbsolutePath toRoot;
    private static Model model;
    private static NewSuggestionCommand newSuggestionCommand;

    @Test
    public void execute_responseText_test() {
        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

        // Set up path
        AbsolutePath toRoot = AbsolutePath.fromString("/");

        // Instantiate command
        newSuggestionCommand = new NewSuggestionCommand(toRoot);

        newSuggestionCommand.execute(model);

        // Expected result
        String expectedResponseText = "Create a new note";

        assertEquals(Optional.of(expectedResponseText), model.responseTextProperty().getValue());
    }
}
