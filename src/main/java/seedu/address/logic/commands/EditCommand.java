package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.goal.Goal;

import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;

import seedu.address.model.recipe.ingredient.Ingredient;

/**
 * Edits the details of an existing recipe in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recipe identified "
            + "by the index number used in the displayed recipe list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_INGREDIENT_GRAIN + "GRAIN]..."
            + "[" + PREFIX_INGREDIENT_VEGE + "VEGETABLE]..."
            + "[" + PREFIX_INGREDIENT_PROTEIN + "PROTEIN]..."
            + "[" + PREFIX_INGREDIENT_OTHER + "OTHER]..."
            + "[" + PREFIX_STEP + "STEP]... "
            + "[" + PREFIX_GOAL + "GOAL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "10 "
            + PREFIX_INGREDIENT_VEGE + "Insert new vegetable here."
            + PREFIX_INGREDIENT_PROTEIN + "Insert new protein-rich ingredient here."
            + PREFIX_STEP + "Insert new step here."
            + PREFIX_GOAL + "Insert new goal here.";

    public static final String MESSAGE_EDIT_RECIPE_SUCCESS = "Edited Recipe: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book.";

    private final Index index;
    private final EditRecipeDescriptor editRecipeDescriptor;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public EditCommand(Index index, EditRecipeDescriptor editRecipeDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecipeDescriptor);

        this.index = index;
        this.editRecipeDescriptor = new EditRecipeDescriptor(editRecipeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());
        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);

        if (!recipeToEdit.isSameRecipe(editedRecipe) && model.hasRecipe(editedRecipe)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe));
    }

    /**
     * Creates and returns a {@code Recipe} with the details of {@code recipeToEdit}
     * edited with {@code editRecipeDescriptor}.
     */
    private static Recipe createEditedRecipe(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
        assert recipeToEdit != null;

        Name updatedName = editRecipeDescriptor.getName().orElse(recipeToEdit.getName());
        Time updatedTime = editRecipeDescriptor.getTime().orElse(recipeToEdit.getTime());
        List<Step> updatedStep = editRecipeDescriptor.getSteps().orElse(recipeToEdit.getSteps());
        Set<Goal> updatedGoals = editRecipeDescriptor.getGoals().orElse(recipeToEdit.getGoals());
        Set<Ingredient> updatedIngredients = editRecipeDescriptor.getIngredients()
                .orElse(recipeToEdit.getIngredients());

        return new Recipe(updatedName, updatedTime, updatedIngredients, updatedStep, updatedGoals);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editRecipeDescriptor.equals(e.editRecipeDescriptor);
    }

    /**
     * Stores the details to edit the recipe with. Each non-empty field value will replace the
     * corresponding field value of the recipe.
     */
    public static class EditRecipeDescriptor {
        private Name name;
        private Time time;
        private List<Step> steps;
        private Set<Goal> goals;
        private Set<Ingredient> ingredients;

        public EditRecipeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code goals} is used internally.
         */
        public EditRecipeDescriptor(EditRecipeDescriptor toCopy) {
            setName(toCopy.name);
            setTime(toCopy.time);
            setSteps(toCopy.steps);
            setGoals(toCopy.goals);
            setIngredients(toCopy.ingredients);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, time, ingredients, steps, goals);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        /**
         * Sets {@code ingredients} to this object's {@code ingredients}.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public void setIngredients(Set<Ingredient> ingredients) {
            this.ingredients = (ingredients != null) ? new TreeSet<>(ingredients) : null;
        }

        /**
         * Returns an unmodifiable ingredient set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code ingredients} is null.
         */
        public Optional<Set<Ingredient>> getIngredients() {
            return (ingredients != null) ? Optional.of(Collections.unmodifiableSet(ingredients)) : Optional.empty();
        }

        /**
         * Sets {@code steps} to this object's {@code steps}.
         * A defensive copy of {@code steps} is used internally.
         */
        public void setSteps(List<Step> steps) {
            this.steps = (steps != null) ? new ArrayList<>(steps) : null;
        }

        /**
         * Returns an unmodifiable steps list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code steps} is null.
         */
        public Optional<List<Step>> getSteps() {
            return (steps != null) ? Optional.of(Collections.unmodifiableList(steps)) : Optional.empty();
        }

        /**
         * Sets {@code goals} to this object's {@code goals}.
         * A defensive copy of {@code goals} is used internally.
         */
        public void setGoals(Set<Goal> goals) {
            this.goals = (goals != null) ? new HashSet<>(goals) : null;
        }

        /**
         * Returns an unmodifiable goal set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code goals} is null.
         */
        public Optional<Set<Goal>> getGoals() {
            return (goals != null) ? Optional.of(Collections.unmodifiableSet(goals)) : Optional.empty();
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
            System.out.println("Ingredients test: " + getIngredients().equals(e.getIngredients()));
            return getName().equals(e.getName())
                    && getTime().equals(e.getTime())
                    && getIngredients().equals(e.getIngredients())
                    && getSteps().equals(e.getSteps())
                    && getGoals().equals(e.getGoals());
        }
    }
}
