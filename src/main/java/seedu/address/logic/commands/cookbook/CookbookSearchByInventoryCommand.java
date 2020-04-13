package seedu.address.logic.commands.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.recipe.RecipeContainsInventoryIngredientsPredicate;
import seedu.address.model.recipe.RecipeInventoryIngredientsSimilarityComparator;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CookbookSearchByInventoryCommand extends CookbookSearchCommand {

    public static final String MESSAGE_USAGE =
            "\n" + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + SEARCH_INVENTORY_COMMAND + ": finds all"
                    + " recipes whose ingredient lists contain ingredients in the inventory, and displays them as a "
                    + "list with index numbers. The list is sorted by the level of similarity between the ingredients"
                    + " in the recipe and the ingredients in the inventory.\n"
                    + "Parameters: " + SEARCH_INVENTORY_COMMAND
                    + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + SEARCH_INVENTORY_COMMAND;

    public CookbookSearchByInventoryCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ReadOnlyInventory inventory = model.getInventory();
        model.sortCookbook(new RecipeInventoryIngredientsSimilarityComparator(inventory));
        model.updateFilteredCookbookRecipeList(new RecipeContainsInventoryIngredientsPredicate(inventory));
        return new CommandResult(
                String.format(MESSAGE_RECIPES_LISTED_OVERVIEW, model.getFilteredCookbookRecipeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof CookbookSearchByInventoryCommand;
    }
}
