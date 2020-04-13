package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_GAIN;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_LOSS;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_MAINTAIN;

import java.util.stream.Stream;

import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.ModeCommand;
import seedu.eylah.diettracker.model.Mode;

/**
 * Parses input arguments and creates a new ModeCommand object
 */
public class ModeCommandParser implements Parser<ModeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModeCommand
     * and returns an ModeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LOSS, PREFIX_GAIN, PREFIX_MAINTAIN);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModeCommand.MESSAGE_USAGE));
        }

        Mode mode;
        if (arePrefixesPresent(argMultimap, PREFIX_LOSS)) {
            mode = Mode.LOSS;
        } else if (arePrefixesPresent(argMultimap, PREFIX_GAIN)) {
            mode = Mode.GAIN;
        } else if (arePrefixesPresent(argMultimap, PREFIX_MAINTAIN)) {
            mode = Mode.MAINTAIN;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ModeCommand.MESSAGE_FLAG_NOT_PROVIDED));
        }

        return new ModeCommand(mode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
