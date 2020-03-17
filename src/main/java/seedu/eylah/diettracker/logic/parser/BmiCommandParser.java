package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.eylah.diettracker.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.stream.Stream;

import seedu.eylah.diettracker.logic.commands.BmiCommand;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class BmiCommandParser implements Parser<BmiCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BmiCommand
     * and returns an BmiCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BmiCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HEIGHT, PREFIX_WEIGHT);

        if (!arePrefixesPresent(argMultimap, PREFIX_HEIGHT)
                || !arePrefixesPresent(argMultimap, PREFIX_WEIGHT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BmiCommand.MESSAGE_USAGE));
        }

        Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get());
        Weight weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());

        return new BmiCommand(height, weight);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
