package fithelper.logic.parser.weight;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.stream.Stream;

import fithelper.logic.parser.ArgumentMultimap;
import fithelper.logic.parser.ArgumentTokenizer;
import fithelper.logic.parser.Parser;
import fithelper.logic.parser.ParserUtil;
import fithelper.logic.parser.Prefix;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.logic.weight.DeleteWeightCommand;
import fithelper.model.weight.Date;

/**
 * Parses input arguments and creates a new DeleteWeightCommand object
 */
public class DeleteWeightCommandParser implements Parser<DeleteWeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteWeightCommand
     * and returns a DeleteWeightCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWeightCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Date date;
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            try {
                date = ParserUtil.parseWeightDate(argMultimap.getValue(PREFIX_DATE).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWeightCommand.MESSAGE_USAGE), pe);
            }
        } else {
            date = new Date(LocalDate.now());
        }

        return new DeleteWeightCommand(date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
