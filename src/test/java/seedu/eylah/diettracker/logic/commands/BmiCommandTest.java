package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.BMI_OBJ;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_HEIGHT;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_WEIGHT;
import static seedu.eylah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.diettracker.model.ModelStub;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.self.Bmi;

import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;

public class BmiCommandTest {

    @Test
    public void constructor_nullBmi_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BmiCommand(null, null));
        assertThrows(NullPointerException.class, () -> new BmiCommand(new Height(VALID_HEIGHT), null));
        assertThrows(NullPointerException.class, () -> new BmiCommand(null, new Weight(VALID_WEIGHT)));
    }

    @Test
    public void constructor_noArgsBmi_success() {

    }

    @Test
    public void execute_bmiAcceptedByModel_setBmiSuccessful() throws Exception {
        ModelStubBmi modelStub = new ModelStubBmi();
        Height validHeight = new Height(VALID_HEIGHT);
        Weight validWeight = new Weight(VALID_WEIGHT);
        Bmi validBmi = new Bmi(validHeight, validWeight);

        System.out.println(new BmiCommand(validHeight, validWeight));

        CommandResult commandResult = new BmiCommand(validHeight, validWeight).execute(modelStub);

        assertEquals(String.format(BmiCommand.MESSAGE_CALCULATE_BMI_SUCCESS, validBmi, validBmi.getCategory()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(BMI_OBJ.equals(new BmiCommand(new Height(VALID_HEIGHT), new Weight(VALID_WEIGHT))));

        // same object -> returns true
        assertTrue(BMI_OBJ.equals(BMI_OBJ));

        // null -> returns false
        assertFalse(BMI_OBJ.equals(null));

        // different types -> returns false
        assertFalse(BMI_OBJ.equals(5));

        // different values -> returns false
        assertFalse(BMI_OBJ.equals(new BmiCommand(new Height("164"), new Weight("36"))));
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubBmi extends ModelStub {
        private final Myself myself = new Myself();

        @Override
        public Height getHeight() {
            return myself.getHeight();
        }

        @Override
        public Weight getWeight() {
            return myself.getWeight();
        }
    }
}
