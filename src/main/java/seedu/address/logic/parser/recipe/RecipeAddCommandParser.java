package seedu.address.logic.parser.recipe;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recipe.RecipeAddCommand;
import seedu.address.logic.commands.recipe.RecipeAddIngredientCommand;
import seedu.address.logic.commands.recipe.RecipeAddStepCommand;
import seedu.address.logic.commands.recipe.RecipeAddTagCommand;
import seedu.address.logic.commands.recipe.RecipeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.step.Step;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RecipeAddCommand object
 */
public class RecipeAddCommandParser implements Parser<RecipeAddCommand> {

    private static final Pattern RECIPE_ADD_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile("(?<index>\\d+) *(?<category>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeAddCommand
     * and returns a RecipeAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeAddCommand parse(String args) throws ParseException {
        final Matcher matcher = RECIPE_ADD_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecipeAddCommand.MESSAGE_USAGE));
        }

        final String index = matcher.group("index");
        final String category = matcher.group("category");
        final String arguments = matcher.group("arguments");

        switch (category) {
        case RecipeCommand.INGREDIENT_KEYWORD:
            return parseAddIngredient(index + " " + arguments);
        case RecipeCommand.STEP_KEYWORD:
            return parseAddStep(index + " " + arguments);
        case RecipeCommand.TAG_KEYWORD:
            return parseAddTag(index + " " + arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecipeAddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeAddIngredientCommand
     * and returns a RecipeAddIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    RecipeAddIngredientCommand parseAddIngredient(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    RecipeAddCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeAddCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME);
        assert argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent();
        assert argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).isPresent();
        IngredientName ingredientName = ParserUtil.parseIngredientName(argMultimap
                .getValue(PREFIX_INGREDIENT_NAME).get());
        IngredientQuantity ingredientQuantity = ParserUtil.parseIngredientQuantity(argMultimap
                .getValue(PREFIX_INGREDIENT_QUANTITY).get());
        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity);

        return new RecipeAddIngredientCommand(index, ingredient);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeAddStepCommand
     * and returns a RecipeAddStepCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeAddStepCommand parseAddStep(String args) throws ParseException {
        Index recipeIndex;

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_STEP_INDEX, PREFIX_STEP_DESCRIPTION);

        try {
            recipeIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    RecipeAddCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_STEP_INDEX, PREFIX_STEP_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeAddCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_STEP_INDEX, PREFIX_STEP_DESCRIPTION);
        assert argMultimap.getValue(PREFIX_STEP_INDEX).isPresent();
        Optional<Index> stepIndex = Optional.empty();
        Step toAdd;
        String stepVariable = argMultimap.getValue(PREFIX_STEP_INDEX).get();

        if (!stepVariable.equals(RecipeAddStepCommand.NEXT_KEYWORD)) {
            try {
                stepIndex = Optional.of(ParserUtil.parseIndex(stepVariable));
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                        RecipeAddCommand.MESSAGE_USAGE), pe);
            }
        }

        assert argMultimap.getValue(PREFIX_STEP_DESCRIPTION).isPresent();
        toAdd = ParserUtil.parseStep(argMultimap
                .getValue(PREFIX_STEP_DESCRIPTION).get());

        return new RecipeAddStepCommand(recipeIndex, stepIndex, toAdd);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeAddTagCommand
     * and returns a RecipeAddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeAddTagCommand parseAddTag(String args) throws ParseException {
        Tag toAdd;
        Index recipeIndex;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        try {
            recipeIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeAddCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeAddCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_TAG);
        assert argMultimap.getValue(PREFIX_TAG).isPresent();
        toAdd = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());

        return new RecipeAddTagCommand(recipeIndex, toAdd);
    }

    boolean containsIngredient(String args) {
        return args.contains(PREFIX_INGREDIENT_NAME.toString())
                && args.contains(PREFIX_INGREDIENT_QUANTITY.toString());
    }

    boolean containsStep(String args) {
        return args.contains(PREFIX_STEP_INDEX.toString()) && args.contains(PREFIX_STEP_DESCRIPTION.toString());
    }

    boolean containsTag(String args) {
        return args.contains(PREFIX_TAG.toString());
    }
}
