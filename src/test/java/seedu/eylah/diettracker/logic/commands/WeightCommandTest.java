package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.WEIGHT_OBJ;
import static seedu.eylah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;

import seedu.eylah.diettracker.model.ModelStub;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.self.Weight;


public class WeightCommandTest {

    @Test
    public void constructor_nullWeight_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeightCommand(null));
    }

    @Test
    public void execute_weightAcceptedByModel_setWeightSuccessful() throws Exception {
        WeightCommandTest.ModelStubAcceptingWeightAdded modelStub = new ModelStubAcceptingWeightAdded();
        Weight validWeight = new Weight("50");

        CommandResult commandResult = new WeightCommand(validWeight).execute(modelStub);

        assertEquals(String.format(WeightCommand.MESSAGE_ADD_WEIGHT_SUCCESS, validWeight),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(WEIGHT_OBJ.equals(new WeightCommand(new Weight("65.7"))));

        // same object -> returns true
        assertTrue(WEIGHT_OBJ.equals(WEIGHT_OBJ));

        // null -> returns false
        assertFalse(WEIGHT_OBJ.equals(null));

        // different types -> returns false
        assertFalse(WEIGHT_OBJ.equals(5));

        // different values -> returns false
        assertFalse(WEIGHT_OBJ.equals(new WeightCommand(new Weight("72.3"))));
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingWeightAdded extends ModelStub {
        private final Myself myself = new Myself();

        @Override
        public void setWeight(Weight weight) {
            myself.setWeight(weight);
        }
    }
}
