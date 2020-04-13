package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.WeightCommand;
import seedu.eylah.diettracker.model.self.Weight;

/**
 * Parses input arguments and creates a new WeightCommand object
 */
public class WeightCommandParser implements Parser<WeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WeightCommand
     * and returns an WeightCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WeightCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeightCommand.MESSAGE_USAGE));
        }

        Weight weight = ParserUtil.parseWeight(args);

        if (weight.getWeightFloat() <= 0) {
            throw new ParseException("Please enter a weight value >0 and <1000.");
        }

        if (weight.getWeightFloat() > 1000) {
            throw new ParseException("Please enter a weight value >0 and <1000. Fun fact: The heaviest person in the "
                    + "world is Jon Brower Minnoch at 635kg. However, we at EYLAH believe people can grow till "
                    + "1000kg!");
        }

        return new WeightCommand(weight);
    }
}
