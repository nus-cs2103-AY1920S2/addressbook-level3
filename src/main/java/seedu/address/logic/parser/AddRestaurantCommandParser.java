package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITED;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Cuisine;
import seedu.address.model.restaurant.Hours;
import seedu.address.model.restaurant.Location;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Price;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Visit;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object.
 */
public class AddRestaurantCommandParser implements Parser<AddRestaurantCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @return AddRestaurantCommand
     * @throws ParseException if the user input does not conform the expected format
     *
     */
    public AddRestaurantCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RESTAURANT, PREFIX_LOCATION, PREFIX_OPERATING_HOURS,
                        PREFIX_PRICE, PREFIX_CUISINE, PREFIX_VISITED);

        if (!arePrefixesPresent(argMultimap, PREFIX_RESTAURANT, PREFIX_LOCATION, PREFIX_VISITED)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRestaurantCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseNameR(argMultimap.getValue(PREFIX_NAME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Hours hours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_OPERATING_HOURS).orElse(""));
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).orElse(""));
        Cuisine cuisine = ParserUtil.parseCuisine(argMultimap.getValue(PREFIX_CUISINE).orElse(""));
        Visit visit = ParserUtil.parseVisit(argMultimap.getValue(PREFIX_VISITED).orElse(""));
        ArrayList<Note> recommendedFood = new ArrayList<>(); //add command does not allow adding notes straight away
        ArrayList<Note> goodFood = new ArrayList<>(); //add command does not allow adding notes straight away
        ArrayList<Note> badFood = new ArrayList<>(); //add command does not allow adding notes straight away

        Restaurant restaurant = new Restaurant(name, location, hours, price, cuisine,
                visit, recommendedFood, goodFood, badFood);

        return new AddRestaurantCommand(restaurant);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
