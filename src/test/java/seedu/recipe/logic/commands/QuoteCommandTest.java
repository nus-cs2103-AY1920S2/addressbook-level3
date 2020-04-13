package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.testutil.TypicalQuote.QUOTE_1;
import static seedu.recipe.testutil.TypicalQuote.getTypicalQuoteBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.RecipeBook;

public class QuoteCommandTest {

    private Model model = new ModelManager(new RecipeBook(), new UserPrefs(),
            new CookedRecordBook(), new PlannedBook(), getTypicalQuoteBook());
    private Model expectedModel = new ModelManager(new RecipeBook(), new UserPrefs(),
            new CookedRecordBook(), new PlannedBook(), getTypicalQuoteBook());

    @Test
    public void execute_messageDuplicate_throwsCommandException() {
        Quote duplicateQuote = QUOTE_1;
        QuoteCommand quoteCommand = new QuoteCommand(duplicateQuote.getContent());

        assertCommandFailure(quoteCommand, model, QuoteCommand.MESSAGE_DUPLICATE_RECORD);
    }


}
