package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.RecipeMatchesKeywordsPredicate;

/**
 * Finds and lists all recipes in recipe book that matches the specified arguments.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recipes that matches the "
            + "specified arguments (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: favourites "
            + "[" + PREFIX_TIME + "TIME (upperbound) or TIME RANGE (lowerbound-upperbound)] "
            + "[" + PREFIX_INGREDIENT_GRAIN + "GRAIN]... "
            + "[" + PREFIX_INGREDIENT_VEGE + "VEGETABLE]... "
            + "[" + PREFIX_INGREDIENT_PROTEIN + "PROTEIN]... "
            + "[" + PREFIX_INGREDIENT_OTHER + "OTHER]... "
            + "[" + PREFIX_GOAL + "GOAL]...\n"
            + "Example: " + COMMAND_WORD + " favourites "
            + PREFIX_TIME + "10-20 "
            + PREFIX_INGREDIENT_VEGE + "Search for vegetable "
            + PREFIX_INGREDIENT_PROTEIN + "Search for protein-rich ingredient "
            + PREFIX_GOAL + "Search for goal ";

    private final RecipeMatchesKeywordsPredicate predicate;

    public FilterCommand(RecipeMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RECIPES_LISTED_OVERVIEW, model.getFilteredRecipeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }

}
