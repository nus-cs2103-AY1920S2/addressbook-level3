package seedu.eylah.diettracker.logic.commands;

import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
import static seedu.eylah.diettracker.testutil.TypicalMyself.getTypicalMyself;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_FOOD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private DietModel model;
    private DietModel expectedModel;

    @BeforeEach
    public void setUp() {
        model = new DietModelManager(getTypicalFoodBook(), new UserPrefs(), getTypicalMyself());
        expectedModel = new DietModelManager(model.getFoodBook(), new UserPrefs(), model.getMyself());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand("-a"), model,
                "These are all food you have ever consumed!\n"
                + "\n"
                + "1. Mushroom Pasta Calories: 1200 At: 2020-03-25 Tags: [italian]\n"
                + "2. Chicken Burger Calories: 300 At: 2020-03-25 Tags: [western][fastfood]\n"
                + "3. French Fries Calories: 450 At: 2020-03-25\n"
                + "4. Vanilla Latte Calories: 300 At: 2020-03-25 Tags: [drinks]\n"
                + "5. Coke Calories: 1200 At: 2020-03-25\n"
                + "6. BBQ Pizza Calories: 50 At: 2020-03-25\n"
                + "7. Hotdog Calories: 320 At: 2020-03-25\n"
                + "\n"
                + "Total Calorie Intake : 3820\n"
                + "All foods over period based on input tag has been listed.\n",
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);
        assertCommandSuccess(new ListCommand("-a"), model,
                "These are all food you have ever consumed!\n"
                + "\n"
                + "1. Mushroom Pasta Calories: 1200 At: 2020-03-25 Tags: [italian]\n"
                + "2. Chicken Burger Calories: 300 At: 2020-03-25 Tags: [western][fastfood]\n"
                + "3. French Fries Calories: 450 At: 2020-03-25\n"
                + "4. Vanilla Latte Calories: 300 At: 2020-03-25 Tags: [drinks]\n"
                + "5. Coke Calories: 1200 At: 2020-03-25\n"
                + "6. BBQ Pizza Calories: 50 At: 2020-03-25\n"
                + "7. Hotdog Calories: 320 At: 2020-03-25\n"
                + "\n"
                + "Total Calorie Intake : 3820\n"
                + "All foods over period based on input tag has been listed.\n",
                expectedModel);
    }
}
