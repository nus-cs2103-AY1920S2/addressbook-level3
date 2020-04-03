package seedu.recipe.logic.commands.plan;

import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_FUTURE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;

class PlanCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null index, null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, null));

        // null index, valid date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, DATE_IN_FUTURE));

        // valid index, null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(INDEX_FIRST_RECIPE, null));
    }

}
