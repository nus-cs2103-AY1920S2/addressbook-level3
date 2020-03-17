package seedu.address.logic.parser.general;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.general.exceptions.ParseException;
import seedu.address.model.pet.DateOfBirth;
import seedu.address.model.pet.Food;
import seedu.address.model.pet.Gender;
import seedu.address.model.pet.Name;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes under package parser/pet.
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
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return Gender.valueOf(trimmedGender.toUpperCase());
    }

    /**
     * Parses a {@code String species} into an {@code Species}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code species} is invalid.
     */
    public static Species parseSpecies(String species) throws ParseException {
        requireNonNull(species);
        String trimmedSpecies = species.trim();
        if (!Species.isValidSpecies(trimmedSpecies)) {
            throw new ParseException(Species.MESSAGE_CONSTRAINTS);
        }
        return new Species(trimmedSpecies);
    }

    /**
     * Parses a {@code String dateOfBirth} into an {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDateOfBirth = dateOfBirth.trim();
        if (!DateOfBirth.isValidDateOfBirth(trimmedDateOfBirth)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(trimmedDateOfBirth);
    }

    /**
     * Parses a {@code String food} into an {@code Food}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code food} is invalid.
     */
    public static Food parseFood(String food) throws ParseException {
        requireNonNull(food);
        try {
            String[] foodDetails = food.split(":");
            String trimmedFood = foodDetails[0].trim();
            int foodAmount;
            try {
                foodAmount = Integer.parseInt(foodDetails[1].trim());
            } catch (NumberFormatException e) {
                throw new ParseException(Food.MESSAGE_AMOUNT_CONSTRAINTS);
            }
            if (!Food.isValidFoodName(trimmedFood)) {
                throw new ParseException(Food.MESSAGE_NAME_CONSTRAINTS);
            }
            if (!Food.isValidFoodAmount(foodAmount)) {
                throw new ParseException(Food.MESSAGE_AMOUNT_CONSTRAINTS);
            }
            return new Food(trimmedFood, foodAmount);
        } catch (PatternSyntaxException e) {
            throw new ParseException(Food.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code Collection<String> foodList} into a {@code Set<Food>}.
     */
    public static Set<Food> parseFoodList(Collection<String> foodList) throws ParseException {
        requireNonNull(foodList);
        final Set<Food> foodSet = new HashSet<>();
        for (String food : foodList) {
            foodSet.add(parseFood(food));
        }
        return foodSet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
