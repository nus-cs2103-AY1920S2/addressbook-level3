package com.notably.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class EditCommandTest {
    private static Model model;
    private static AbsolutePath currentPath;

    @BeforeEach
    public void setUp() {
        // Set up model
        BlockModel blockModel = TypicalBlockModel.getTypicalBlockModel();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

    }
    @Test
    void execute_nonNullModel_True() throws CommandException {
        final EditCommand editCommand = new EditCommand();

        editCommand.execute(model);

        assertTrue(model.blockEditableProperty().getValue());
    }

    @Test
    void execute_editCommandInRoot_throwsCannotModifyRootException() throws CommandException {
        final EditCommand editCommand = new EditCommand();

        model.setCurrentlyOpenBlock(AbsolutePath.fromString("/"));

        assertThrows(CommandException.class, () -> editCommand.execute(model));
    }
}
