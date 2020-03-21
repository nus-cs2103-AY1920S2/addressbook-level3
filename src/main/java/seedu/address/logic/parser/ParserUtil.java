package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;

import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Ingredient;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Quantity;
import seedu.address.model.recipe.ingredient.Unit;
import seedu.address.model.recipe.ingredient.Vegetable;
import seedu.address.model.util.QuantityUtil;

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
     * Parses multiple {@code oneBasedIndex} into an {@code Index} array and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseMultipleIndex(String oneBasedIndex) throws ParseException {
        String[] args = oneBasedIndex.trim().split(" ");
        int len = args.length;

        Integer[] toSort = new Integer[len];
        for (int i = 0; i < len; i++) {
            String index = args[i];
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            toSort[i] = Integer.parseInt(index);
        }
        Arrays.sort(toSort);

        Index[] indices = new Index[len];
        for (int i = 0; i < len; i++) {
            indices[i] = Index.fromOneBased(toSort[i]);
        }
        return indices;
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
     * Parses a {@code String time} into a {@code Time[] array}.
     * Used to parse a range of times when the filter command is called.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time[] parseTimeRange(String time) throws ParseException {
        requireNonNull(time);
        String[] parsedTimeString = time.split("-");
        assert(parsedTimeString.length > 0);

        int numOfTime = parsedTimeString.length;
        if (numOfTime > 2) { // Case: If there are more than 2 timings specified (ie. more than 2 dashes in the range)
            throw new ParseException(Time.TIME_RANGE_CONSTRANTS);
        } else if (numOfTime == 1) { // Case: If there is only one timing specified
            String trimmedTime = parsedTimeString[0].trim();
            if (!Time.isValidTime(trimmedTime)) {
                throw new ParseException(Time.TIME_RANGE_CONSTRANTS);
            }
            return new Time[] {new Time(trimmedTime)};
        } else { // Case: If a range of timings is specified
            String[] sortedTimeString;
            try {
                sortedTimeString = Stream.of(parsedTimeString).map(String::trim).mapToInt(Integer::parseInt)
                        .sorted().mapToObj(String::valueOf).toArray(String[]::new);
            } catch (NumberFormatException nfe) {
                // Case: If user uses wrong delimiter (eg. t/10,20 instead of t/10-20)
                throw new ParseException(Time.TIME_RANGE_CONSTRANTS);
            }

            Time[] parsedTime = new Time[2];
            for (int i = 0; i < 2; i++) {
                if (!Time.isValidTime(sortedTimeString[i])) {
                    throw new ParseException(Time.TIME_RANGE_CONSTRANTS);
                }
                parsedTime[i] = new Time(sortedTimeString[i]);
            }
            if (parsedTime[0].equals(parsedTime[1])) { // Case: If user inputs same timings in range (eg. t/10-10)
                return new Time[] {parsedTime[0]};
            }
            return parsedTime;
        }
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
     * Parses {@code String quantity} that is in the form (magnitude unit) into a {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed, and units are case-insensitive.
     *
     * @throws ParseException if the give {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        quantity = quantity.toLowerCase().trim();
        int indexOfUnit = QuantityUtil.indexOfFirstAlphabet(quantity);
        if (indexOfUnit == 0 || indexOfUnit == quantity.length()) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }

        double magnitude = Double.parseDouble(quantity.substring(0, indexOfUnit));
        String unitString = quantity.substring(indexOfUnit);
        if (!QuantityUtil.getAvailUnitsAsList().contains(unitString)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }

        Unit unit = QuantityUtil.parseUnit(unitString);
        return new Quantity(magnitude, unit);
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

        if (splitFields.length < 2) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }
        String trimmedGrainName = splitFields[1].trim();
        String trimmedGrainQuantity = splitFields[0].trim();

        if (!Ingredient.isValidIngredientName(trimmedGrainName)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }
        Quantity grainQuantity = parseQuantity(trimmedGrainQuantity);
        return new Grain(trimmedGrainName, grainQuantity);
    }

    /**
     * Parses {@code Collection<String> grains} into a {@code Set<Grain>}.
     */
    public static Set<Grain> parseGrains(Collection<String> grains) throws ParseException {
        requireNonNull(grains);
        final Set<Grain> grainsSet = new TreeSet<>();
        for (String grain : grains) {
            grainsSet.add(parseGrain(grain));
        }
        return grainsSet;
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

        Quantity vegetableQuantity = parseQuantity(trimmedVegetableQuantity);
        return new Vegetable(trimmedVegetableName, vegetableQuantity);
    }

    /**
     * Parses {@code Collection<String> vegetables} into a {@code Set<Vegetable>}.
     */
    public static Set<Vegetable> parseVegetables(Collection<String> vegetables) throws ParseException {
        requireNonNull(vegetables);
        final Set<Vegetable> vegetablesSet = new HashSet<>();
        for (String vegetable : vegetables) {
            vegetablesSet.add(parseVegetable(vegetable));
        }
        return vegetablesSet;
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

        Quantity proteinQuantity = parseQuantity(trimmedProteinQuantity);
        return new Protein(trimmedProteinName, proteinQuantity);
    }

    /**
     * Parses {@code Collection<String> proteins} into a {@code Set<Protein>}.
     */
    public static Set<Protein> parseProteins(Collection<String> proteins) throws ParseException {
        requireNonNull(proteins);
        final Set<Protein> proteinsSet = new HashSet<>();
        for (String protein : proteins) {
            proteinsSet.add(parseProtein(protein));
        }
        return proteinsSet;
    }

    /**
     * Parses a {@code String fruit} into a {@code Fruit}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fruit} is invalid.
     */
    public static Fruit parseFruit(String fruit) throws ParseException {
        requireNonNull(fruit);

        String[] splitFields = fruit.split(",");

        if (splitFields.length != 2) {
            throw new ParseException(Ingredient.MESSAGE_MISSING_FIELD);
        }
        String trimmedFruitName = splitFields[1].trim();
        String trimmedFruitQuantity = splitFields[0].trim();

        if (!Ingredient.isValidIngredientName(trimmedFruitName)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }

        Quantity fruitQuantity = parseQuantity(trimmedFruitQuantity);
        return new Fruit(trimmedFruitName, fruitQuantity);
    }

    /**
     * Parses {@code Collection<String> fruits} into a {@code Set<Fruit>}.
     */
    public static Set<Fruit> parseFruits(Collection<String> fruits) throws ParseException {
        requireNonNull(fruits);
        final Set<Fruit> fruitsSet = new HashSet<>();
        for (String fruit : fruits) {
            fruitsSet.add(parseFruit(fruit));
        }
        return fruitsSet;
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

        Quantity otherQuantity = parseQuantity(trimmedOtherQuantity);
        return new Other(trimmedOtherName, otherQuantity);
    }

    /**
     * Parses {@code Collection<String> others} into a {@code Set<Other>}.
     */
    public static Set<Other> parseOthers(Collection<String> others) throws ParseException {
        final Set<Other> othersSet = new HashSet<>();
        requireNonNull(others);
        for (String other : others) {
            othersSet.add(parseOther(other));
        }
        return othersSet;
    }

}
