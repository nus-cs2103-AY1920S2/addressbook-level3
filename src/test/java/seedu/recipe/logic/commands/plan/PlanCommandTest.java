package seedu.recipe.logic.commands.plan;

import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;

class PlanCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null indexes, null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, null));

        // valid indexes, null date
        Index[] validIndexArray = new Index[1];
        validIndexArray[0] = INDEX_FIRST_RECIPE;
        assertThrows(NullPointerException.class, () -> new PlanCommand(validIndexArray, null));

        // null indexes, valid date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, DATE_TODAY));
    }

}
