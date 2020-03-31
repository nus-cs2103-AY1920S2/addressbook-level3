package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_VALUE;

import java.time.LocalDate;
import java.util.stream.Stream;

import fithelper.logic.commands.AddWeightCommand;
import fithelper.logic.parser.exceptions.ParseException;

import fithelper.model.weight.WeightValue;
import fithelper.model.weight.Date;


/**
 * Parses input arguments and creates a new AddWeightCommand object
 */
public class AddWeightCommandParser implements Parser<AddWeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddWeightCommand
     * and returns an AddWeightCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWeightCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_VALUE);

        if (!arePrefixesPresent(argMultimap, PREFIX_VALUE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeightCommand.MESSAGE_USAGE));
        }

        Date date;
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            date = ParserUtil.parseWeightDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            date = new Date(LocalDate.now());
        }
        WeightValue weightValue = ParserUtil.parseWeightValue(argMultimap.getValue(PREFIX_VALUE).get());
        return new AddWeightCommand(date, weightValue);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
