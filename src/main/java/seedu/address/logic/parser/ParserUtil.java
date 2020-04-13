package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DIFFERENT_NUMBER_OF_INPUTS;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String recipeName} into a {@code RecipeName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recipeName} is invalid.
     */
    public static RecipeName parseRecipeName(String recipeName) throws ParseException {
        requireNonNull(recipeName);
        String trimmedRecipeName = recipeName.trim();
        if (!RecipeName.isValidRecipeName(trimmedRecipeName)) {
            throw new ParseException(RecipeName.MESSAGE_CONSTRAINTS);
        }
        return new RecipeName(trimmedRecipeName);
    }

    /**
     * Parses a {@code String recipeDescription} into a {@code RecipeDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recipeDescription} is invalid.
     */
    public static RecipeDescription parseRecipeDescription(String recipeDescription) throws ParseException {
        requireNonNull(recipeDescription);
        String trimmedRecipeDescription = recipeDescription.trim();
        if (!RecipeDescription.isValidRecipeDescription(trimmedRecipeDescription)) {
            throw new ParseException(RecipeName.MESSAGE_CONSTRAINTS);
        }
        return new RecipeDescription(trimmedRecipeDescription);
    }

    /**
     * Parses an {@code String ingredientName} into an {@code IngredientName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ingredientName} is invalid.
     */
    public static IngredientName parseIngredientName(String ingredientName) throws ParseException {
        requireNonNull(ingredientName);
        String trimmedIngredientName = ingredientName.trim();
        if (!IngredientName.isValidIngredientName(trimmedIngredientName)) {
            throw new ParseException(IngredientName.MESSAGE_CONSTRAINTS);
        }
        return new IngredientName(trimmedIngredientName);
    }

    /**
     * Parses an {@code String ingredientQuantity} into an {@code IngredientQuantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ingredientQuantity} is invalid.
     */
    public static IngredientQuantity parseIngredientQuantity(String ingredientQuantity) throws ParseException {
        requireNonNull(ingredientQuantity);
        String trimmedIngredientQuantity = ingredientQuantity.trim();
        if (!IngredientQuantity.isValidIngredientQuantity(trimmedIngredientQuantity)) {
            throw new ParseException(IngredientQuantity.MESSAGE_CONSTRAINTS);
        }
        return new IngredientQuantity(trimmedIngredientQuantity);
    }

    /**
     * Parses an {@code String stepDescription} into an {@code Step}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Step} is invalid.
     */
    public static Step parseStep(String stepDescription) throws ParseException {
        requireNonNull(stepDescription);
        String trimmedStepDescription = stepDescription.trim();
        if (!Step.isValidStep(trimmedStepDescription)) {
            throw new ParseException(Step.MESSAGE_CONSTRAINTS);
        }
        return new Step(trimmedStepDescription);
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

    /**
     * Parses {@code List<String> ingredientNames} into a {@code List<IngredientName>}.
     */
    public static List<IngredientName> parseIngredientNames(List<String> ingredientNames) {
        requireNonNull(ingredientNames);

        return ingredientNames.stream().map(IngredientName::new).collect(Collectors.toList());
    }

    /**
     * Parses {@code List<String ingredientQuantities} into a {@code List<IngredientQuantity>}.
     */
    public static List<IngredientQuantity> parseIngredientQuantities(List<String> ingredientQuantities) {
        requireNonNull(ingredientQuantities);

        return ingredientQuantities.stream().map(IngredientQuantity::new).collect(Collectors.toList());
    }

    /**
     * Parses the given {@code List<IngredientName> names} and {@code List<IngredientQuantity> quantities}
     * into a {@code UniqueIngredientList}.
     * @throws ParseException if the size of the two lists are different.
     */
    public static List<Ingredient> parseIngredients(List<IngredientName> names, List<IngredientQuantity> quantities)
            throws ParseException {

        if (names.size() != quantities.size()) {
            throw new ParseException(
                    String.format(MESSAGE_DIFFERENT_NUMBER_OF_INPUTS, names.size(), quantities.size()));
        }

        return IntStream.range(0, names.size())
                .mapToObj(i -> new Ingredient(names.get(i), quantities.get(i)))
                .collect(Collectors.toList());
    }

    /**
     * Parses {@code List<String> stepDescriptions} into a {@code UniqueStepList}.
     */
    public static UniqueStepList parseSteps(List<String> stepDescriptions) {
        requireNonNull(stepDescriptions);
        UniqueStepList steps = new UniqueStepList();

        for (String stepDescription : stepDescriptions) {
            steps.add(new Step(stepDescription));
        }

        return steps;
    }
}
