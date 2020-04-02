package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Date;
import seedu.recipe.model.Model;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Adds cooked recipes identified by index into cookedRecordsBook.
 */
public class CookedCommand extends Command {
    public static final String COMMAND_WORD = "cooked";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Indicate that the recipe has been cooked at the current time\n"
            + "Parameters: INDEX NUMBER(s) (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DUPLICATE_RECORD = "This recipe has already been added!";

    private final Index[] targetIndex;
    private final Tab goalsTab = Tab.GOALS;
    private final CommandType commandType;

    public CookedCommand(Index[] targetIndex) {
        this.targetIndex = targetIndex;
        this.commandType = CommandType.GOALS;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> mostRecentList = model.getFilteredRecipeList();
        StringBuilder sb = new StringBuilder().append("Cooked ");

        for (int i = 0; i < targetIndex.length; i++) {
            if (targetIndex[i].getZeroBased() >= mostRecentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
            }
            Recipe recipeCooked = mostRecentList.get(targetIndex[i].getZeroBased());
            Record record = new Record(recipeCooked.getName(), new Date(), recipeCooked.getGoals());
            if (model.hasRecord(record)) {
                throw new CommandException(MESSAGE_DUPLICATE_RECORD);
            }
            model.addRecord(record);
            if (i == targetIndex.length - 1 && targetIndex.length != 1) {
                sb.append(" and ");
            }
            sb.append(recipeCooked.getName().toString());
            if (i < targetIndex.length - 2) {
                sb.append(", ");
            }
        }
        sb.append("!");
        model.commitBook(commandType);
        return new CommandResult(sb.toString(), false, goalsTab, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookedCommand // instanceof handles nulls
                && Arrays.equals(targetIndex, ((CookedCommand) other).targetIndex)); // state check
    }

}
