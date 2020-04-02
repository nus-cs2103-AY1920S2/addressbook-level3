package seedu.recipe.logic.commands.plan;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;

class PlanCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null index and null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, null));

        // valid index, null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(INDEX_FIRST_RECIPE, null));


    }

/*    @Test
    void execute() {
    }*/
}