package fithelper.logic.parser.weight;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.stream.Stream;

import fithelper.logic.parser.ArgumentMultimap;
import fithelper.logic.parser.ArgumentTokenizer;
import fithelper.logic.parser.Parser;
import fithelper.logic.parser.ParserUtil;
import fithelper.logic.parser.Prefix;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.logic.weight.EditWeightCommand;
import fithelper.model.weight.Date;
import fithelper.model.weight.WeightValue;

/**
 * Parses input arguments and creates a new EditWeightCommand object
 */
public class EditWeightCommandParser implements Parser<EditWeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditWeightCommand
     * and returns a EditWeightCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditWeightCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_VALUE);

        if (!arePrefixesPresent(argMultimap, PREFIX_VALUE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeightCommand.MESSAGE_USAGE));
        }

        Date date;
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            try {
                date = ParserUtil.parseWeightDate(argMultimap.getValue(PREFIX_DATE).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeightCommand.MESSAGE_USAGE), pe);
            }
        } else {
            date = new Date(LocalDate.now());
        }

        try {
            WeightValue weightValue = ParserUtil.parseWeightValue(argMultimap.getValue(PREFIX_VALUE).get());
            return new EditWeightCommand(date, weightValue);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeightCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
