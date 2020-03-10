package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Vegetable;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Time;

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
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException { // TODO: Remove email field
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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
     * Parses a {@code String vegetable} into a {@code Vegetable}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code vegetable} is invalid.
     */
    public static Vegetable parseVegetable(String vegetable) throws ParseException {
        requireNonNull(vegetable);
        String[] splitFields = vegetable.split(",");
        String trimmedVegetableName = splitFields[1].trim();
        String trimmedVegetableQuantity = splitFields[0].trim();

        if (!Vegetable.isValidIngredientName(trimmedVegetableName)) {
            throw new ParseException(Vegetable.MESSAGE_CONSTRAINTS);
        }

        double vegetableQuantity = Double.parseDouble(trimmedVegetableQuantity);
        return new Vegetable(vegetableQuantity, trimmedVegetableName);
    }

    /**
     * Parses {@code Collection<String> vegetables} and adds them to the {@code Set<Ingredient>} ingredientSet.
     */
    public static Set<Ingredient> parseVegetables(Collection<String> vegetables, Set<Ingredient> ingredientSet)
            throws ParseException {
        if (ingredientSet == null || ingredientSet.isEmpty()) {
            ingredientSet = new HashSet<>();
        }
        requireNonNull(vegetables);
        for (String vegetable : vegetables) {
            ingredientSet.add(parseVegetable(vegetable));
        }
        return ingredientSet;
    }



}
