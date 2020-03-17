package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;

import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Ingredient;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String time} into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String step} into an {@code Step}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code step} is invalid.
     */
    public static Step parseStep(String step) throws ParseException {
        requireNonNull(step);
        String trimmedStep = step.trim();
        if (!Step.isValidStep(trimmedStep)) {
            throw new ParseException(Step.MESSAGE_CONSTRAINTS);
        }
        return new Step(trimmedStep);
    }

    /**
     * Parses {@code Collection<String> steps} into a {@code List<Step>}.
     */
    public static List<Step> parseSteps(Collection<String> steps) throws ParseException {
        requireNonNull(steps);
        if (steps.contains("")) {
            throw new ParseException(Step.MESSAGE_CONSTRAINTS);
        }
        final List<Step> stepsList = new ArrayList<>();
        for (String step : steps) {
            Step nextStep = parseStep(step);
            if (!stepsList.contains(nextStep)) {
                stepsList.add(nextStep);
            }
        }
        return stepsList;
    }

    /**
     * Parses a {@code String goal} into a {@code Goal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code goal} is invalid.
     */
    public static Goal parseGoal(String goal) throws ParseException {
        requireNonNull(goal);
        String trimmedGoal = goal.trim();
        if (!Goal.isValidGoalName(trimmedGoal)) {
            throw new ParseException(Goal.MESSAGE_CONSTRAINTS);
        }
        return new Goal(trimmedGoal);
    }

    /**
     * Parses {@code Collection<String> goals} into a {@code Set<Goal>}.
     */
    public static Set<Goal> parseGoals(Collection<String> goals) throws ParseException {
        requireNonNull(goals);
        final Set<Goal> goalSet = new HashSet<>();
        for (String goalName : goals) {
            goalSet.add(parseGoal(goalName));
        }
        return goalSet;
    }

    /**
     * Parses a {@code String grain} into a {@code Grain}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grain} is invalid.
     */
    public static Grain parseGrain(String grain) throws ParseException {
        requireNonNull(grain);
        String[] splitFields = grain.split(",");

        if (splitFields.length != 2) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }
        String trimmedGrainName = splitFields[1].trim();
        String trimmedGrainQuantity = splitFields[0].trim();

        if (!Ingredient.isValidIngredientName(trimmedGrainName)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }

        double grainQuantity = Double.parseDouble(trimmedGrainQuantity);
        return new Grain(trimmedGrainName, grainQuantity);
    }

    /**
     * Parses {@code Collection<String> grains} and adds them to the {@code Set<Ingredient>} ingredientSet.
     */
    public static Set<Ingredient> parseGrains(Collection<String> grains, Set<Ingredient> ingredientSet)
            throws ParseException {
        if (ingredientSet == null || ingredientSet.isEmpty()) {
            ingredientSet = new TreeSet<>();
        }
        requireNonNull(grains);
        for (String grain : grains) {
            ingredientSet.add(parseGrain(grain));
        }
        return ingredientSet;
    }

    /**
     * Parses a {@code String vegetable} into a {@code Vegetable}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code vegetable} is invalid.
     */
    public static Vegetable parseVegetable(String vegetable) throws ParseException {
        requireNonNull(vegetable);
        String[] splitFields = vegetable.split(",");
        if (splitFields.length != 2) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }

        String trimmedVegetableName = splitFields[1].trim();
        String trimmedVegetableQuantity = splitFields[0].trim();

        if (!Ingredient.isValidIngredientName(trimmedVegetableName)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }

        double vegetableQuantity = Double.parseDouble(trimmedVegetableQuantity);
        return new Vegetable(trimmedVegetableName, vegetableQuantity);
    }

    /**
     * Parses {@code Collection<String> vegetables} and adds them to the {@code Set<Ingredient>} ingredientSet.
     */
    public static Set<Ingredient> parseVegetables(Collection<String> vegetables, Set<Ingredient> ingredientSet)
            throws ParseException {
        if (ingredientSet == null || ingredientSet.isEmpty()) {
            ingredientSet = new TreeSet<>();
        }
        requireNonNull(vegetables);
        for (String vegetable : vegetables) {
            ingredientSet.add(parseVegetable(vegetable));
        }
        return ingredientSet;
    }

    /**
     * Parses a {@code String protein} into a {@code Protein}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code protein} is invalid.
     */
    public static Protein parseProtein(String protein) throws ParseException {
        requireNonNull(protein);
        String[] splitFields = protein.split(",");

        if (splitFields.length != 2) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }
        String trimmedProteinName = splitFields[1].trim();
        String trimmedProteinQuantity = splitFields[0].trim();

        if (!Ingredient.isValidIngredientName(trimmedProteinName)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }

        double proteinQuantity = Double.parseDouble(trimmedProteinQuantity);
        return new Protein(trimmedProteinName, proteinQuantity);
    }

    /**
     * Parses {@code Collection<String> proteins} and adds them to the {@code Set<Ingredient>} ingredientSet.
     */
    public static Set<Ingredient> parseProteins(Collection<String> proteins, Set<Ingredient> ingredientSet)
            throws ParseException {
        if (ingredientSet == null || ingredientSet.isEmpty()) {
            ingredientSet = new TreeSet<>();
        }
        requireNonNull(proteins);
        for (String protein : proteins) {
            ingredientSet.add(parseProtein(protein));
        }
        return ingredientSet;
    }

    /**
     * Parses a {@code String other} into a {@code Other}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code other} is invalid.
     */
    public static Other parseOther(String other) throws ParseException {
        requireNonNull(other);
        String[] splitFields = other.split(",");

        if (splitFields.length != 2) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }
        String trimmedOtherName = splitFields[1].trim();
        String trimmedOtherQuantity = splitFields[0].trim();

        if (!Ingredient.isValidIngredientName(trimmedOtherName)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }

        double otherQuantity = Double.parseDouble(trimmedOtherQuantity);
        return new Other(trimmedOtherName, otherQuantity);
    }

    /**
     * Parses {@code Collection<String> others} and adds them to the {@code Set<Ingredient>} ingredientSet.
     */
    public static Set<Ingredient> parseOthers(Collection<String> others, Set<Ingredient> ingredientSet)
            throws ParseException {
        if (ingredientSet == null || ingredientSet.isEmpty()) {
            ingredientSet = new TreeSet<>();
        }
        requireNonNull(others);
        for (String other : others) {
            ingredientSet.add(parseProtein(other));
        }
        return ingredientSet;
    }

}
