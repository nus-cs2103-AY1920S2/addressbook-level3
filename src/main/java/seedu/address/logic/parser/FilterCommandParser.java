package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
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

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.RecipeMatchesKeywordsPredicate;
import seedu.address.model.recipe.Time;

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

        List<Time> filterByTime;
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            filterByTime = parseTimeForFilter(argMultimap.getValue(PREFIX_TIME).get());
        } else {
            filterByTime = Collections.emptyList();
        }

        Set<Goal> filterByGoals;
        if (!argMultimap.getAllValues(PREFIX_GOAL).isEmpty()) {
            filterByGoals = parseGoalsForFilter(argMultimap.getAllValues(PREFIX_GOAL));
        } else {
            filterByGoals = Collections.emptySet();
        }

        boolean filterByFavourites = false;
        if (argMultimap.getPreamble().toLowerCase().equals("favourites")) {
            filterByFavourites = true;
        }

        if (!isValidPredicates(filterByTime, filterByGoals, filterByFavourites)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(new RecipeMatchesKeywordsPredicate(filterByTime, filterByGoals, filterByFavourites));
    }

    /**
     * Returns true if there are predicates (arguments) in the user's input to filter the recipe book by.
     */
    private boolean isValidPredicates(List<Time> time, Set<Goal> goals, boolean favourites) {
        return !time.isEmpty() || !goals.isEmpty() || favourites;
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
        return time.equals("")
                ? Collections.emptyList()
                : Arrays.asList(ParserUtil.parseTimeRange(time));
    }

}
