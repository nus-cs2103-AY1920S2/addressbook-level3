package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_HEIGHT;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_WEIGHT;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.DietLogicManager;
import seedu.eylah.diettracker.logic.commands.BmiCommand;
import seedu.eylah.diettracker.model.self.Height;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class BmiCommandParser implements Parser<BmiCommand> {

    private final Logger logger = LogsCenter.getLogger(DietLogicManager.class);
    /**
     * Parses the given {@code String} of arguments in the context of the BmiCommand
     * and returns an BmiCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BmiCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HEIGHT, PREFIX_WEIGHT);

        Height height;
        Weight weight;

        if (!argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BmiCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_HEIGHT).isEmpty()) {
            height = new Height(0);
        } else {
            height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get());
            if (height.getHeightFloat() > 1000 || height.getHeightFloat() <= 0) {
                throw new ParseException("Invalid height or weight value, height and weight may only range from >0 up"
                        + " to 1000.");
            }
        }

        if (argMultimap.getValue(PREFIX_WEIGHT).isEmpty()) {
            weight = new Weight(0);
        } else {
            weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());
            if (weight.getWeightFloat() > 1000 || weight.getWeightFloat() <= 0) {
                throw new ParseException("Invalid height or weight value, height and weight may only range from >0 up"
                        + " to 1000.");
            }
        }

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
