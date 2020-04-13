package seedu.address.logic.commands.recipe;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;

/**
 * Represents a recipe command with hidden internal logic and the ability to be executed.
 */
public abstract class RecipeCommand extends Command {

    public static final String COMMAND_CATEGORY = "recipe";
    public static final String STEP_KEYWORD = "step";
    public static final String INGREDIENT_KEYWORD = "ingredient";
    public static final String TAG_KEYWORD = "tag";
    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " INDEX " + "add"
            + ": adds an ingredient, a step, or a tag to a recipe with the given INDEX.\n"
            + "\nParameters for adding an ingredient: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + "add" + " "
            + INGREDIENT_KEYWORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs "
            + PREFIX_INGREDIENT_QUANTITY + "12\n"
            + "\nParameters for adding a step: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_STEP_INDEX + "STEP_INDEX "
            + PREFIX_STEP_DESCRIPTION + "STEP_DESCRIPTION\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + "add" + " "
            + STEP_KEYWORD + " "
            + PREFIX_STEP_INDEX + "1 "
            + PREFIX_STEP_DESCRIPTION + "Add potatoes and water to a large pot, and bring to a boil.\n"
            + "\nParameters for adding a tag: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + "add" + " "
            + TAG_KEYWORD + " "
            + PREFIX_TAG + "Pasta\n"
            + "\n" + COMMAND_CATEGORY + " INDEX " + "remove"
            + ": removes an ingredient, a step, or a tag from a recipe with the given INDEX.\n"
            + "\nParameters for removing an ingredient: \n"
            + "INDEX (must be a valid positive integer) "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + "[" + PREFIX_INGREDIENT_QUANTITY + "QUANTITY]\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + "remove" + " "
            + PREFIX_INGREDIENT_NAME + "Eggs "
            + PREFIX_INGREDIENT_QUANTITY + "12\n"
            + "* if no quantity is specified, all instances of the specified ingredient will be removed.\n"
            + "\nParameters for removing a step: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_STEP_INDEX + "STEP_INDEX\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + "remove" + " "
            + PREFIX_STEP_INDEX + "1\n"
            + "\nParameters for removing a tag: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + "remove" + " "
            + PREFIX_TAG + "Pasta";

    /**
     * Stores the details to edit the recipe with. Each non-empty field value will replace the
     * corresponding field value of the recipe.
     */
    public static class EditRecipeDescriptor {
        private RecipeName name;
        private RecipeDescription description;
        private ObservableList<Ingredient> ingredients;
        private UniqueStepList steps;
        private Set<Tag> tags;

        public EditRecipeDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecipeDescriptor(EditRecipeDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setIngredients(toCopy.ingredients);
            setSteps(toCopy.steps);
            setTags(toCopy.tags);
        }

        /**
         * Creates and returns a {@code Recipe} with the details of {@code recipeToEdit}
         * edited with {@code editRecipeDescriptor}.
         */
        public static Recipe createEditedRecipe(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
            assert recipeToEdit != null;

            RecipeName updatedName = editRecipeDescriptor.getName()
                    .orElse(recipeToEdit.getName());
            RecipeDescription updatedDescription = editRecipeDescriptor.getDescription()
                    .orElse(recipeToEdit.getDescription());
            UniqueIngredientList updatedIngredients = new UniqueIngredientList();
            updatedIngredients.setIngredients(editRecipeDescriptor.getIngredients()
                    .orElse(recipeToEdit.getIngredients()));
            UniqueStepList updatedSteps = editRecipeDescriptor.getSteps()
                    .orElse(recipeToEdit.getSteps());
            Set<Tag> updatedTags = editRecipeDescriptor.getTags()
                    .orElse(recipeToEdit.getTags());

            return new Recipe(updatedName, updatedDescription, updatedIngredients, updatedSteps, updatedTags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, ingredients, steps, tags);
        }

        public void setName(RecipeName name) {
            this.name = name;
        }

        public Optional<RecipeName> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(RecipeDescription description) {
            this.description = description;
        }

        public Optional<RecipeDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setIngredients(ObservableList<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public Optional<ObservableList<Ingredient>> getIngredients() {
            return Optional.ofNullable(ingredients);
        }

        public void setSteps(UniqueStepList steps) {
            this.steps = steps;
        }

        public Optional<UniqueStepList> getSteps() {
            return Optional.ofNullable(steps);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecipeDescriptor)) {
                return false;
            }

            // state check
            EditRecipeDescriptor e = (EditRecipeDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription())
                    && getIngredients().equals(e.getIngredients())
                    && getSteps().equals(e.getSteps())
                    && getTags().equals(e.getTags());
        }
    }
}
