package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.RecipeMatchesKeywordsPredicate;
import seedu.address.model.recipe.Time;
import seedu.address.model.recipe.ingredient.Fruit;
import seedu.address.model.recipe.ingredient.Grain;
import seedu.address.model.recipe.ingredient.Other;
import seedu.address.model.recipe.ingredient.Protein;
import seedu.address.model.recipe.ingredient.Vegetable;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIME, PREFIX_INGREDIENT_GRAIN, PREFIX_INGREDIENT_VEGE,
                        PREFIX_INGREDIENT_PROTEIN, PREFIX_INGREDIENT_OTHER, PREFIX_GOAL);

        boolean isFilteredByFavourites = filterByFavourites(argMultimap);

        List<Time> filterByTime = filterByTime(argMultimap);
        Set<Goal> filterByGoals = filterByGoals(argMultimap);
        Set<Grain> filterByGrain = filterByGrain(argMultimap);
        Set<Vegetable> filterByVeg = filterByVege(argMultimap);
        Set<Protein> filterByProtein = filterByProtein(argMultimap);
        Set<Fruit> filterByFruit = filterByFruit(argMultimap);
        Set<Other> filterByOthers = filterByOthers(argMultimap);

        if (!CollectionUtil.isAnyNonEmpty(filterByTime, filterByGoals, filterByGrain, filterByVeg,
                filterByProtein, filterByFruit, filterByOthers) && !isFilteredByFavourites) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(new RecipeMatchesKeywordsPredicate(filterByTime, filterByGoals, isFilteredByFavourites,
                filterByGrain, filterByVeg, filterByProtein, filterByFruit, filterByOthers));
    }

    /**
     * Parses and returns the time input as a {@code List<Time>} if it is present. Otherwise, returns an empty list.
     */
    private List<Time> filterByTime(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            return parseTimeForFilter(argMultimap.getValue(PREFIX_TIME).get());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Parses and returns the goals input as a {@code Set<Goal>} if it is present. Otherwise, returns an empty set.
     */
    private Set<Goal> filterByGoals(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_GOAL).isEmpty()) {
            return parseGoalsForFilter(argMultimap.getAllValues(PREFIX_GOAL));
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Returns whether user specified filtering by favourites.
     */
    private boolean filterByFavourites(ArgumentMultimap argMultimap) throws ParseException {
        String preamble = argMultimap.getPreamble().toLowerCase();
        if (preamble.isBlank()) {
            return false;
        }

        if (preamble.equals("favourites")) {
            return true;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses and returns the Grain ingredient(s) input(s) as a {@code Set<Grain>} if present.
     * Otherwise, returns an empty set.
     */
    private Set<Grain> filterByGrain(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_INGREDIENT_GRAIN).isEmpty()) {
            return parseGrainsForFilter(argMultimap.getAllValues(PREFIX_INGREDIENT_GRAIN));
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Parses and returns the Vegetable ingredient(s) input(s) as a {@code Set<Vegetable>} if present.
     * Otherwise, returns an empty set.
     */
    private Set<Vegetable> filterByVege(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_INGREDIENT_VEGE).isEmpty()) {
            return parseVegetablesForFilter(argMultimap.getAllValues(PREFIX_INGREDIENT_VEGE));
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Parses and returns the Protein ingredient(s) input(s) as a {@code Set<Protein>} if present.
     * Otherwise, returns an empty set.
     */
    private Set<Protein> filterByProtein(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_INGREDIENT_PROTEIN).isEmpty()) {
            return parseProteinsForFilter(argMultimap.getAllValues(PREFIX_INGREDIENT_PROTEIN));
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Parses and returns the Fruit ingredient(s) input(s) as a {@code Set<Fruit>} if present.
     * Otherwise, returns an empty set.
     */
    private Set<Fruit> filterByFruit(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_INGREDIENT_FRUIT).isEmpty()) {
            return parseFruitsForFilter(argMultimap.getAllValues(PREFIX_INGREDIENT_FRUIT));
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Parses and returns the Other ingredient(s) input(s) as a {@code Set<Other>} if present.
     * Otherwise, returns an empty set.
     */
    private Set<Other> filterByOthers(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_INGREDIENT_OTHER).isEmpty()) {
            return parseOthersForFilter(argMultimap.getAllValues(PREFIX_INGREDIENT_OTHER));
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Parses and adds the {@code Collection<String> grains} into the {@code Set<Grain>}
     * If {@code grains} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Grain>} containing zero Grain ingredients.
     */
    private Set<Grain> parseGrainsForFilter(Collection<String> grains) throws ParseException {
        assert grains != null;
        Collection<String> grainSet = grains.size() == 1 && grains.contains("")
                ? Collections.emptySet()
                : grains;
        return ParserUtil.parseGrainsNameOnly(grainSet);
    }

    /**
     * Parses and adds the {@code Collection<String> vegetables} into the {@code Set<Vegetable>}
     * If {@code vegetables} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Vegetable>} containing zero Vegetable ingredients.
     */
    private Set<Vegetable> parseVegetablesForFilter(Collection<String> vegetables) throws ParseException {
        assert vegetables != null;
        Collection<String> vegetableSet = vegetables.size() == 1 && vegetables.contains("")
                ? Collections.emptySet()
                : vegetables;
        return ParserUtil.parseVegetablesNameOnly(vegetableSet);
    }

    /**
     * Parses and adds the {@code Collection<String> proteins} into the {@code Set<Protein>}
     * If {@code proteins} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Protein>} containing zero Protein ingredients.
     */
    private Set<Protein> parseProteinsForFilter(Collection<String> proteins) throws ParseException {
        assert proteins != null;
        Collection<String> proteinSet = proteins.size() == 1 && proteins.contains("")
                ? Collections.emptySet()
                : proteins;
        return ParserUtil.parseProteinsNameOnly(proteinSet);
    }

    /**
     * Parses and adds the {@code Collection<String> fruits} into the {@code Set<Fruit>}
     * If {@code fruits} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Fruit>} containing zero Protein ingredients.
     */
    private Set<Fruit> parseFruitsForFilter(Collection<String> fruits) throws ParseException {
        assert fruits != null;
        Collection<String> fruitSet = fruits.size() == 1 && fruits.contains("")
                ? Collections.emptySet()
                : fruits;
        return ParserUtil.parseFruitsNameOnly(fruitSet);
    }

    /**
     * Parses and adds the {@code Collection<String> others} into the {@code Set<Other>}
     * If {@code others} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Other>} containing zero Other ingredients.
     */
    private Set<Other> parseOthersForFilter(Collection<String> others) throws ParseException {
        assert others != null;
        Collection<String> otherSet = others.size() == 1 && others.contains("")
                ? Collections.emptySet()
                : others;
        return ParserUtil.parseOthersNameOnly(otherSet);
    }

    /**
     * Parses {@code Collection<String> goals} into a {@code Set<Goal>} if {@code goals} is non-empty.
     * If {@code goals} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Goal>} containing zero goals.
     */
    private Set<Goal> parseGoalsForFilter(Collection<String> goals) throws ParseException {
        assert goals != null;
        Collection<String> goalSet = goals.size() == 1 && goals.contains("")
                ? Collections.emptySet()
                : goals;
        return ParserUtil.parseGoals(goalSet);
    }

    /**
     * Parses {@code String time} into a {@code List<Time>} if {@code time} is non-empty.
     */
    private List<Time> parseTimeForFilter(String time) throws ParseException {
        assert time != null;
        return time.isBlank()
                ? Collections.emptyList()
                : Arrays.asList(ParserUtil.parseTimeRange(time));
    }

}
