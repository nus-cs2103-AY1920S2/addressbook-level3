package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITED;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditRestaurantDescriptor;
import seedu.address.logic.commands.EditRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRestaurantCommand object
 */
public class EditRestaurantCommandParser implements Parser<EditRestaurantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRestaurantCommand
     * and returns an EditRestaurantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRestaurantCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RESTAURANT, PREFIX_LOCATION, PREFIX_CUISINE, PREFIX_VISITED,
                        PREFIX_OPERATING_HOURS, PREFIX_PRICE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRestaurantCommand.MESSAGE_USAGE), pe);
        }

        EditRestaurantDescriptor editRestaurantDescriptor = new EditRestaurantDescriptor();
        if (argMultimap.getValue(PREFIX_RESTAURANT).isPresent()) {
            editRestaurantDescriptor.setName(ParserUtil.parseNameR(argMultimap.getValue(PREFIX_RESTAURANT).get()));
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editRestaurantDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }

        if (argMultimap.getValue(PREFIX_VISITED).isPresent()) {
            editRestaurantDescriptor.setVisit(ParserUtil.parseVisit(argMultimap.getValue(PREFIX_VISITED).get()));
        }

        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editRestaurantDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }

        if (argMultimap.getValue(PREFIX_CUISINE).isPresent()) {
            editRestaurantDescriptor.setCuisine(ParserUtil.parseCuisine(argMultimap.getValue(PREFIX_CUISINE).get()));
        }

        if (argMultimap.getValue(PREFIX_OPERATING_HOURS).isPresent()) {
            editRestaurantDescriptor
                    .setHours(ParserUtil.parseHours(argMultimap.getValue(PREFIX_OPERATING_HOURS).get()));
        }

        if (!editRestaurantDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRestaurantCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRestaurantCommand(index, editRestaurantDescriptor);
    }

}
