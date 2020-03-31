package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
<<<<<<< HEAD
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(), getTypicalRecordBook());
        expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(), model.getRecordBook());
=======
        model = new ModelManager(getTypicalRecipeBook(), new PlannedBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRecipeBook(), new PlannedBook(), new UserPrefs());
>>>>>>> upstream/master
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
