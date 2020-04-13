package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.RecipeMatchesKeywordsPredicate;
import seedu.recipe.ui.tab.Tab;

/**
 * Finds and lists all recipes in recipe book that matches the specified arguments.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recipes that matches the "
            + "specified criteria.\n"
            + "Parameters: [<favourites> "
            + "<" + PREFIX_TIME + "time (upperbound) or time range (lowerbound-upperbound)> "
            + "<" + PREFIX_INGREDIENT_GRAIN + "grain>... "
            + "<" + PREFIX_INGREDIENT_VEGE + "vegetable>... "
            + "<" + PREFIX_INGREDIENT_PROTEIN + "protein>... "
            + "<" + PREFIX_INGREDIENT_FRUIT + "fruit>... "
            + "<" + PREFIX_INGREDIENT_OTHER + "other>... "
            + "<" + PREFIX_GOAL + "goal>...]\n"
            + "Example: " + COMMAND_WORD + " favourites "
            + PREFIX_TIME + "10-20 "
            + PREFIX_INGREDIENT_VEGE + "Kailan "
            + PREFIX_INGREDIENT_PROTEIN + "Chicken thigh "
            + PREFIX_GOAL + "Bulk like the Hulk";

    private final RecipeMatchesKeywordsPredicate predicate;
    private final Tab recipesTab = Tab.RECIPES;

    public FilterCommand(RecipeMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(predicate);
        String finalMessage = String.format(Messages.MESSAGE_RECIPES_LISTED_OVERVIEW,
                model.getFilteredRecipeList().size());
        return new CommandResult(finalMessage, false, false, recipesTab, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }

}
