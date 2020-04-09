package com.notably.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.commons.path.RelativePath;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockModel;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;
import com.notably.testutil.TypicalBlockModel;

class DeleteCommandTest {
    private static Model model;
    private static AbsolutePath toDeletePath;

    @BeforeEach
    public void setUp() {
        // Set up model
        BlockModel blockModel = TypicalBlockModel.getTypicalBlockModel();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

    }
    @Test
    void execute_validAbsolutePathWithNoChildren_blockSuccessfullyDeleted() throws CommandException {
        final AbsolutePath toDeletePath = TypicalBlockModel.PATH_TO_CS2106_TUTORIAL_1;
        final DeleteCommand deleteCommand = new DeleteCommand(toDeletePath);

        deleteCommand.execute(model);

        assertFalse(model.hasPath(toDeletePath));

    }

    @Test
    void execute_validRelativePathWithNoChildren_blockSuccessfullyDeleted() throws CommandException {
        final RelativePath toDeletePath = RelativePath.fromString("CS2106/Lecture Notes");
        final DeleteCommand deleteCommand = new DeleteCommand(toDeletePath);

        deleteCommand.execute(model);

        assertFalse(model.hasPath(toDeletePath.toAbsolutePath(TypicalBlockModel.INITIAL_OPEN_PATH)));

    }

    @Test
    void execute_validAbsolutePathWithChildren_blockSuccessfullyDeleted() throws CommandException {
        final AbsolutePath toDeletePath = TypicalBlockModel.PATH_TO_CS2106;
        final DeleteCommand deleteCommand = new DeleteCommand(toDeletePath);

        final AbsolutePath expectedChildrenDeletion1 = TypicalBlockModel.PATH_TO_CS2106_LECTURES;
        final AbsolutePath expectedChildrenDeletion2 = TypicalBlockModel.PATH_TO_CS2106_TUTORIALS;
        final AbsolutePath expectedChildrenDeletion3 = TypicalBlockModel.PATH_TO_CS2106_TUTORIAL_1;

        deleteCommand.execute(model);

        assertFalse(model.hasPath(toDeletePath));
        assertFalse(model.hasPath(expectedChildrenDeletion1));
        assertFalse(model.hasPath(expectedChildrenDeletion2));
        assertFalse(model.hasPath(expectedChildrenDeletion3));
    }

    @Test
    void execute_deleteRoot_throwsCommandException() {
        final AbsolutePath rootPath = AbsolutePath.fromString("/");
        final DeleteCommand deleteCommand = new DeleteCommand(rootPath);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    void execute_deleteNoneExistingBlockAbsolutePath_throwsCommandException() {
        final AbsolutePath rootPath = AbsolutePath.fromString("/NonExisting");
        final DeleteCommand deleteCommand = new DeleteCommand(rootPath);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    void execute_deleteNoneExistingBlockRelativePath_throwsCommandException() {
        final RelativePath rootPath = RelativePath.fromString("./CS2103T/NonExisting");
        final DeleteCommand deleteCommand = new DeleteCommand(rootPath);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }
}
