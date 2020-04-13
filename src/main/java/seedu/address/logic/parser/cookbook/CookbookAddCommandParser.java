package seedu.address.logic.parser.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.cookbook.CookbookAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new CookbookAddCommand object
 */
public class CookbookAddCommandParser implements Parser<CookbookAddCommand> {

    private static final Pattern COOKBOOK_ADD_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(" *recipe(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the CookbookAddCommand
     * and returns a CookbookAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookbookAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = COOKBOOK_ADD_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CookbookAddCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECIPE_NAME, PREFIX_RECIPE_DESCRIPTION, PREFIX_INGREDIENT_NAME,
                        PREFIX_INGREDIENT_QUANTITY, PREFIX_STEP_DESCRIPTION, PREFIX_TAG);

        if (!argMultimap.arePrefixesPresent(PREFIX_RECIPE_NAME, PREFIX_RECIPE_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CookbookAddCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_RECIPE_NAME, PREFIX_RECIPE_DESCRIPTION);
        assert argMultimap.getValue(PREFIX_RECIPE_NAME).isPresent();
        assert argMultimap.getValue(PREFIX_RECIPE_DESCRIPTION).isPresent();
        RecipeName recipeName = ParserUtil.parseRecipeName(argMultimap.getValue(PREFIX_RECIPE_NAME).get());
        RecipeDescription recipeDescription =
                ParserUtil.parseRecipeDescription(argMultimap.getValue(PREFIX_RECIPE_DESCRIPTION).get());

        List<IngredientName> ingredientNames =
                ParserUtil.parseIngredientNames(argMultimap.getAllValues(PREFIX_INGREDIENT_NAME));
        List<IngredientQuantity> ingredientQuantities =
                ParserUtil.parseIngredientQuantities(argMultimap.getAllValues(PREFIX_INGREDIENT_QUANTITY));
        UniqueIngredientList ingredients = new UniqueIngredientList();
        ingredients.setIngredients(ParserUtil.parseIngredients(ingredientNames, ingredientQuantities));
        UniqueStepList steps = ParserUtil.parseSteps(argMultimap.getAllValues(PREFIX_STEP_DESCRIPTION));
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new CookbookAddCommand(new Recipe(recipeName, recipeDescription, ingredients, steps, tags));
    }
}
