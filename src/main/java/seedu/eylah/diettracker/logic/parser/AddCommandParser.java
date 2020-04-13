package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_CALORIES;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_NAME;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.AddCommand;
import seedu.eylah.diettracker.model.food.Calories;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.model.food.Name;
import seedu.eylah.diettracker.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CALORIES, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Calories calories = ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Food food = new Food(name, calories, tagList);

        return new AddCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
