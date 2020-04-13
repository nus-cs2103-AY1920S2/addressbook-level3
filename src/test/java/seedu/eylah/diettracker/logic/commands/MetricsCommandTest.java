package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
import static seedu.eylah.diettracker.testutil.TypicalMyself.getTypicalMyself;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class MetricsCommandTest {

    private DietModel model;
    private DietModel expectedModel;

    @BeforeEach
    public void setUp() {
        model = new DietModelManager(getTypicalFoodBook(), new UserPrefs(), getTypicalMyself());
        expectedModel = new DietModelManager(model.getFoodBook(), new UserPrefs(), model.getMyself());
    }

    @Test
    public void execute_metricsShown() {
        assertCommandSuccess(new MetricsCommand(), model,
                "Your metrics are as follows:\n"
                + "\n"
                + "    Height: You have not stored your own height yet! Type 'height <<insert height here>>'"
                + " to store your height!\n"
                + "    Weight: You have not stored your own weight yet! Type 'weight <<insert weight here>>'"
                + " to store your weight!\n"
                + "    Dieting Mode: MAINTAIN\n"
                + "Your metrics are shown.\n",
                expectedModel);
    }

    @Test
    public void equals() {
        MetricsCommand metricsFirstCommand = new MetricsCommand();

        // same object -> returns true
        assertTrue(metricsFirstCommand.equals(metricsFirstCommand));

        // different types -> returns false
        assertFalse(metricsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(metricsFirstCommand.equals(null));
    }
}
