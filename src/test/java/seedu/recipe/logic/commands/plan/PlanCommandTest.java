package seedu.recipe.logic.commands.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_FUTURE;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.model.Date;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;

class PlanCommandTest {

    private static final Index[] VALID_INDEXES = new Index[] {INDEX_FIRST_RECIPE};

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null indexes, null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, null));

        // valid indexes, null date
        assertThrows(NullPointerException.class, () -> new PlanCommand(VALID_INDEXES, null));

        // null indexes, valid date
        assertThrows(NullPointerException.class, () -> new PlanCommand(null, DATE_TODAY));
    }

    @Test
    public void execute_planAcceptedByModel_planSuccessful() throws Exception {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), new PlannedBook(), new QuoteBook());
        Date validDate = DATE_TODAY;
        Recipe validRecipe = model.getFilteredRecipeList().get(VALID_INDEXES[0].getZeroBased());
        String successfulRecipeMessage = VALID_INDEXES[0].getOneBased() + " (" + validRecipe.getName() + ")";
        String completeSuccessMessage = String.format(PlanCommand.MESSAGE_DATE, validDate.toString())
                + String.format(PlanCommand.MESSAGE_SUCCESS, successfulRecipeMessage) + "\n";

        CommandResult commandResult = new PlanCommand(VALID_INDEXES, validDate).execute(model);

        assertEquals(completeSuccessMessage, commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicatePlan_planUnsuccessful() throws Exception {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), new PlannedBook(), new QuoteBook());
        Date validDate = DATE_TODAY;
        Recipe validRecipe = model.getFilteredRecipeList().get(VALID_INDEXES[0].getZeroBased());

        String unsuccessfulRecipeMessage = VALID_INDEXES[0].getOneBased() + " (" + validRecipe.getName() + ")";
        String completeUnsuccessfulMessage = String.format(PlanCommand.MESSAGE_DATE, validDate.toString())
                + String.format(PlanCommand.MESSAGE_DUPLICATE_PLANNED_RECIPE, unsuccessfulRecipeMessage);

        PlanCommand planCommand = new PlanCommand(VALID_INDEXES, validDate);
        planCommand.execute(model);
        CommandResult commandResult = planCommand.execute(model);

        assertEquals(completeUnsuccessfulMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Index[] validOneIndex = VALID_INDEXES;
        Index[] validTwoIndex = new Index[] {INDEX_SECOND_RECIPE};

        PlanCommand todayIndexOne = new PlanCommand(validOneIndex, DATE_TODAY);

        // same object -> returns true
        assertTrue(todayIndexOne.equals(todayIndexOne));

        // same values -> returns true
        PlanCommand todayIndexOneCopy = new PlanCommand(validOneIndex, DATE_TODAY);
        assertTrue(todayIndexOne.equals(todayIndexOneCopy));

        // different types -> returns false
        assertFalse(todayIndexOne.equals(1));

        // null -> returns false
        assertFalse(todayIndexOne.equals(null));

        // different indexes -> returns false
        PlanCommand todayIndexTwo = new PlanCommand(validTwoIndex, DATE_TODAY);
        assertFalse(todayIndexOne.equals(todayIndexTwo));

        // different date -> returns false
        PlanCommand futureIndexOne = new PlanCommand(validOneIndex, DATE_IN_FUTURE);
        assertFalse(todayIndexOne.equals(futureIndexOne));

        // different date and indexes -> returns false
        PlanCommand futureIndexTwo = new PlanCommand(validTwoIndex, DATE_IN_FUTURE);
        assertFalse(todayIndexOne.equals(futureIndexTwo));

    }


}
