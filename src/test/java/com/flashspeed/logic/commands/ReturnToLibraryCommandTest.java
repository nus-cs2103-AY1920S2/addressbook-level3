package com.flashspeed.logic.commands;

import static com.flashspeed.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.ModelManager;
import com.flashspeed.model.UserPrefs;
import com.flashspeed.testutil.DeckUtils;

public class ReturnToLibraryCommandTest {

    @Test
    public void execute_return_success() throws Exception {
        Model model = new ModelManager(DeckUtils.getTypicalLibrary(), new UserPrefs());
        model.selectDeck(Index.fromZeroBased(0));

        CommandResult commandResult = new ReturnToLibraryCommand().execute(model);

        assertEquals(String.format(ReturnToLibraryCommand.MESSAGE_SUCCESS),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_inPlayMode_throwsCommandException() {
        Model model = new ModelManager(DeckUtils.getTypicalLibrary(), new UserPrefs());
        model.play(Index.fromZeroBased(0));

        ReturnToLibraryCommand returnToLibraryCommand = new ReturnToLibraryCommand();

        assertThrows(CommandException.class,
                ReturnToLibraryCommand.MESSAGE_NOT_IN_VIEW_MODE, () -> returnToLibraryCommand.execute(model));
    }

    @Test
    public void execute_alreadyInLibraryView_throwsCommandException() {
        Model model = new ModelManager();

        assertCommandFailure(new ReturnToLibraryCommand(), model, ReturnToLibraryCommand.MESSAGE_ALREADY_IN_LIBRARY);
    }
}
