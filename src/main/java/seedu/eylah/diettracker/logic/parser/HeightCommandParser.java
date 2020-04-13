package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.HeightCommand;
import seedu.eylah.diettracker.model.self.Height;

/**
 * Parses input arguments and creates a new HeightCommand object
 */
public class HeightCommandParser implements Parser<HeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HeightCommand
     * and returns an HeightCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HeightCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HeightCommand.MESSAGE_USAGE));
        }

        Height height = ParserUtil.parseHeight(args);
        if (height.getHeightFloat() <= 0) {
            throw new ParseException("Please enter a height value >0 and <1000. Fun fact: The shortest person in the "
                    + "world is Chandra Bahadur Dangi at 54.6cm. However, we at EYLAH know you might want to calculate"
                    + " metrics for your baby too!");
        }

        if (height.getHeightFloat() > 1000) {
            throw new ParseException("Please enter a height value >0 and <1000. Fun fact: The tallest person in the "
                    + "world is Robert Wadlow at 2.72m. However, we at EYLAH believe people can grow till 10m!");
        }

        return new HeightCommand(height);
    }
}
