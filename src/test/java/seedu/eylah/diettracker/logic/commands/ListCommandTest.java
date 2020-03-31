package seedu.eylah.diettracker.logic.commands;

//import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.showFoodAtIndex;
import static seedu.eylah.diettracker.testutil.TypicalFood.getTypicalFoodBook;
//import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_FOOD;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.ModelManager;
import seedu.eylah.diettracker.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFoodBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getFoodBook(), new UserPrefs());
    }

    //@Test
    //public void execute_listIsNotFiltered_showsSameList() {
    //    assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //}
    //
    //@Test
    //public void execute_listIsFiltered_showsEverything() {
    //    showFoodAtIndex(model, INDEX_FIRST_FOOD);
    //    assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //}
}
