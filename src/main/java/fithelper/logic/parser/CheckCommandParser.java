package fithelper.logic.parser;

import fithelper.logic.commands.CheckCommand;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.Type;

import java.util.stream.Stream;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

/**
 * Parses input arguments and creates a new CheckCommand object
 */
public class CheckCommandParser implements Parser<CheckCommand> {
    @Override
    public CheckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_KEYWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_KEYWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        Type checkType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        String keyword = argMultimap.getValue(PREFIX_KEYWORD).get();
        return new CheckCommand(checkType, keyword);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
