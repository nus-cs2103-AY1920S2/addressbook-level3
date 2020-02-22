package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODWEIGHT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddFoodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.food.Food;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddFoodCommandParser implements Parser<AddFoodCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFoodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FOODNAME, PREFIX_FOODTIME,
                        PREFIX_FOODLOCATION, PREFIX_FOODWEIGHT);

        if (!arePrefixesPresent(argMultimap, PREFIX_FOODNAME, PREFIX_FOODTIME, PREFIX_FOODLOCATION, PREFIX_FOODWEIGHT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
        }

        String name = ParserUtil.parseFoodName(argMultimap.getValue(PREFIX_FOODNAME).get());
        String time = ParserUtil.parseFoodTime(argMultimap.getValue(PREFIX_FOODTIME).get());
        String location = ParserUtil.parseFoodLocation(argMultimap.getValue(PREFIX_FOODLOCATION).get());
        String weight = ParserUtil.parseFoodWeight(argMultimap.getValue(PREFIX_FOODWEIGHT).get());

        Food food = new Food(name, time, location, weight);

        return new AddFoodCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
