package com.notably.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;
import com.notably.testutil.TypicalBlockModel;

class OpenCommandTest {
    private static Model model;

    @BeforeEach
    public void setUp() {
        // Set up model
        BlockModel blockModel = TypicalBlockModel.getTypicalBlockModel();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

    }

    @Test
    void execute_validPath_updatedCurrentDirectory() throws CommandException {
        final AbsolutePath toOpenPath = AbsolutePath.fromString("/Y2S2/CS2106");
        final OpenCommand openCommand = new OpenCommand(toOpenPath);

        openCommand.execute(model);

        assertEquals(toOpenPath, model.getCurrentlyOpenPath());

    }

    @Test
    void execute_invalidPath_throwsNoSuchBlockException() {
        final AbsolutePath toOpenPath = AbsolutePath.fromString("/NoSuchBlock");
        final OpenCommand openCommand = new OpenCommand(toOpenPath);

        assertThrows(CommandException.class, () -> openCommand.execute(model));
    }
}
