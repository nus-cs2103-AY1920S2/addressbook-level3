package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.HEIGHT_OBJ;
import static seedu.eylah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;

import seedu.eylah.diettracker.model.ModelStub;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.self.Height;


public class HeightCommandTest {

    @Test
    public void constructor_nullHeight_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HeightCommand(null));
    }

    @Test
    public void execute_heightAcceptedByModel_setHeightSuccessful() throws Exception {
        HeightCommandTest.ModelStubAcceptingHeightAdded modelStub = new ModelStubAcceptingHeightAdded();
        Height validHeight = new Height("160");

        CommandResult commandResult = new HeightCommand(validHeight).execute(modelStub);

        assertEquals(String.format(HeightCommand.MESSAGE_ADD_HEIGHT_SUCCESS, validHeight),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(HEIGHT_OBJ.equals(new HeightCommand(new Height("170.2"))));

        // same object -> returns true
        assertTrue(HEIGHT_OBJ.equals(HEIGHT_OBJ));

        // null -> returns false
        assertFalse(HEIGHT_OBJ.equals(null));

        // different types -> returns false
        assertFalse(HEIGHT_OBJ.equals(5));

        // different values -> returns false
        assertFalse(HEIGHT_OBJ.equals(new HeightCommand(new Height("165.5"))));
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingHeightAdded extends ModelStub {

        private final Myself myself = new Myself();

        @Override
        public void setHeight(Height height) {
            myself.setHeight(height);
        }
    }
}
