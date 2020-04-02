package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.util.CollectionUtil;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Vegetable;

/**
 * Edits the details of an existing recipe in the recipe book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recipe identified "
            + "by the index number used in the displayed recipe list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_INGREDIENT_GRAIN + "GRAIN]... "
            + "[" + PREFIX_INGREDIENT_VEGE + "VEGETABLE]... "
            + "[" + PREFIX_INGREDIENT_PROTEIN + "PROTEIN]... "
            + "[" + PREFIX_INGREDIENT_FRUIT + "FRUIT]... "
            + "[" + PREFIX_INGREDIENT_OTHER + "OTHER]... "
            + "[" + PREFIX_STEP + "STEP]... "
            + "[" + PREFIX_GOAL + "GOAL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "10 "
            + PREFIX_INGREDIENT_VEGE + "Insert new vegetable here "
            + PREFIX_INGREDIENT_PROTEIN + "Inser t new protein-rich ingredient here "
            + PREFIX_STEP + "Insert new step here "
            + PREFIX_GOAL + "Insert new goal here ";

    public static final String MESSAGE_EDIT_RECIPE_SUCCESS = "Edited Recipe: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book.";
    public static final String MESSAGE_CANNOT_DELETE_ALL_INGREDIENTS = "Cannot delete all ingredients!";

    private final Index index;
    private final EditRecipeDescriptor editRecipeDescriptor;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public EditCommand(Index index, EditRecipeDescriptor editRecipeDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecipeDescriptor);

        this.index = index;
        this.editRecipeDescriptor = new EditRecipeDescriptor(editRecipeDescriptor);
        this.commandType = CommandType.MAIN;
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

        if (editedRecipe.getTotalNumberOfIngredients() <= 0) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_ALL_INGREDIENTS);
        }

        if (!recipeToEdit.isSameRecipe(editedRecipe) && model.hasRecipe(editedRecipe)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);
        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe));
    }

    /**
     * Creates and returns a {@code Recipe} with the details of {@code recipeToEdit}
     * edited with {@code editRecipeDescriptor}.
     */
    protected static Recipe createEditedRecipe(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
        assert recipeToEdit != null;

        boolean isFavourite = recipeToEdit.isFavourite();
        Name updatedName = editRecipeDescriptor.getName().orElse(recipeToEdit.getName());
        Time updatedTime = editRecipeDescriptor.getTime().orElse(recipeToEdit.getTime());
        List<Step> updatedStep = editRecipeDescriptor.getSteps().orElse(recipeToEdit.getSteps());
        Set<Goal> updatedGoals = editRecipeDescriptor.getGoals().orElse(recipeToEdit.getGoals());
        Set<Grain> updatedGrains = editRecipeDescriptor.getGrains().orElse(recipeToEdit.getGrains());
        Set<Vegetable> updatedVegetables = editRecipeDescriptor.getVegetables().orElse(recipeToEdit.getVegetables());
        Set<Protein> updatedProteins = editRecipeDescriptor.getProteins().orElse(recipeToEdit.getProteins());
        Set<Fruit> updatedFruits = editRecipeDescriptor.getFruits().orElse(recipeToEdit.getFruits());
        Set<Other> updatedOthers = editRecipeDescriptor.getOthers().orElse(recipeToEdit.getOthers());

        return new Recipe(updatedName, updatedTime,
                updatedGrains, updatedVegetables, updatedProteins, updatedFruits, updatedOthers,
                updatedStep, updatedGoals, isFavourite);
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
        private Set<Grain> grains;
        private Set<Vegetable> vegetables;
        private Set<Protein> proteins;
        private Set<Fruit> fruits;
        private Set<Other> others;

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
            setGrains(toCopy.grains);
            setVegetables(toCopy.vegetables);
            setProteins(toCopy.proteins);
            setFruits(toCopy.fruits);
            setOthers(toCopy.others);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, time, grains, vegetables, proteins, fruits, others, steps, goals);
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
         * Sets {@code grains} to this object's {@code grains}.
         * A defensive copy of {@code grains} is used internally.
         */
        public void setGrains(Set<Grain> grains) {
            this.grains = (grains != null) ? new TreeSet<>(grains) : null;
        }

        /**
         * Returns an unmodifiable grains set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code grains} is null.
         */
        public Optional<Set<Grain>> getGrains() {
            return (grains != null) ? Optional.of(Collections.unmodifiableSet(grains)) : Optional.empty();
        }

        /**
         * Sets {@code vegetables} to this object's {@code vegetables}.
         * A defensive copy of {@code vegetables} is used internally.
         */
        public void setVegetables(Set<Vegetable> vegetables) {
            this.vegetables = (vegetables != null) ? new TreeSet<>(vegetables) : null;
        }

        /**
         * Returns an unmodifiable vegetables set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code vegetables} is null.
         */
        public Optional<Set<Vegetable>> getVegetables() {
            return (vegetables != null) ? Optional.of(Collections.unmodifiableSet(vegetables)) : Optional.empty();
        }

        /**
         * Sets {@code proteins} to this object's {@code proteins}.
         * A defensive copy of {@code proteins} is used internally.
         */
        public void setProteins(Set<Protein> proteins) {
            this.proteins = (proteins != null) ? new TreeSet<>(proteins) : null;
        }

        /**
         * Returns an unmodifiable proteins set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code proteins} is null.
         */
        public Optional<Set<Protein>> getProteins() {
            return (proteins != null) ? Optional.of(Collections.unmodifiableSet(proteins)) : Optional.empty();
        }

        /**
         * Sets {@code fruits} to this object's {@code fruits}.
         * A defensive copy of {@code fruits} is used internally.
         */
        public void setFruits(Set<Fruit> fruits) {
            this.fruits = (fruits != null) ? new TreeSet<>(fruits) : null;
        }

        /**
         * Returns an unmodifiable fruits set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code fruits} is null.
         */
        public Optional<Set<Fruit>> getFruits() {
            return (fruits != null) ? Optional.of(Collections.unmodifiableSet(fruits)) : Optional.empty();
        }

        /**
         * Sets {@code others} to this object's {@code others}.
         * A defensive copy of {@code others} is used internally.
         */
        public void setOthers(Set<Other> others) {
            this.others = (others != null) ? new TreeSet<>(others) : null;
        }

        /**
         * Returns an unmodifiable others set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code others} is null.
         */
        public Optional<Set<Other>> getOthers() {
            return (others != null) ? Optional.of(Collections.unmodifiableSet(others)) : Optional.empty();
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
            return getName().equals(e.getName())
                    && getTime().equals(e.getTime())
                    && getGrains().equals(e.getGrains())
                    && getVegetables().equals(e.getVegetables())
                    && getProteins().equals(e.getProteins())
                    && getFruits().equals(e.getFruits())
                    && getOthers().equals(e.getOthers())
                    && getSteps().equals(e.getSteps())
                    && getGoals().equals(e.getGoals());
        }
    }
}
