package seedu.recipe.logic.parser.plan;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_PAST;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.commands.plan.PlanCommand;
import seedu.recipe.model.Date;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;

class PlanCommandParserTest {

    @Test
    public void execute_dateInPast_throwsCommandException() {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
                getTypicalRecordBook(), new PlannedBook(), new QuoteBook());
        Index[] validIndexes = getValidIndexes();
        Date invalidDate = DATE_IN_PAST;

        PlanCommand planCommand = new PlanCommand(validIndexes, invalidDate);

        assertThrows(CommandException.class, PlanCommand.MESSAGE_INVALID_DATE, () -> planCommand.execute(model));
    }

}