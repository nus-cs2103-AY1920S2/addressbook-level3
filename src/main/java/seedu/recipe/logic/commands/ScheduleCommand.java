package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.Date;
import seedu.recipe.model.recipe.Recipe;

/**
 * Schedules a recipe to a date.
 */

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Plans a recipe to be cooked on a certain date. "
            + "Parameters: "
            + "RECIPE_INDEX "
            + PREFIX_DATE + "YYYY-MM-DD \n"
            + "Example: " + COMMAND_WORD + " "
            + "3 "
            + PREFIX_DATE + "2020-03-16";

    public static final String MESSAGE_SUCCESS = "Recipe planned: %1$s, %2$s";

    private final Index index;
    private final Date atDate;

    /**
     * Creates an ScheduleCommand to set the specified {@code Recipe} on a certain date
     */
    public ScheduleCommand(Index index, Date date) {
        requireNonNull(index);
        requireNonNull(date);
        this.index = index;
        atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToPlan = lastShownList.get(index.getZeroBased());

        model.planRecipe(recipeToPlan, atDate);
        model.commitRecipeBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, recipeToPlan.toString(), atDate.toString()));
    }

}
