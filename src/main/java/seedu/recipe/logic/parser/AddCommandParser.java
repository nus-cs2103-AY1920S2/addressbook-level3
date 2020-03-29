package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
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
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIME, PREFIX_INGREDIENT_GRAIN,
                        PREFIX_INGREDIENT_VEGE, PREFIX_INGREDIENT_PROTEIN, PREFIX_INGREDIENT_FRUIT,
                        PREFIX_INGREDIENT_OTHER, PREFIX_STEP, PREFIX_GOAL);

        boolean isAtLeastOneIngredientPresent = areAnyPrefixesPresent(argMultimap, PREFIX_INGREDIENT_GRAIN,
                PREFIX_INGREDIENT_VEGE, PREFIX_INGREDIENT_PROTEIN, PREFIX_INGREDIENT_FRUIT, PREFIX_INGREDIENT_OTHER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIME)
            || !argMultimap.getPreamble().isEmpty() || !isAtLeastOneIngredientPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        List<Step> steps = ParserUtil.parseSteps(argMultimap.getAllValues(PREFIX_STEP));
        Set<Goal> goalList = ParserUtil.parseGoals(argMultimap.getAllValues(PREFIX_GOAL));

        Set<Grain> grainsList = ParserUtil.parseGrains(argMultimap.getAllValues(PREFIX_INGREDIENT_GRAIN));
        Set<Vegetable> vegetablesList = ParserUtil.parseVegetables(argMultimap.getAllValues(PREFIX_INGREDIENT_VEGE));
        Set<Protein> proteinsList = ParserUtil.parseProteins(argMultimap.getAllValues(PREFIX_INGREDIENT_PROTEIN));
        Set<Fruit> fruitsList = ParserUtil.parseFruits(argMultimap.getAllValues(PREFIX_INGREDIENT_FRUIT));
        Set<Other> othersList = ParserUtil.parseOthers(argMultimap.getAllValues(PREFIX_INGREDIENT_OTHER));

        // When recipe is first added, it will not be marked as favourite by default.
        Recipe recipe = new Recipe(name, time,
                grainsList, vegetablesList, proteinsList, fruitsList, othersList,
                steps, goalList, false);

        return new AddCommand(recipe);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if one of the prefixes is not an empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
