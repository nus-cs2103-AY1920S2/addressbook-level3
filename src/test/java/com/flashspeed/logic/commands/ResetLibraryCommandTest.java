package com.flashspeed.logic.commands;

import static com.flashspeed.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.model.Library;
import com.flashspeed.model.Model;
import com.flashspeed.model.ModelManager;
import com.flashspeed.model.UserPrefs;
import com.flashspeed.testutil.DeckUtils;

public class ResetLibraryCommandTest {

    @Test
    public void execute_emptyLibrary_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ResetLibraryCommand(), model, ResetLibraryCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyLibrary_success() {
        Model model = new ModelManager(DeckUtils.getTypicalLibrary(), new UserPrefs());
        Model expectedModel = new ModelManager(DeckUtils.getTypicalLibrary(), new UserPrefs());
        expectedModel.setLibrary(new Library());

        assertCommandSuccess(new ResetLibraryCommand(), model, ResetLibraryCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_inPlayMode_throwsCommandException() {
        Model model = new ModelManager(DeckUtils.getTypicalLibrary(), new UserPrefs());
        model.play(Index.fromZeroBased(0));

        CommandTestUtil.assertCommandFailure(new ResetLibraryCommand(),
                model, ResetLibraryCommand.MESSAGE_NOT_IN_VIEW_MODE);
    }
}
