package com.notably.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.Block;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

class NewCommandTest {
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
    void execute_validInput_updatedDatastructure() {
        final Block currentBlock = (new BlockImpl(new Title("CS2103")));


        final AbsolutePath expectedPath = AbsolutePath.fromString("/CS2103");

        model.addBlockToCurrentPath(currentBlock);

        assertTrue(model.hasPath(expectedPath));
    }

    @Test
    void execute_invalidInputDuplicateTitle_throwsDuplicateBlockException() {
        final Block filler = (new BlockImpl(new Title("CS2103")));
        final Block duplicateBlock = (new BlockImpl(new Title("CS2103")));
        model.addBlockToCurrentPath(filler);

        assertThrows(CommandException.class, () -> model.addBlockToCurrentPath(duplicateBlock));
    }
}
