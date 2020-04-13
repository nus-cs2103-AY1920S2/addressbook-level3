package seedu.address.logic.parser.recipe;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recipe.RecipeCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveIngredientCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveStepCommand;
import seedu.address.logic.commands.recipe.RecipeRemoveTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RecipeRemoveCommand object
 */
public class RecipeRemoveCommandParser implements Parser<RecipeRemoveCommand> {

    private static final Pattern RECIPE_ADD_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile("(?<index>\\d+) *(?<category>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeRemoveCommand
     * and returns a RecipeRemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeRemoveCommand parse(String args) throws ParseException {
        final Matcher matcher = RECIPE_ADD_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecipeRemoveCommand.MESSAGE_USAGE));
        }

        final String index = matcher.group("index");
        final String category = matcher.group("category");
        final String arguments = matcher.group("arguments");

        switch (category) {
        case RecipeCommand.INGREDIENT_KEYWORD:
            return parseRemoveIngredient(index + " " + arguments);
        case RecipeCommand.STEP_KEYWORD:
            return parseRemoveStep(index + " " + arguments);
        case RecipeCommand.TAG_KEYWORD:
            return parseRemoveTag(index + " " + arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecipeRemoveCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeRemoveIngredientCommand
     * and returns a RecipeRemoveIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    RecipeRemoveIngredientCommand parseRemoveIngredient(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    RecipeRemoveCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeRemoveCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME);
        assert argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent();

        IngredientName ingredientName = ParserUtil.parseIngredientName(argMultimap
                .getValue(PREFIX_INGREDIENT_NAME).get());

        Optional<IngredientQuantity> ingredientQuantity = argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_QUANTITY)
                ? Optional.of(ParserUtil.parseIngredientQuantity(argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY)
                    .get()))
                : Optional.empty();

        return new RecipeRemoveIngredientCommand(index, ingredientName, ingredientQuantity);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeRemoveStepCommand
     * and returns a RecipeRemoveStepCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeRemoveStepCommand parseRemoveStep(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STEP_INDEX);

        Index recipeIndex;

        try {
            recipeIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                RecipeRemoveCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_STEP_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeRemoveCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_STEP_INDEX);
        assert argMultimap.getValue(PREFIX_STEP_INDEX).isPresent();
        Index stepIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STEP_INDEX).get());

        return new RecipeRemoveStepCommand(recipeIndex, stepIndex);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeRemoveTagCommand
     * and returns a RecipeRemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeRemoveTagCommand parseRemoveTag(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index recipeIndex;

        try {
            recipeIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeRemoveCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeRemoveCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_TAG);
        assert argMultimap.getValue(PREFIX_TAG).isPresent();
        Tag toRemove = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());

        return new RecipeRemoveTagCommand(recipeIndex, toRemove);
    }

    boolean containsIngredient(String args) {
        return args.contains(PREFIX_INGREDIENT_NAME.toString());
    }

    boolean containsStep(String args) {
        return args.contains(PREFIX_STEP_INDEX.toString());
    }

    boolean containsTag(String args) {
        return args.contains(PREFIX_TAG.toString());
    }
}
