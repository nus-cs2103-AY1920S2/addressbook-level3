package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.MODE_GAIN;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.MODE_LOSS;
import static seedu.eylah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;

import seedu.eylah.diettracker.model.Mode;
import seedu.eylah.diettracker.model.ModelStub;
import seedu.eylah.diettracker.model.Myself;

public class ModeCommandTest {

    @Test
    public void constructor_nullMode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModeCommand(null));
    }

    @Test
    public void execute_modeAcceptedByModel_setModeSuccessful() throws Exception {
        ModeCommandTest.ModelStubAcceptingModeAdded modelStub = new ModelStubAcceptingModeAdded();
        Mode validMode = Mode.LOSS;

        CommandResult commandResult = new ModeCommand(validMode).execute(modelStub);

        assertEquals(String.format(ModeCommand.MESSAGE_SUCCESS, validMode),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(MODE_GAIN.equals(new ModeCommand(Mode.GAIN)));

        // same object -> returns true
        assertTrue(MODE_GAIN.equals(MODE_GAIN));

        // null -> returns false
        assertFalse(MODE_GAIN.equals(null));

        // different types -> returns false
        assertFalse(MODE_GAIN.equals(5));

        // different modes -> returns false
        assertFalse(MODE_GAIN.equals(MODE_LOSS));
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingModeAdded extends ModelStub {
        private final Myself myself = new Myself();

        @Override
        public void setMode(Mode mode) {
            myself.setMode(mode);
        }
    }
}
