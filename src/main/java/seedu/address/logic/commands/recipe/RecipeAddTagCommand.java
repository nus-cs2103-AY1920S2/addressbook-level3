package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to a recipe.
 */
public class RecipeAddTagCommand extends RecipeAddCommand {

    public static final String MESSAGE_SUCCESS = "New tag added: %1$s";

    private final Index recipeIndex;
    private final Tag toAdd;

    public RecipeAddTagCommand(Index recipeIndex, Tag toAdd) {
        requireNonNull(recipeIndex);
        requireNonNull(toAdd);
        this.recipeIndex = recipeIndex;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (recipeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        assert recipeIndex.getZeroBased() < lastShownList.size();
        Recipe recipeToEdit = lastShownList.get(recipeIndex.getZeroBased());
        Set<Tag> targetTagSet = recipeToEdit.getTags();

        if (!targetTagSet.add(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TAG);
        }

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setTags(targetTagSet);
        Recipe editedRecipe = EditRecipeDescriptor.createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setCookbookRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredCookbookRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeAddTagCommand // instanceof handles nulls
                && toAdd.equals(((RecipeAddTagCommand) other).toAdd));
    }
}
